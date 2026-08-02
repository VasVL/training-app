package com.vasev.trainingapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import com.vasev.trainingapp.core.navigation.MainScreen
import com.vasev.trainingapp.core.navigation.Screen
import com.vasev.trainingapp.feature.auth.contract.AuthScreen
import com.vasev.trainingapp.feature.auth.contract.UserEditRequest
import com.vasev.trainingapp.feature.auth.domain.repository.UserRepository
import com.vasev.trainingapp.navigation.NavigatorImpl
import com.vasev.trainingapp.navigation.entity.NavigationCommand
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Single-activity host for the whole app. Fragments are swapped via Navigation Component.
 * Единственная Activity-хост для всего приложения. Fragment'ы меняются через Navigation Component.
 *
 * `@AndroidEntryPoint` — Marks Activity as Hilt entry point, allows @Inject /
 * `@AndroidEntryPoint` — Помечает Activity как точку входа Hilt, разрешает @Inject.
 *
 * This annotation makes Hilt generate a injector for this activity so that fields
 * annotated with `@Inject lateinit var` are populated after `super.onCreate(...)`.
 * It also means this activity can receive Hilt-scoped ViewModels via `@HiltViewModel`.
 * Эта аннотация заставляет Hilt сгенерировать инжектор для этой activity, чтобы поля
 * с `@Inject lateinit var` заполнялись после `super.onCreate(...)`.
 * Это также значит, что activity может получать Hilt-ViewModel через `@HiltViewModel`.
 *
 * The navigation host is declared in activity_main.xml. FragmentContainerView creates the
 * NavHostFragment and loads the start destination from nav_graph.xml automatically.
 * Хост навигации объявлен в activity_main.xml. FragmentContainerView сам создаёт
 * NavHostFragment и загружает стартовую точку из nav_graph.xml.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var isInitialDestinationLoading = true

    /**
     * `@Inject` — Hilt provides the app-scoped navigator that emits navigation commands.
     * `@Inject` — Hilt предоставляет навигатор уровня приложения, отправляющий команды навигации.
     */
    @Inject
    internal lateinit var navigator: NavigatorImpl

    /**
     * `@Inject` — Hilt provides the repository used to choose the initial destination.
     * `@Inject` — Hilt предоставляет репозиторий для выбора начальной точки назначения.
     */
    @Inject
    internal lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition {
            isInitialDestinationLoading
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                navigator.commands.collect { command ->
                    handleNavigationCommand(command)
                }
            }
        }
        openInitialScreen(savedInstanceState = savedInstanceState)
    }

    private fun handleNavigationCommand(command: NavigationCommand) {
        val navController = getNavController()
        when (command) {
            is NavigationCommand.Back -> {
                navController.popBackStack()
            }

            is NavigationCommand.Navigate -> {
                navigate(navController, command.screen)
            }

            is NavigationCommand.PopUpTo -> {
                val destinationId = getDestinationId(command.screen)
                if (destinationId == null) {
                    Timber.w("Navigation: unsupported popUpTo screen")
                    return
                }
                navController.popBackStack(
                    destinationId = destinationId,
                    inclusive = command.inclusive,
                )
            }
        }
    }

    private fun getDestinationId(screen: Screen): Int? {
        return when (screen) {
            is AuthScreen.CreateFirstUser,
            is AuthScreen.CreateNewUser,
            is AuthScreen.EditUser -> R.id.userEditFragment

            is AuthScreen.Select -> R.id.userSelectFragment
            is MainScreen.Main -> R.id.mainFragment
            else -> {
                Timber.e("Navigation: destination id is not defined for ${screen::class.qualifiedName}")
                null
            }
        }
    }

    private fun getNavController(): NavController {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            as NavHostFragment
        return navHostFragment.navController
    }

    private fun openInitialScreen(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            Timber.d("openInitialScreen: result=SKIPPED_RECREATED_ACTIVITY")
            isInitialDestinationLoading = false
            return
        }

        lifecycleScope.launch {
            try {
                val activeUser = userRepository.getDefault()
                if (activeUser != null) {
                    Timber.d("openInitialScreen: result=OPEN_MAIN")
                    return@launch
                }

                Timber.d("openInitialScreen: result=CREATE_FIRST_USER")
                getNavController().navigate(
                    NavGraphDirections.actionGlobalUserEditFragment(
                        UserEditRequest.CreateFirstUser,
                    ),
                    navOptions {
                        popUpTo(R.id.mainFragment) {
                            inclusive = true
                        }
                    },
                )
            } catch (throwable: Throwable) {
                Timber.e(throwable, "openInitialScreen: result=CHECK_ACTIVE_USER_FAILED")
            } finally {
                isInitialDestinationLoading = false
                Timber.d("openInitialScreen: result=SPLASH_RELEASED")
            }
        }
    }

    private fun navigate(navController: NavController, screen: Screen) {
        when (screen) {
            is AuthScreen.CreateFirstUser -> {
                navController.navigate(
                    NavGraphDirections.actionGlobalUserEditFragment(
                        UserEditRequest.CreateFirstUser,
                    ),
                )
            }

            is AuthScreen.CreateNewUser -> {
                navController.navigate(
                    NavGraphDirections.actionGlobalUserEditFragment(
                        UserEditRequest.CreateNewUser,
                    ),
                )
            }

            is AuthScreen.EditUser -> {
                navController.navigate(
                    NavGraphDirections.actionGlobalUserEditFragment(
                        UserEditRequest.EditUser(userId = screen.userId),
                    ),
                )
            }

            is AuthScreen.Select -> {
                navController.navigate(NavGraphDirections.actionGlobalUserSelectFragment())
            }

            is MainScreen.Main -> {
                navController.navigate(NavGraphDirections.actionGlobalMainFragment())
            }

            else -> {
                Timber.e("Navigation: navigate is not defined for ${screen::class.qualifiedName}")
            }
        }
    }
}
