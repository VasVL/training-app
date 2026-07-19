package com.vasev.trainingapp.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vasev.trainingapp.core.database.entity.SetTemplateEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for `set_templates` — individual set templates within exercise entries / DAO для `set_templates` — шаблоны подходов
 *
 * `public` — visible to the `app` module so that the Hilt `DatabaseModule` can provide it /
 * `public` — виден модулю `app`, чтобы Hilt-модуль `DatabaseModule` мог его предоставлять
 */
@Dao
interface SetTemplateDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(template: SetTemplateEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(templates: List<SetTemplateEntity>): List<Long>

    @Update
    suspend fun update(template: SetTemplateEntity)

    @Delete
    suspend fun delete(template: SetTemplateEntity)

    @Query("SELECT * FROM set_templates WHERE id = :id")
    suspend fun getById(id: Long): SetTemplateEntity?

    @Query("SELECT * FROM set_templates WHERE exerciseSetId = :exerciseSetId ORDER BY `order` ASC")
    suspend fun getByExerciseSet(exerciseSetId: Long): List<SetTemplateEntity>

    @Query("SELECT * FROM set_templates WHERE exerciseSetId = :exerciseSetId ORDER BY `order` ASC")
    fun observeByExerciseSet(exerciseSetId: Long): Flow<List<SetTemplateEntity>>
}
