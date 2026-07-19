package com.vasev.trainingapp.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vasev.trainingapp.core.database.entity.ProgramTagEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for `program_tags` — user-defined tags of programs / DAO для `program_tags` — пользовательские теги программ
 *
 * `public` — visible to the `app` module so that the Hilt `DatabaseModule` can provide it /
 * `public` — виден модулю `app`, чтобы Hilt-модуль `DatabaseModule` мог его предоставлять
 */
@Dao
interface ProgramTagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tag: ProgramTagEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tags: List<ProgramTagEntity>)

    @Delete
    suspend fun delete(tag: ProgramTagEntity)

    @Query("SELECT * FROM program_tags WHERE programId = :programId")
    suspend fun getByProgram(programId: Long): List<ProgramTagEntity>

    @Query("SELECT * FROM program_tags WHERE tag = :tag")
    suspend fun getByTag(tag: String): List<ProgramTagEntity>

    @Query("SELECT * FROM program_tags WHERE programId = :programId")
    fun observeByProgram(programId: Long): Flow<List<ProgramTagEntity>>
}
