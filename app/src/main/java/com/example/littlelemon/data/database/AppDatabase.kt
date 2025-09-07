package com.example.littlelemon.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.littlelemon.data.cart.CartDao
import com.example.littlelemon.data.cart.CartItemEntity

@Database(
    entities = [CartItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun cartDao(): CartDao
}