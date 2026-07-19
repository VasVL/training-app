package com.vasev.trainingapp.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vasev.trainingapp.core.database.entity.ExerciseMuscleEntity

/**
 * DAO for `exercise_muscles` — links between exercises and muscles / DAO для `exercise_muscles` — связи упражнений и мышц
 *
 * `internal` — visible only inside the `core/database` module / `internal` — виден только внутри модуля `core/database`
 */
@Dao
internal interface ExerciseMuscleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(link: ExerciseMuscleEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(links: List<ExerciseMuscleEntity>)

    @Delete
    suspend fun delete(link: ExerciseMuscleEntity)

    @Query("SELECT * FROM exercise_muscles WHERE exerciseId = :exerciseId")
    suspend fun getByExercise(exerciseId: Long): List<ExerciseMuscleEntity>

    @Query("SELECT * FROM exercise_muscles WHERE muscleId = :muscleId")
    suspend fun getByMuscle(muscleId: Long): List<ExerciseMuscleEntity>
}
