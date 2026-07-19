package com.vasev.trainingapp.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vasev.trainingapp.core.database.entity.WorkoutLogSetEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for `workout_log_sets` — individual sets within workout log exercises / DAO для `workout_log_sets` — подходы в дневнике
 *
 * `public` — visible to the `app` module so that the Hilt `DatabaseModule` can provide it /
 * `public` — виден модулю `app`, чтобы Hilt-модуль `DatabaseModule` мог его предоставлять
 */
@Dao
interface WorkoutLogSetDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(set: WorkoutLogSetEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(sets: List<WorkoutLogSetEntity>): List<Long>

    @Update
    suspend fun update(set: WorkoutLogSetEntity)

    @Delete
    suspend fun delete(set: WorkoutLogSetEntity)

    @Query("SELECT * FROM workout_log_sets WHERE id = :id")
    suspend fun getById(id: Long): WorkoutLogSetEntity?

    @Query("SELECT * FROM workout_log_sets WHERE workoutLogExerciseId = :workoutLogExerciseId ORDER BY `order` ASC")
    suspend fun getByWorkoutLogExercise(workoutLogExerciseId: Long): List<WorkoutLogSetEntity>

    @Query("SELECT * FROM workout_log_sets WHERE workoutLogExerciseId = :workoutLogExerciseId ORDER BY `order` ASC")
    fun observeByWorkoutLogExercise(workoutLogExerciseId: Long): Flow<List<WorkoutLogSetEntity>>
}
