package com.vasev.trainingapp.feature.auth.ui.userselect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vasev.trainingapp.feature.auth.ui.R
import com.vasev.trainingapp.feature.auth.ui.databinding.FragmentUserSelectBinding
import com.vasev.trainingapp.feature.auth.ui.userselect.adapter.UserSelectAdapter
import com.vasev.trainingapp.feature.auth.ui.userselect.entity.UserSelectUiAction
import com.vasev.trainingapp.feature.auth.ui.userselect.entity.UserSelectUiState
import com.vasev.trainingapp.feature.auth.ui.userselect.viewmodel.UserSelectViewModel
import com.google.android.material.snackbar.Snackbar
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

    private val userSelectAdapter = UserSelectAdapter(
        onUserActionsClicked = { user, anchor ->
            showUserActionsMenu(
                anchor = anchor,
                user = user,
            )
        },
        onUserClicked = { id ->
            viewModel.activateUser(id)
        },
    )

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
        observeUiActions()
        observeUiState()
    }

    private fun observeUiActions() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiAction.collect { uiAction ->
                    renderUiAction(uiAction)
                }
            }
        }
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
        binding.usersErrorTextView.setText(
            when (uiState.reason) {
                UserSelectUiState.Error.Reason.LOAD_USERS_FAILED -> {
                    R.string.auth_error_load_users_failed
                }
            },
        )
    }

    private fun renderUiAction(uiAction: UserSelectUiAction) {
        when (uiAction) {
            is UserSelectUiAction.ShowDeletionConfirmation -> {
                showDeletionConfirmationDialog(uiAction)
            }

            is UserSelectUiAction.ShowDeletionSnackbar -> {
                showDeletionSnackbar(uiAction)
            }

            is UserSelectUiAction.ShowError -> {
                val messageResId = when (uiAction.reason) {
                    UserSelectUiAction.ShowError.ErrorReason.ACTIVE_USER_NOT_FOUND -> {
                        R.string.auth_error_active_user_not_found
                    }

                    UserSelectUiAction.ShowError.ErrorReason.ACTIVATE_USER_WRITE_FAILED -> {
                        R.string.auth_error_database_write_failed
                    }

                    UserSelectUiAction.ShowError.ErrorReason.CANCEL_DELETION_WRITE_FAILED -> {
                        R.string.auth_error_cancel_deletion_write_failed
                    }

                    UserSelectUiAction.ShowError.ErrorReason.DELETE_USER_NOT_AVAILABLE -> {
                        R.string.auth_error_delete_user_not_available
                    }

                    UserSelectUiAction.ShowError.ErrorReason.REQUEST_DELETION_WRITE_FAILED -> {
                        R.string.auth_error_request_deletion_write_failed
                    }
                }
                Snackbar.make(
                    /* view = */ binding.root,
                    /* text = */ messageResId,
                    /* duration = */ Snackbar.LENGTH_LONG,
                ).show()
            }
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
            viewModel.createNewUser()
        }
        binding.createFirstUserButton.setOnClickListener {
            viewModel.createNewUser()
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

    private fun showUserActionsMenu(
        anchor: View,
        user: UserSelectUiState.Ready.UserItem,
    ) {
        PopupMenu(requireContext(), anchor).apply {
            menuInflater.inflate(R.menu.menu_user_actions, menu)
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_delete_user -> {
                        viewModel.requestUserDeletion(user.id)
                        true
                    }

                    R.id.action_edit_user -> {
                        viewModel.editUser(user.id)
                        true
                    }

                    else -> false
                }
            }
            show()
        }
    }

    private fun showDeletionConfirmationDialog(
        uiAction: UserSelectUiAction.ShowDeletionConfirmation,
    ) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.auth_title_delete_user, uiAction.name))
            .setMessage(R.string.auth_message_delete_user_confirmation)
            .setNegativeButton(R.string.auth_user_deletion_action_cancel, null)
            .setPositiveButton(R.string.auth_action_delete) { _, _ ->
                viewModel.deleteUser(
                    id = uiAction.id,
                    name = uiAction.name,
                )
            }
            .show()
    }

    private fun showDeletionSnackbar(
        uiAction: UserSelectUiAction.ShowDeletionSnackbar,
    ) {
        Snackbar.make(
            /* view = */ binding.root,
            /* text = */ R.string.auth_user_deletion_message_undo,
            /* duration = */ DELETION_SNACKBAR_DURATION_MILLIS,
        ).setAction(R.string.auth_user_deletion_action_undo) {
            viewModel.cancelDeletion(uiAction.id)
        }.addCallback(object : Snackbar.Callback() {

            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
                if (event != DISMISS_EVENT_ACTION) {
                    viewModel.finalizeDeletion(uiAction.id)
                }
            }
        }).show()
    }

    private companion object {
        const val DELETION_SNACKBAR_DURATION_MILLIS = 5_000
    }
}
