package com.vasev.trainingapp.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vasev.trainingapp.core.database.entity.ProgramCategoryEntity
import com.vasev.trainingapp.core.database.entity.ProgramCategory
import kotlinx.coroutines.flow.Flow

/**
 * DAO for `program_categories` — built-in category tags of programs / DAO для `program_categories` — вшитые категории программ
 *
 * `internal` — visible only inside the `core/database` module / `internal` — виден только внутри модуля `core/database`
 */
@Dao
internal interface ProgramCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: ProgramCategoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<ProgramCategoryEntity>)

    @Delete
    suspend fun delete(category: ProgramCategoryEntity)

    @Query("SELECT * FROM program_categories WHERE programId = :programId")
    suspend fun getByProgram(programId: Long): List<ProgramCategoryEntity>

    @Query("SELECT * FROM program_categories WHERE programId = :programId")
    fun observeByProgram(programId: Long): Flow<List<ProgramCategoryEntity>>

    @Query("SELECT * FROM program_categories WHERE category = :category")
    suspend fun getByCategory(category: ProgramCategory): List<ProgramCategoryEntity>
}
