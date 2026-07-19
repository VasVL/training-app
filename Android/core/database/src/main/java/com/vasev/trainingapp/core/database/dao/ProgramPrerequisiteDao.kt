package com.vasev.trainingapp.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vasev.trainingapp.core.database.entity.ProgramPrerequisiteEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for `program_prerequisites` — conditions to start a program / DAO для `program_prerequisites` — условия начала программы
 *
 * `public` — visible to the `app` module so that the Hilt `DatabaseModule` can provide it /
 * `public` — виден модулю `app`, чтобы Hilt-модуль `DatabaseModule` мог его предоставлять
 */
@Dao
interface ProgramPrerequisiteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(prerequisite: ProgramPrerequisiteEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(prerequisites: List<ProgramPrerequisiteEntity>): List<Long>

    @Update
    suspend fun update(prerequisite: ProgramPrerequisiteEntity)

    @Delete
    suspend fun delete(prerequisite: ProgramPrerequisiteEntity)

    @Query("SELECT * FROM program_prerequisites WHERE id = :id")
    suspend fun getById(id: Long): ProgramPrerequisiteEntity?

    @Query("SELECT * FROM program_prerequisites WHERE programId = :programId")
    suspend fun getByProgram(programId: Long): List<ProgramPrerequisiteEntity>

    @Query("SELECT * FROM program_prerequisites WHERE programId = :programId")
    fun observeByProgram(programId: Long): Flow<List<ProgramPrerequisiteEntity>>
}
