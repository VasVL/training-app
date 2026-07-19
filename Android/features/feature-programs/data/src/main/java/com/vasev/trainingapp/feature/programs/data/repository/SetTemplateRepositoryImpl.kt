package com.vasev.trainingapp.feature.programs.data.repository

import com.vasev.trainingapp.core.database.dao.SetTemplateDao
import com.vasev.trainingapp.feature.programs.data.mapper.ProgramsMapper
import com.vasev.trainingapp.feature.programs.domain.entity.SetTemplate
import com.vasev.trainingapp.feature.programs.domain.repository.SetTemplateRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of [SetTemplateRepository] backed by the Room [SetTemplateDao]. /
 * Реализация [SetTemplateRepository] на основе Room [SetTemplateDao].
 *
 * `@Inject` — Hilt creates this class and injects [setTemplateDao] and [mapper] /
 * `@Inject` — Hilt создаёт этот класс и внедряет [setTemplateDao] и [mapper]
 */
class SetTemplateRepositoryImpl @Inject constructor(
    private val mapper: ProgramsMapper,
    private val setTemplateDao: SetTemplateDao,
) : SetTemplateRepository {

    override fun observeByExerciseSet(exerciseSetId: Long): Flow<List<SetTemplate>> {
        return setTemplateDao.observeByExerciseSet(exerciseSetId).map { list ->
            list.map { mapper.map(it) }
        }
    }

    override suspend fun getById(id: Long): SetTemplate? {
        return setTemplateDao.getById(id)?.let { mapper.map(it) }
    }

    override suspend fun getByExerciseSet(exerciseSetId: Long): List<SetTemplate> {
        return setTemplateDao.getByExerciseSet(exerciseSetId).map { mapper.map(it) }
    }

    override suspend fun insert(template: SetTemplate): Long {
        return setTemplateDao.insert(mapper.map(template))
    }

    override suspend fun update(template: SetTemplate) {
        return setTemplateDao.update(mapper.map(template))
    }

    override suspend fun delete(template: SetTemplate) {
        return setTemplateDao.delete(mapper.map(template))
    }
}
