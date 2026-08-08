package com.vasev.trainingapp.feature.main.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.vasev.trainingapp.feature.main.ui.R
import com.vasev.trainingapp.feature.main.ui.databinding.FragmentMainBinding
import com.vasev.trainingapp.feature.main.ui.databinding.HeaderMainDrawerBinding
import com.vasev.trainingapp.feature.main.ui.main.entity.MainUiState
import com.vasev.trainingapp.feature.main.ui.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Persistent application shell displayed after an active profile is selected.
 * Постоянная оболочка приложения, отображаемая после выбора активного профиля.
 *
 * `@AndroidEntryPoint` — Hilt creates the Fragment injector and makes the Hilt ViewModel
 * available through the `by viewModels()` delegate.
 * `@AndroidEntryPoint` — Hilt создаёт инжектор Fragment и делает Hilt ViewModel
 * доступной через делегат `by viewModels()`.
 */
@AndroidEntryPoint
internal class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private var _drawerHeaderBinding: HeaderMainDrawerBinding? = null

    private val binding: FragmentMainBinding
        get() = requireNotNull(_binding)

    private val drawerHeaderBinding: HeaderMainDrawerBinding
        get() = requireNotNull(_drawerHeaderBinding)

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        Timber.d("onCreateView")
        _binding = FragmentMainBinding.inflate(
            /* inflater = */ inflater,
            /* parent = */ container,
            /* attachToParent = */ false,
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated")
        bindDrawerHeader()
        setupBottomNavigation()
        setupDrawer()
        setupToolbar()
        observeUiState()
    }

    override fun onDestroyView() {
        Timber.d("onDestroyView")
        super.onDestroyView()
        _drawerHeaderBinding = null
        _binding = null
    }

    private fun bindDrawerHeader() {
        _drawerHeaderBinding = HeaderMainDrawerBinding.bind(
            binding.mainDrawerNavigationView.getHeaderView(/* index = */ 0),
        )
    }

    private fun setupBottomNavigation() {
        binding.mainBottomNavigationView.setupWithNavController(getChildNavController())
    }

    private fun getChildNavController(): NavController {
        val navHostFragment = childFragmentManager.findFragmentById(R.id.mainChildNavHost)
            as NavHostFragment
        return navHostFragment.navController
    }

    private fun setupDrawer() {
        binding.mainDrawerNavigationView.setNavigationItemSelectedListener { menuItem ->
            handleDrawerItemSelection(menuItem.itemId)
        }
        drawerHeaderBinding.mainDrawerHeader.setOnClickListener {
            closeDrawer()
            viewModel.openActiveProfile()
        }
        drawerHeaderBinding.mainDrawerChooseProfileButton.setOnClickListener {
            closeDrawer()
            viewModel.openUserSelection()
        }
        drawerHeaderBinding.mainDrawerRetryButton.setOnClickListener {
            viewModel.reloadActiveUser()
        }
    }

    private fun handleDrawerItemSelection(@IdRes itemId: Int): Boolean {
        val handled = when (itemId) {
            R.id.mainDrawerHelp -> {
                openChildDestination(R.id.mainHelpFragment)
                true
            }

            R.id.mainDrawerProfile -> {
                viewModel.openActiveProfile()
                true
            }

            R.id.mainDrawerSettings -> {
                openChildDestination(R.id.mainSettingsFragment)
                true
            }

            R.id.mainDrawerUsers -> {
                viewModel.openUserSelection()
                true
            }

            else -> {
                Timber.e("handleDrawerItemSelection: itemId=$itemId, result=UNSUPPORTED_ITEM")
                false
            }
        }
        if (handled) {
            closeDrawer()
        }
        Timber.d("handleDrawerItemSelection: itemId=$itemId, handled=$handled")
        return handled
    }

    private fun openChildDestination(@IdRes destinationId: Int) {
        val navController = getChildNavController()
        if (navController.currentDestination?.id == destinationId) {
            Timber.d("openChildDestination: destinationId=$destinationId, result=ALREADY_OPEN")
            return
        }
        Timber.d("openChildDestination: destinationId=$destinationId")
        navController.navigate(destinationId)
    }

    private fun closeDrawer() {
        Timber.d("closeDrawer")
        binding.mainDrawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun setupToolbar() {
        binding.mainToolbar.title = requireActivity().title
        binding.mainToolbar.setNavigationOnClickListener {
            openDrawer()
        }
    }

    private fun openDrawer() {
        Timber.d("openDrawer")
        binding.mainDrawerLayout.openDrawer(GravityCompat.START)
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

    private fun render(uiState: MainUiState) {
        Timber.d("render: uiState=${uiState::class.simpleName}")
        when (uiState) {
            is MainUiState.Error -> renderError(uiState)
            is MainUiState.Loading -> renderLoading()
            is MainUiState.Ready -> renderReady(uiState)
        }
    }

    private fun renderError(uiState: MainUiState.Error) {
        drawerHeaderBinding.mainDrawerErrorGroup.isVisible = true
        drawerHeaderBinding.mainDrawerLoadingGroup.isVisible = false
        drawerHeaderBinding.mainDrawerReadyGroup.isVisible = false
        drawerHeaderBinding.mainDrawerErrorMessageTextView.setText(
            when (uiState.reason) {
                MainUiState.Error.ErrorReason.LOAD_ACTIVE_USER_FAILED -> {
                    R.string.main_drawer_active_profile_error_message
                }
            },
        )
        setActiveProfileAvailable(isAvailable = false)
    }

    private fun renderLoading() {
        drawerHeaderBinding.mainDrawerErrorGroup.isVisible = false
        drawerHeaderBinding.mainDrawerLoadingGroup.isVisible = true
        drawerHeaderBinding.mainDrawerReadyGroup.isVisible = false
        setActiveProfileAvailable(isAvailable = false)
    }

    private fun renderReady(uiState: MainUiState.Ready) {
        drawerHeaderBinding.mainDrawerErrorGroup.isVisible = false
        drawerHeaderBinding.mainDrawerLoadingGroup.isVisible = false
        drawerHeaderBinding.mainDrawerReadyGroup.isVisible = true
        drawerHeaderBinding.mainDrawerActiveUserNameTextView.text = uiState.activeUser.name
        drawerHeaderBinding.mainDrawerAvatarTextView.text = getAvatarText(uiState.activeUser.name)
        setActiveProfileAvailable(isAvailable = true)
    }

    private fun getAvatarText(name: String): String {
        return name.take(1).uppercase(Locale.getDefault())
    }

    private fun setActiveProfileAvailable(isAvailable: Boolean) {
        drawerHeaderBinding.mainDrawerHeader.isClickable = isAvailable
        drawerHeaderBinding.mainDrawerHeader.isFocusable = isAvailable
        binding.mainDrawerNavigationView.menu.findItem(R.id.mainDrawerProfile).isEnabled = isAvailable
    }
}
