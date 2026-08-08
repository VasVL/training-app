package com.vasev.trainingapp.feature.main.ui.placeholder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.vasev.trainingapp.feature.main.ui.databinding.FragmentMainPlaceholderBinding
import timber.log.Timber

/**
 * Reusable placeholder for an unfinished main-shell destination.
 * Переиспользуемая заглушка для незавершённой точки главной оболочки.
 */
internal class MainPlaceholderFragment : Fragment() {

    private var _binding: FragmentMainPlaceholderBinding? = null

    private val binding: FragmentMainPlaceholderBinding
        get() = requireNotNull(_binding)

    private val placeholderArgs: MainPlaceholderFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        Timber.d("onCreateView")
        _binding = FragmentMainPlaceholderBinding.inflate(
            /* inflater = */ inflater,
            /* parent = */ container,
            /* attachToParent = */ false,
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated")
        render(placeholderArgs.titleResId)
    }

    override fun onDestroyView() {
        Timber.d("onDestroyView")
        _binding = null
        super.onDestroyView()
    }

    private fun render(@StringRes titleResId: Int) {
        Timber.d("render: titleResId=$titleResId")
        binding.mainPlaceholderTitleTextView.setText(titleResId)
    }
}
