package com.vasev.trainingapp.feature.auth.ui.useredit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.vasev.trainingapp.feature.auth.ui.R
import com.vasev.trainingapp.feature.auth.ui.databinding.FragmentUserEditBinding
import timber.log.Timber

/**
 * Container for the profile and personal maximums tabs.
 * Контейнер вкладок профиля и личных максимумов.
 *
 * TODO: Connect navigation arguments and the shared UserEditViewModel.
 * TODO: Подключить аргументы навигации и общую UserEditViewModel.
 */
internal class UserEditFragment : Fragment() {

    private var _binding: FragmentUserEditBinding? = null
    private var tabLayoutMediator: TabLayoutMediator? = null

    private val binding: FragmentUserEditBinding
        get() = requireNotNull(_binding)

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
        Timber.d("User edit: container view destroyed")
        tabLayoutMediator?.detach()
        tabLayoutMediator = null
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("User edit: container opened")
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
