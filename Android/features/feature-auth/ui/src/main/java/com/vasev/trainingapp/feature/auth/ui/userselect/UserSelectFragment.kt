package com.vasev.trainingapp.feature.auth.ui.userselect

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
import com.vasev.trainingapp.feature.auth.ui.databinding.FragmentUserSelectBinding
import com.vasev.trainingapp.feature.auth.ui.userselect.adapter.UserSelectAdapter
import com.vasev.trainingapp.feature.auth.ui.userselect.entity.UserSelectUiState
import com.vasev.trainingapp.feature.auth.ui.userselect.viewmodel.UserSelectViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Screen for choosing the active local user profile.
 * Экран выбора активного локального профиля пользователя.
 *
 * `@AndroidEntryPoint` — Hilt creates the Fragment injector and makes the Hilt ViewModel
 * available through the `by viewModels()` delegate.
 * `@AndroidEntryPoint` — Hilt создаёт инжектор Fragment и делает Hilt ViewModel
 * доступной через делегат `by viewModels()`.
 */
@AndroidEntryPoint
class UserSelectFragment : Fragment() {

    private var _binding: FragmentUserSelectBinding? = null

    private val binding: FragmentUserSelectBinding
        get() = requireNotNull(_binding)

    private val userSelectAdapter = UserSelectAdapter { id ->
        viewModel.onUserClicked(id)
    }

    private val viewModel: UserSelectViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUserSelectBinding.inflate(
            /* inflater = */ inflater,
            /* parent = */ container,
            /* attachToParent = */ false,
        )
        return binding.root
    }

    override fun onDestroyView() {
        Timber.d("User selection: screen view destroyed")
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("User selection: screen opened")
        setupClickListeners()
        setupUsersList()
        observeUiState()
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    render(uiState)
                }
            }
        }
    }

    private fun render(uiState: UserSelectUiState) {
        when (uiState) {
            is UserSelectUiState.Error -> renderError(uiState)
            is UserSelectUiState.Loading -> renderLoading()
            is UserSelectUiState.Ready -> renderReady(uiState)
        }
    }

    private fun renderError(uiState: UserSelectUiState.Error) {
        Timber.w("User selection: render error state, displayed users=${uiState.users.size}")
        renderUsers(uiState.users)
        binding.usersErrorTextView.isVisible = true
        binding.usersErrorTextView.text = uiState.message.ifBlank {
            getString(R.string.auth_error_generic)
        }
    }

    private fun renderLoading() {
        Timber.d("User selection: render loading state")
        binding.addUserFab.isVisible = false
        binding.emptyUsersContainer.isVisible = false
        binding.pendingUsersContainer.isVisible = true
        binding.usersErrorTextView.isVisible = false
        binding.usersRecyclerView.isVisible = false
    }

    private fun renderReady(uiState: UserSelectUiState.Ready) {
        Timber.d("User selection: render ready state, users=${uiState.users.size}")
        renderUsers(uiState.users)
        binding.usersErrorTextView.isVisible = false
    }

    private fun renderUsers(users: List<UserSelectUiState.Ready.UserItem>) {
        val hasUsers = users.isNotEmpty()
        binding.addUserFab.isVisible = hasUsers
        binding.emptyUsersContainer.isVisible = !hasUsers
        binding.pendingUsersContainer.isVisible = false
        binding.usersRecyclerView.isVisible = hasUsers
        userSelectAdapter.submitList(users)
    }

    private fun setupClickListeners() {
        binding.addUserFab.setOnClickListener {
            viewModel.onAddUserClicked()
        }
        binding.createFirstUserButton.setOnClickListener {
            viewModel.onAddUserClicked()
        }
    }

    private fun setupUsersList() {
        binding.usersRecyclerView.adapter = userSelectAdapter
        binding.usersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.usersRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                when {
                    dy > 0 -> binding.addUserFab.hide()
                    dy < 0 -> binding.addUserFab.show()
                }
            }
        })
    }
}
