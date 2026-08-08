package com.vasev.trainingapp.feature.auth.ui.useredit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.core.os.BundleCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vasev.trainingapp.core.navigation.Navigator
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.vasev.trainingapp.feature.auth.contract.UserEditRequest
import com.vasev.trainingapp.feature.auth.ui.R
import com.vasev.trainingapp.feature.auth.ui.databinding.FragmentUserEditBinding
import com.vasev.trainingapp.feature.auth.ui.useredit.entity.UserEditUiAction
import com.vasev.trainingapp.feature.auth.ui.useredit.entity.UserEditUiState
import com.vasev.trainingapp.feature.auth.ui.useredit.viewmodel.UserEditViewModel
import com.vasev.trainingapp.feature.main.contract.MainScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Container for the profile and personal maximums tabs.
 * Контейнер вкладок профиля и личных максимумов.
 *
 * `@AndroidEntryPoint` — Hilt provides the shared [UserEditViewModel] to this Fragment.
 * `@AndroidEntryPoint` — Hilt предоставляет этому Fragment общую [UserEditViewModel].
 */
@AndroidEntryPoint
internal class UserEditFragment : Fragment() {

    @Inject
    internal lateinit var navigator: Navigator

    private var _binding: FragmentUserEditBinding? = null
    private var tabLayoutMediator: TabLayoutMediator? = null

    private val binding: FragmentUserEditBinding
        get() = requireNotNull(_binding)

    private val viewModel: UserEditViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUserEditBinding.inflate(
            /* inflater = */ inflater,
            /* parent = */ container,
            /* attachToParent = */ false,
        )
        return binding.root
    }

    override fun onDestroyView() {
        Timber.d("onDestroyView")
        tabLayoutMediator?.detach()
        tabLayoutMediator = null
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated")
        setupUserEditPager()
        setupBackNavigation()
        setupSaveUserButton()
        observeUiState()
        observeUiActions()
        val request = getUserEditRequest()
        if (request == null) {
            Timber.e("onViewCreated: result=INVALID_REQUEST")
            navigator.back()
            return
        }
        initializeUserEdit(request)
    }

    private fun setupUserEditPager() {
        binding.userEditViewPager.adapter = UserEditPagerAdapter(this)
        tabLayoutMediator = TabLayoutMediator(
            binding.userEditTabLayout,
            binding.userEditViewPager,
        ) { tab, position ->
            tab.setText(TAB_TITLES[position])
        }.also { mediator ->
            mediator.attach()
        }
    }

    private fun setupSaveUserButton() {
        binding.saveUserButton.setOnClickListener {
            handleSaveUserButtonClick()
        }
    }

    private fun setupBackNavigation() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {

                override fun handleOnBackPressed() {
                    Timber.d("handleOnBackPressed")
                    viewModel.requestExit()
                }
            },
        )
    }

    private fun handleSaveUserButtonClick() {
        val state = viewModel.uiState.value as? UserEditUiState.Ready
        if (state == null) {
            Timber.d("handleSaveUserButtonClick: result=IGNORED")
            return
        }
        if (
            state.mode is UserEditUiState.Ready.Mode.CreateFirstUser &&
            state.isSaved &&
            !state.hasChanges
        ) {
            Timber.d("handleSaveUserButtonClick: result=NAVIGATE_TO_MAIN")
            navigator.navigate(MainScreen.Main)
            return
        }

        Timber.d("handleSaveUserButtonClick: result=SAVE_PROFILE")
        viewModel.saveProfile()
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    renderSaveUserButton(state = state)
                }
            }
        }
    }

    private fun renderSaveUserButton(state: UserEditUiState) {
        val readyState = state as? UserEditUiState.Ready
        if (readyState == null) {
            binding.saveUserButton.isVisible = false
            return
        }

        val isContinueAvailable =
            readyState.mode is UserEditUiState.Ready.Mode.CreateFirstUser &&
                readyState.isSaved &&
                !readyState.hasChanges
        binding.saveUserButton.isEnabled =
            isContinueAvailable || readyState.validationErrors.isEmpty()
        binding.saveUserButton.isVisible = readyState.hasChanges || isContinueAvailable
        binding.saveUserButton.setText(
            if (isContinueAvailable) {
                R.string.auth_action_continue
            } else {
                R.string.auth_action_save
            },
        )
    }

    private fun observeUiActions() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiAction.collect { action ->
                    renderUiAction(action = action)
                }
            }
        }
    }

    private fun renderUiAction(action: UserEditUiAction) {
        Timber.d("renderUiAction: action=${action::class.simpleName}")
        when (action) {
            is UserEditUiAction.CloseScreen -> closeScreen()
            is UserEditUiAction.ShowError -> showError(reason = action.reason)
            is UserEditUiAction.ShowExitConfirmation -> showExitConfirmation(action = action)
        }
    }

    private fun closeScreen() {
        Timber.d("closeScreen")
        navigator.back()
    }

    private fun showExitConfirmation(action: UserEditUiAction.ShowExitConfirmation) {
        val dialogBuilder = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.auth_title_exit_with_changes)
            .setNegativeButton(R.string.auth_action_exit_without_saving) { _, _ ->
                viewModel.discardChanges()
            }
            .setNeutralButton(R.string.auth_action_stay, null)

        if (action.isSaveAvailable) {
            dialogBuilder
                .setMessage(R.string.auth_message_exit_with_changes)
                .setPositiveButton(R.string.auth_action_save_and_exit) { _, _ ->
                    viewModel.saveProfile(closeScreenAfterSave = true)
                }
        } else {
            dialogBuilder.setMessage(R.string.auth_message_exit_invalid_form)
        }
        dialogBuilder.show()
    }

    private fun showError(reason: UserEditUiAction.ShowError.ErrorReason) {
        Timber.w("showError: reason=$reason")
        Snackbar.make(
            /* view = */ binding.root,
            /* text = */ getErrorMessageResId(reason = reason),
            /* duration = */ Snackbar.LENGTH_LONG,
        ).show()
    }

    private fun getErrorMessageResId(
        reason: UserEditUiAction.ShowError.ErrorReason,
    ): Int {
        return when (reason) {
            UserEditUiAction.ShowError.ErrorReason.SAVE_PROFILE_FAILED -> {
                R.string.auth_error_save_profile
            }
        }
    }

    private fun getUserEditRequest(): UserEditRequest? {
        return BundleCompat.getSerializable(
            /* bundle = */ requireArguments(),
            /* key = */ UserEditRequest.NAVIGATION_ARGUMENT_KEY,
            /* clazz = */ UserEditRequest::class.java,
        )
    }

    private fun initializeUserEdit(request: UserEditRequest) {
        Timber.d("initializeUserEdit: request=${request::class.simpleName}")
        when (request) {
            is UserEditRequest.CreateFirstUser -> viewModel.createFirstUser()
            is UserEditRequest.CreateNewUser -> viewModel.createNewUser()
            is UserEditRequest.EditUser -> viewModel.loadUser(request.userId)
        }
    }

    private class UserEditPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                PROFILE_TAB_POSITION -> UserProfileFragment()
                MAXES_TAB_POSITION -> UserMaxesFragment()
                else -> error("Unknown user edit tab position: $position")
            }
        }

        override fun getItemCount(): Int {
            return TAB_TITLES.size
        }
    }

    private companion object {

        private const val MAXES_TAB_POSITION = 1
        private const val PROFILE_TAB_POSITION = 0
        @StringRes
        private val TAB_TITLES = listOf(
            R.string.auth_user_edit_tab_profile,
            R.string.auth_user_edit_tab_maxes,
        )
    }
}
