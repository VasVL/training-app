package com.vasev.trainingapp.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vasev.trainingapp.core.database.entity.WorkoutTemplateEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for `workout_templates` / DAO для `workout_templates`
 *
 * `internal` — visible only inside the `core/database` module / `internal` — виден только внутри модуля `core/database`
 */
@Dao
internal interface WorkoutTemplateDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(template: WorkoutTemplateEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(templates: List<WorkoutTemplateEntity>): List<Long>

    @Update
    suspend fun update(template: WorkoutTemplateEntity)

    @Delete
    suspend fun delete(template: WorkoutTemplateEntity)

    @Query("SELECT * FROM workout_templates WHERE id = :id")
    suspend fun getById(id: Long): WorkoutTemplateEntity?

    @Query("SELECT * FROM workout_templates WHERE dayId = :dayId ORDER BY `order` ASC")
    suspend fun getByDay(dayId: Long): List<WorkoutTemplateEntity>

    @Query("SELECT * FROM workout_templates WHERE dayId = :dayId ORDER BY `order` ASC")
    fun observeByDay(dayId: Long): Flow<List<WorkoutTemplateEntity>>
}
