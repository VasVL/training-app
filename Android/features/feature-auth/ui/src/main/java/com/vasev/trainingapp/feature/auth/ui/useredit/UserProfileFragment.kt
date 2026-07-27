package com.vasev.trainingapp.feature.auth.ui.useredit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vasev.trainingapp.feature.auth.ui.databinding.FragmentUserProfileBinding
import timber.log.Timber

/**
 * Profile tab of the user editing screen.
 * Вкладка профиля экрана редактирования пользователя.
 */
internal class UserProfileFragment : Fragment() {

    private var _binding: FragmentUserProfileBinding? = null

    private val binding: FragmentUserProfileBinding
        get() = requireNotNull(_binding)

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
        Timber.d("User profile: tab view destroyed")
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("User profile: tab opened")
    }
}
