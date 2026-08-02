package com.vasev.trainingapp.feature.auth.ui.useredit

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vasev.trainingapp.feature.auth.ui.R
import com.vasev.trainingapp.feature.auth.ui.databinding.FragmentUserProfileBinding
import com.vasev.trainingapp.feature.auth.ui.useredit.entity.UserEditUiState
import com.vasev.trainingapp.feature.auth.ui.useredit.formatter.UserEditUiFormatterProvider
import com.vasev.trainingapp.feature.auth.ui.useredit.viewmodel.UserEditViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Profile tab of the user editing screen.
 * Вкладка профиля экрана редактирования пользователя.
 *
 * `@AndroidEntryPoint` — Hilt provides this Fragment with UI formatting dependencies.
 * `@AndroidEntryPoint` — Hilt предоставляет этому Fragment зависимости для форматирования UI.
 */
@AndroidEntryPoint
internal class UserProfileFragment : Fragment() {

    private var isRendering = false
    private var _binding: FragmentUserProfileBinding? = null

    @Inject
    internal lateinit var formatterProvider: UserEditUiFormatterProvider

    private val binding: FragmentUserProfileBinding
        get() = requireNotNull(_binding)

    private val viewModel: UserEditViewModel by viewModels(
        ownerProducer = { requireParentFragment() },
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUserProfileBinding.inflate(
            /* inflater = */ inflater,
            /* parent = */ container,
            /* attachToParent = */ false,
        )
        return binding.root
    }

