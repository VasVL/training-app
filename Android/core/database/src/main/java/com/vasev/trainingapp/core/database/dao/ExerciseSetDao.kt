package com.vasev.trainingapp.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vasev.trainingapp.core.database.entity.ExerciseSetEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for `exercise_sets` — exercise entries within workout templates / DAO для `exercise_sets` — упражнения в шаблонах тренировок
 *
 * `internal` — visible only inside the `core/database` module / `internal` — виден только внутри модуля `core/database`
 */
@Dao
internal interface ExerciseSetDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(set: ExerciseSetEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(sets: List<ExerciseSetEntity>): List<Long>

    @Update
    suspend fun update(set: ExerciseSetEntity)

    @Delete
    suspend fun delete(set: ExerciseSetEntity)

    @Query("SELECT * FROM exercise_sets WHERE id = :id")
    suspend fun getById(id: Long): ExerciseSetEntity?

    @Query("SELECT * FROM exercise_sets WHERE workoutTemplateId = :workoutTemplateId ORDER BY `order` ASC")
    suspend fun getByWorkoutTemplate(workoutTemplateId: Long): List<ExerciseSetEntity>

    @Query("SELECT * FROM exercise_sets WHERE workoutTemplateId = :workoutTemplateId ORDER BY `order` ASC")
    fun observeByWorkoutTemplate(workoutTemplateId: Long): Flow<List<ExerciseSetEntity>>
}
