package com.vasev.trainingapp.feature.auth.ui.useredit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vasev.trainingapp.feature.auth.ui.databinding.FragmentUserMaxesBinding
import timber.log.Timber

/**
 * Personal maximums tab of the user editing screen.
 * Вкладка личных максимумов экрана редактирования пользователя.
 */
internal class UserMaxesFragment : Fragment() {

    private var _binding: FragmentUserMaxesBinding? = null

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
    }
}
