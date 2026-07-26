package com.vasev.trainingapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
