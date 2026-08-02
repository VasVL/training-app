package com.vasev.trainingapp.feature.auth.ui.useredit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vasev.trainingapp.feature.auth.ui.R
import com.vasev.trainingapp.feature.auth.ui.databinding.FragmentUserMaxesBinding
import com.vasev.trainingapp.feature.auth.ui.useredit.adapter.UserMaxesAdapter
import com.vasev.trainingapp.feature.auth.ui.useredit.entity.UserEditUiState
import com.vasev.trainingapp.feature.auth.ui.useredit.entity.UserMaxesUiState
import com.vasev.trainingapp.feature.auth.ui.useredit.viewmodel.UserEditViewModel
import com.vasev.trainingapp.feature.auth.ui.useredit.viewmodel.UserMaxesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Personal maximums tab of the user editing screen.
 * Вкладка личных максимумов экрана редактирования пользователя.
 */
@AndroidEntryPoint
internal class UserMaxesFragment : Fragment() {

    private var _binding: FragmentUserMaxesBinding? = null

    private val userEditViewModel: UserEditViewModel by viewModels(
        ownerProducer = { requireParentFragment() },
    )

    private val userMaxesAdapter = UserMaxesAdapter()

    private val userMaxesViewModel: UserMaxesViewModel by viewModels()

    private val binding: FragmentUserMaxesBinding
        get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUserMaxesBinding.inflate(
            /* inflater = */ inflater,
            /* parent = */ container,
            /* attachToParent = */ false,
        )
        return binding.root
    }

    override fun onDestroyView() {
        Timber.d("User maximums: tab view destroyed")
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("User maximums: tab opened")
        setupMaximumsList()
        setupClickListeners()
        observeUiState()
    }

    private fun setupMaximumsList() {
        binding.userMaximumsRecyclerView.adapter = userMaxesAdapter
        binding.userMaximumsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.userMaximumsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                when {
                    dy > 0 -> binding.addUserMaximumButton.hide()
                    dy < 0 -> binding.addUserMaximumButton.show()
                }
            }
        })
    }

    private fun setupClickListeners() {
        binding.retryMaximumsButton.setOnClickListener {
            userMaxesViewModel.retry()
        }
        binding.addUserMaximumButton.setOnClickListener {
            Timber.d("User maximums: add result requested")
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    userEditViewModel.uiState.collect { uiState ->
                        updateUserTarget(uiState)
                    }
                }
                launch {
                    userMaxesViewModel.uiState.collect { uiState ->
                        render(uiState)
                    }
                }
            }
        }
    }

    private fun updateUserTarget(uiState: UserEditUiState) {
        when (uiState) {
            is UserEditUiState.Error -> {
                Timber.w("User maximums: parent profile is not ready")
            }

            is UserEditUiState.Loading -> {
                Timber.d("User maximums: parent profile is loading")
            }

            is UserEditUiState.Ready -> {
                when (val mode = uiState.mode) {
                    is UserEditUiState.Ready.Mode.CreateFirstUser -> {
                        userMaxesViewModel.setUser(null)
                    }

                    is UserEditUiState.Ready.Mode.CreateNewUser -> {
                        userMaxesViewModel.setUser(null)
                    }

                    is UserEditUiState.Ready.Mode.EditUser -> {
                        userMaxesViewModel.setUser(mode.userId)
                    }
                }
            }
        }
    }

    private fun render(uiState: UserMaxesUiState) {
        binding.addUserMaximumButton.isVisible = false
        binding.emptyMaximumsTextView.isVisible = false
        binding.maximumsErrorContainer.isVisible = false
        binding.pendingMaximumsContainer.isVisible = false
        binding.userMaximumsRecyclerView.isVisible = false

        when (uiState) {
            is UserMaxesUiState.Error -> renderError(uiState)
            is UserMaxesUiState.Loading -> renderLoading()
            is UserMaxesUiState.Ready.Content -> renderContent(uiState)
            is UserMaxesUiState.Ready.NewProfile -> renderNewProfile()
        }
    }

    private fun renderError(uiState: UserMaxesUiState.Error) {
        Timber.w("User maximums: render error ${uiState.reason}")
        binding.maximumsErrorTextView.setText(R.string.auth_error_load_maximums)
        binding.maximumsErrorContainer.isVisible = true
    }

    private fun renderLoading() {
        Timber.d("User maximums: render loading")
        binding.pendingMaximumsContainer.isVisible = true
    }

    private fun renderContent(uiState: UserMaxesUiState.Ready.Content) {
        Timber.d("User maximums: render content, count=${uiState.maximums.size}")
        userMaxesAdapter.submitList(uiState.maximums)
        binding.addUserMaximumButton.isVisible = true
        if (uiState.maximums.isEmpty()) {
            binding.emptyMaximumsTextView.setText(R.string.auth_title_user_maxes_empty)
            binding.emptyMaximumsTextView.isVisible = true
        } else {
            binding.userMaximumsRecyclerView.isVisible = true
        }
    }

    private fun renderNewProfile() {
        Timber.d("User maximums: render unsaved profile hint")
        binding.emptyMaximumsTextView.setText(R.string.auth_hint_save_profile_for_maxes)
        binding.emptyMaximumsTextView.isVisible = true
    }

}
