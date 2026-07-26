package com.vasev.trainingapp.feature.auth.ui.userselect.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.R as MaterialR
import com.google.android.material.color.MaterialColors
import com.vasev.trainingapp.feature.auth.ui.R
import com.vasev.trainingapp.feature.auth.ui.databinding.ItemUserBinding
import com.vasev.trainingapp.feature.auth.ui.userselect.entity.UserSelectUiState

/**
 * Adapter for selecting an active user from the local profile list.
 * Адаптер для выбора активного пользователя из списка локальных профилей.
 */
internal class UserSelectAdapter(
    private val onUserClicked: (Long) -> Unit,
) : ListAdapter<UserSelectUiState.Ready.UserItem, UserSelectAdapter.UserViewHolder>(
    UserDiffCallback(),
) {

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return UserViewHolder(
            binding = binding,
            onUserClicked = onUserClicked,
        )
    }

    internal class UserViewHolder(
        private val binding: ItemUserBinding,
        private val onUserClicked: (Long) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserSelectUiState.Ready.UserItem) {
            binding.activeUserChip.isVisible = user.isActive
            binding.ownerChip.isVisible = user.role == UserSelectUiState.Ready.UserItem.Role.OWNER
            binding.userAvatarTextView.text = user.name.firstOrNull()?.uppercase().orEmpty()
            binding.userCard.strokeColor = MaterialColors.getColor(
                binding.root,
                if (user.isActive) {
                    MaterialR.attr.colorPrimary
                } else {
                    MaterialR.attr.colorOutline
                },
            )
            binding.userNameTextView.text = user.name
            binding.userRoleTextView.setText(
                if (user.role == UserSelectUiState.Ready.UserItem.Role.OWNER) {
                    R.string.auth_role_owner
                } else {
                    R.string.auth_role_trainee
                },
            )
            binding.root.setOnClickListener {
                onUserClicked(user.id)
            }
        }
    }

    private class UserDiffCallback : DiffUtil.ItemCallback<UserSelectUiState.Ready.UserItem>() {

        override fun areContentsTheSame(
            newItem: UserSelectUiState.Ready.UserItem,
            oldItem: UserSelectUiState.Ready.UserItem,
        ): Boolean {
            return newItem == oldItem
        }

        override fun areItemsTheSame(
            newItem: UserSelectUiState.Ready.UserItem,
            oldItem: UserSelectUiState.Ready.UserItem,
        ): Boolean {
            return newItem.id == oldItem.id
        }
    }
}
