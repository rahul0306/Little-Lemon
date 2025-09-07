package com.example.littlelemon.data.cart

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT * FROM cart_items ORDER BY updatedAt DESC")
    fun observeCart(): Flow<List<CartItemEntity>>

    @Upsert
    suspend fun upsert(items: List<CartItemEntity>)

    @Upsert
    suspend fun upsertOne(item: CartItemEntity)

    @Query("UPDATE cart_items SET quantity = :quantity, updatedAt = :ts WHERE dishId = :dishId ")
    suspend fun updateQuantity(dishId:Int, quantity:Int,ts:Long = System.currentTimeMillis())

    @Query("DELETE FROM cart_items WHERE dishId = :dishId")
    suspend fun deleteOne(dishId: Int)

    @Query("DELETE FROM cart_items")
    suspend fun clear()
}