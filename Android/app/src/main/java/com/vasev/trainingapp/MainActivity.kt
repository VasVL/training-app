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
 * For now this is a stub — a real NavController and nav_host setup will be added
 * in the next phase when Fragments and nav_graph are ready.
 * Пока это заглушка — реальный NavController и nav_host будут добавлены
 * в следующей фазе, когда будут готовы Fragment'ы и nav_graph.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    /**
     * Stub onCreate — no layout yet. Will host NavHostFragment in the next phase.
     * Заглушка onCreate — layout'а пока нет. Будет хостить NavHostFragment в следующей фазе.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO Phase 1: setContentView + NavController setup / TODO Фаза 1: setContentView + настройка NavController
    }
}
