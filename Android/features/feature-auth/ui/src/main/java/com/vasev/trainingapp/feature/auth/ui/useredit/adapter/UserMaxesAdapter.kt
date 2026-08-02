package com.vasev.trainingapp.feature.auth.ui.useredit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vasev.trainingapp.feature.auth.ui.databinding.ItemUserMaximumBinding
import com.vasev.trainingapp.feature.auth.ui.useredit.entity.UserMaxesUiState

/**
 * Adapter for personal result rows.
 * Адаптер строк личных результатов.
 */
internal class UserMaxesAdapter : ListAdapter<
    UserMaxesUiState.Ready.Content.Maximum,
    UserMaxesAdapter.MaximumViewHolder,
>(MaximumDiffCallback()) {

    override fun onBindViewHolder(holder: MaximumViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaximumViewHolder {
        val binding = ItemUserMaximumBinding.inflate(
            /* inflater = */ LayoutInflater.from(parent.context),
            /* parent = */ parent,
            /* attachToParent = */ false,
        )
        return MaximumViewHolder(binding)
    }

    internal class MaximumViewHolder(
        private val binding: ItemUserMaximumBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(maximum: UserMaxesUiState.Ready.Content.Maximum) {
            binding.exerciseNameTextView.text = maximum.exerciseName
            binding.maximumValueTextView.text = maximum.valueText
            binding.measuredAtTextView.text = maximum.dateText
        }
    }

    private class MaximumDiffCallback :
        DiffUtil.ItemCallback<UserMaxesUiState.Ready.Content.Maximum>() {

        override fun areContentsTheSame(
            newItem: UserMaxesUiState.Ready.Content.Maximum,
            oldItem: UserMaxesUiState.Ready.Content.Maximum,
        ): Boolean {
            return newItem == oldItem
        }

        override fun areItemsTheSame(
            newItem: UserMaxesUiState.Ready.Content.Maximum,
            oldItem: UserMaxesUiState.Ready.Content.Maximum,
        ): Boolean {
            return newItem.id == oldItem.id
        }
    }
}
