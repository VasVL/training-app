package com.vasev.trainingapp.feature.main.ui.main.mapper

import com.vasev.trainingapp.feature.auth.contract.entity.ActiveUserSummary
import com.vasev.trainingapp.feature.main.ui.main.entity.MainUiState
import javax.inject.Inject

/**
 * Maps the auth contract model to the main-screen UI model.
 * Преобразует contract-модель auth в UI-модель главного экрана.
 *
 * `@Inject` — Hilt creates this stateless mapper for the ViewModel.
 * `@Inject` — Hilt создаёт этот маппер без состояния для ViewModel.
 */
internal class MainActiveUserMapper @Inject constructor() {

    fun map(summary: ActiveUserSummary): MainUiState.Ready.ActiveUser {
        return MainUiState.Ready.ActiveUser(
            id = summary.id,
            name = summary.name,
        )
    }
}
