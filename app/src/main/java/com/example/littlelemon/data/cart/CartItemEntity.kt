package com.example.littlelemon.data.cart

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.littlelemon.domain.CartItem

@Entity("cart_items")
data class CartItemEntity(
    @PrimaryKey  val dishId:Int,
    val title:String,
    val price:Double,
    val quantity:Int,
    val updatedAt:Long = System.currentTimeMillis()
)

fun CartItemEntity.asCartItem() = CartItem(
    dishId = dishId,
    title = title,
    price = price,
    quantity = quantity,
)

fun CartItem.asEntity() = CartItemEntity(
    dishId = dishId,
    title = title,
    price = price,
    quantity = quantity,
)