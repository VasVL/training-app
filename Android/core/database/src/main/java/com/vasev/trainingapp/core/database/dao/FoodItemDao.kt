package com.vasev.trainingapp.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vasev.trainingapp.core.database.entity.FoodItemEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for `food_items` — food products from the nutrition database / DAO для `food_items` — продукты из базы питания
 *
 * `public` — visible to the `app` module so that the Hilt `DatabaseModule` can provide it /
 * `public` — виден модулю `app`, чтобы Hilt-модуль `DatabaseModule` мог его предоставлять
 */
@Dao
interface FoodItemDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(item: FoodItemEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(items: List<FoodItemEntity>): List<Long>

    @Update
    suspend fun update(item: FoodItemEntity)

    @Delete
    suspend fun delete(item: FoodItemEntity)

    @Query("SELECT * FROM food_items WHERE id = :id")
    suspend fun getById(id: Long): FoodItemEntity?

    @Query("SELECT * FROM food_items WHERE remoteId = :remoteId")
    suspend fun getByRemoteId(remoteId: String): FoodItemEntity?

    @Query("SELECT * FROM food_items WHERE name LIKE '%' || :query || '%' ORDER BY name ASC")
    suspend fun searchByName(query: String): List<FoodItemEntity>

    @Query("SELECT * FROM food_items ORDER BY name ASC")
    fun observeAll(): Flow<List<FoodItemEntity>>

    @Query("SELECT * FROM food_items WHERE category = :category ORDER BY name ASC")
    fun observeByCategory(category: String): Flow<List<FoodItemEntity>>
}
