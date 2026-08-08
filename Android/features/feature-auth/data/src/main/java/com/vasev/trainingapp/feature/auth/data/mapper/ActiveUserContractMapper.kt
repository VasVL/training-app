package com.vasev.trainingapp.feature.auth.data.mapper

import com.vasev.trainingapp.feature.auth.contract.entity.ActiveUserSummary
import com.vasev.trainingapp.core.database.entity.projection.ActiveUserProjection
import javax.inject.Inject

/**
 * Maps the active-user database projection to the public feature contract model.
 * Преобразует database-проекцию активного пользователя в публичную contract-модель.
 *
 * `@Inject` — Hilt creates this stateless mapper for the provider.
 * `@Inject` — Hilt создаёт этот маппер без состояния для провайдера.
 */
internal class ActiveUserContractMapper @Inject constructor() {

    fun map(projection: ActiveUserProjection): ActiveUserSummary {
        return ActiveUserSummary(
            id = projection.id,
            name = projection.name,
        )
    }
}