    override fun onDestroyView() {
        Timber.d("onDestroyView")
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated")
        setupFieldListeners()
        setupRetryButton()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    render(uiState)
                }
            }
        }
    }

    private fun setupFieldListeners() {
        binding.birthDateEditText.setOnClickListener {
            showBirthDatePicker()
        }
        binding.genderEditText.setOnClickListener {
            showGenderPicker()
        }
        binding.heightEditText.doAfterTextChanged { text ->
            if (!isRendering) {
                viewModel.setHeight(heightInput = text?.toString().orEmpty())
            }
        }
        binding.nameEditText.doAfterTextChanged { text ->
            if (!isRendering) {
                viewModel.setName(nameInput = text?.toString().orEmpty())
            }
        }
        binding.weightEditText.doAfterTextChanged { text ->
            if (!isRendering) {
                viewModel.setWeight(weightInput = text?.toString().orEmpty())
            }
        }
    }

    private fun setupRetryButton() {
        binding.retryProfileButton.setOnClickListener {
            viewModel.reloadUser()
        }
    }

    private fun showBirthDatePicker() {
        val initialDate = (viewModel.uiState.value as? UserEditUiState.Ready)
            ?.birthDate
            ?: LocalDate.now().minusYears(30)
        DatePickerDialog(
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                viewModel.setBirthDate(
                    birthDate = LocalDate.of(
                        year,
                        monthOfYear + 1,
                        dayOfMonth,
                    ),
                )
            },
            initialDate.year,
            initialDate.monthValue - 1,
            initialDate.dayOfMonth,
        ).apply {
            setButton(
                DialogInterface.BUTTON_NEUTRAL,
                getText(R.string.auth_action_clear),
            ) { _, _ ->
                viewModel.setBirthDate(birthDate = null)
            }
            show()
        }
    }

    private fun showGenderPicker() {
        val gender = (viewModel.uiState.value as? UserEditUiState.Ready)
            ?.gender
            ?: UserEditUiState.Ready.Gender.UNKNOWN
        val genders = arrayOf(
            getString(R.string.auth_gender_female),
            getString(R.string.auth_gender_male),
        )
        MaterialAlertDialogBuilder(requireContext())
            .setSingleChoiceItems(genders, getGenderPosition(gender)) { dialog, position ->
                viewModel.setGender(gender = getGenderForPosition(position))
                dialog.dismiss()
            }
            .setNeutralButton(R.string.auth_action_clear) { _, _ ->
                viewModel.setGender(gender = UserEditUiState.Ready.Gender.UNKNOWN)
            }
            .show()
    }

    private fun getGenderForPosition(position: Int): UserEditUiState.Ready.Gender {
        return when (position) {
            FEMALE_GENDER_POSITION -> UserEditUiState.Ready.Gender.FEMALE
            MALE_GENDER_POSITION -> UserEditUiState.Ready.Gender.MALE
            else -> error("Unknown gender position: $position")
        }
    }

    private fun getGenderPosition(gender: UserEditUiState.Ready.Gender): Int {
        return when (gender) {
            UserEditUiState.Ready.Gender.FEMALE -> FEMALE_GENDER_POSITION
            UserEditUiState.Ready.Gender.MALE -> MALE_GENDER_POSITION
            UserEditUiState.Ready.Gender.UNKNOWN -> NO_GENDER_POSITION
        }
    }

    private fun render(uiState: UserEditUiState) {
        Timber.d("render: state=${uiState::class.simpleName}")
        binding.profileContentScrollView.isVisible = uiState is UserEditUiState.Ready
        binding.profileErrorContainer.isVisible = uiState is UserEditUiState.Error
        binding.profileLoadingContainer.isVisible = uiState is UserEditUiState.Loading

        when (uiState) {
            is UserEditUiState.Error -> {
                Timber.w("render: state=ERROR, reason=${uiState.reason}")
                binding.profileErrorTextView.setText(getErrorMessageResId(uiState.reason))
                binding.retryProfileButton.isVisible =
                    uiState.reason == UserEditUiState.Error.Reason.LOAD_USER_FAILED
            }

            is UserEditUiState.Loading -> {
                Timber.d("render: state=LOADING")
            }

            is UserEditUiState.Ready -> {
                Timber.d("render: state=READY")
                renderProfile(uiState)
            }
        }
    }

    private fun renderProfile(uiState: UserEditUiState.Ready) {
        isRendering = true
        try {
            renderFirstProfileHints(mode = uiState.mode)
            renderValidationErrors(validationErrors = uiState.validationErrors)
            setTextIfChanged(
                text = formatBirthDate(uiState.birthDate),
                target = binding.birthDateEditText,
            )
            setTextIfChanged(
                text = getGenderText(uiState.gender),
                target = binding.genderEditText,
            )
            setTextIfChanged(
                text = uiState.heightInput,
                target = binding.heightEditText,
            )
            binding.heightTextInputLayout.suffixText = getHeightUnitText(uiState.heightUnit)
            setTextIfChanged(
                text = uiState.nameInput,
                target = binding.nameEditText,
            )
            setTextIfChanged(
                text = uiState.weightInput,
                target = binding.weightEditText,
            )
            binding.weightTextInputLayout.suffixText = getWeightUnitText(uiState.weightUnit)
        } finally {
            isRendering = false
        }
    }

    private fun renderFirstProfileHints(mode: UserEditUiState.Ready.Mode) {
        val isFirstProfile = mode is UserEditUiState.Ready.Mode.CreateFirstUser
        binding.localOnlyCard.isVisible = isFirstProfile
        binding.nameRequiredHintTextView.isVisible = isFirstProfile
    }

    private fun renderValidationErrors(
        validationErrors: Set<UserEditUiState.Ready.ValidationError>,
    ) {
        binding.birthDateTextInputLayout.error = if (
            UserEditUiState.Ready.ValidationError.BIRTH_DATE_FUTURE in validationErrors
        ) {
            getText(R.string.auth_error_birth_date_future)
        } else {
            null
        }
        binding.heightTextInputLayout.error = if (
            UserEditUiState.Ready.ValidationError.HEIGHT_INVALID in validationErrors
        ) {
            getText(R.string.auth_error_height_invalid)
        } else {
            null
        }
        binding.nameTextInputLayout.error = if (
            UserEditUiState.Ready.ValidationError.NAME_REQUIRED in validationErrors
        ) {
            getText(R.string.auth_error_name_required)
        } else {
            null
        }
        binding.weightTextInputLayout.error = if (
            UserEditUiState.Ready.ValidationError.WEIGHT_INVALID in validationErrors
        ) {
            getText(R.string.auth_error_weight_invalid)
        } else {
            null
        }
    }

    private fun setTextIfChanged(
        target: TextView,
        text: String,
    ) {
        if (target.text.toString() != text) {
            target.text = text
        }
    }

    private fun formatBirthDate(birthDate: LocalDate?): String {
        return birthDate?.format(formatterProvider.provide().dateFormatter).orEmpty()
    }

    private fun getErrorMessageResId(reason: UserEditUiState.Error.Reason): Int {
        return when (reason) {
            UserEditUiState.Error.Reason.LOAD_USER_FAILED -> R.string.auth_error_load_user
            UserEditUiState.Error.Reason.USER_NOT_FOUND -> R.string.auth_error_user_not_found
        }
    }

    private fun getGenderText(gender: UserEditUiState.Ready.Gender): String {
        return when (gender) {
            UserEditUiState.Ready.Gender.FEMALE -> getString(R.string.auth_gender_female)
            UserEditUiState.Ready.Gender.MALE -> getString(R.string.auth_gender_male)
            UserEditUiState.Ready.Gender.UNKNOWN -> ""
        }
    }

    private fun getHeightUnitText(unit: UserEditUiState.Ready.HeightUnit): CharSequence? {
        return when (unit) {
            UserEditUiState.Ready.HeightUnit.CENTIMETERS -> getText(R.string.auth_unit_centimeters)
            UserEditUiState.Ready.HeightUnit.INCHES -> getText(R.string.auth_unit_inches)
        }
    }

    private fun getWeightUnitText(unit: UserEditUiState.Ready.WeightUnit): CharSequence? {
        return when (unit) {
            UserEditUiState.Ready.WeightUnit.KILOGRAMS -> getText(R.string.auth_unit_kilograms)
            UserEditUiState.Ready.WeightUnit.POUNDS -> getText(R.string.auth_unit_pounds)
        }
    }

    private companion object {

        private const val FEMALE_GENDER_POSITION = 0
        private const val MALE_GENDER_POSITION = 1
        private const val NO_GENDER_POSITION = -1
    }
}
