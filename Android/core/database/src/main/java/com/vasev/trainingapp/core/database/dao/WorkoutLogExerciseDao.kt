package com.vasev.trainingapp.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vasev.trainingapp.core.database.entity.WorkoutLogExerciseEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for `workout_log_exercises` — exercise entries within workout logs / DAO для `workout_log_exercises` — упражнения в дневнике
 *
 * `internal` — visible only inside the `core/database` module / `internal` — виден только внутри модуля `core/database`
 */
@Dao
internal interface WorkoutLogExerciseDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(exercise: WorkoutLogExerciseEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(exercises: List<WorkoutLogExerciseEntity>): List<Long>

    @Update
    suspend fun update(exercise: WorkoutLogExerciseEntity)

    @Delete
    suspend fun delete(exercise: WorkoutLogExerciseEntity)

    @Query("SELECT * FROM workout_log_exercises WHERE id = :id")
    suspend fun getById(id: Long): WorkoutLogExerciseEntity?

    @Query("SELECT * FROM workout_log_exercises WHERE workoutLogId = :workoutLogId ORDER BY `order` ASC")
    suspend fun getByWorkoutLog(workoutLogId: Long): List<WorkoutLogExerciseEntity>

    @Query("SELECT * FROM workout_log_exercises WHERE workoutLogId = :workoutLogId ORDER BY `order` ASC")
    fun observeByWorkoutLog(workoutLogId: Long): Flow<List<WorkoutLogExerciseEntity>>
}
