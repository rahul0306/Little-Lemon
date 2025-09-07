package com.example.littlelemon.data.cart

import com.example.littlelemon.domain.CartItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("UNCHECKED_CAST")
@Singleton
class CartSyncRepository @Inject constructor(
    private val cartDao: CartDao,
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) {
    fun observeCart(): Flow<List<CartItem>> =
        cartDao.observeCart().map { list -> list.map { it.asCartItem() } }

    suspend fun addOrUpdate(item: CartItem) {
        cartDao.upsertOne(item.asEntity())
        pushItemToRemote(item)
    }

    suspend fun updateQuantity(dishId: Int, quantity: Int) {
        cartDao.updateQuantity(dishId = dishId, quantity = quantity.coerceAtLeast(1))
        val item = cartDao.observeCart().map { it.find { e -> e.dishId == dishId }?.asCartItem() }
            .awaitFirstOrNull()
        item?.let { pushItemToRemote(it.copy(quantity = quantity.coerceAtLeast(1))) }
    }

    suspend fun remove(dishId: Int) {
        cartDao.deleteOne(dishId = dishId)
        remoteDoc("cart", dishId.toString())?.delete()?.await()
    }

    suspend fun clearCart() {
        cartDao.clear()
        val uid = uidOrNull() ?: return
        val batch = db.batch()
        db.collection("users").document(uid).collection("cart").get().await().forEach {
            batch.delete(it.reference)
        }
        batch.commit().await()
    }

    suspend fun syncMerge() {
        val uid = uidOrNull() ?: return
        val snap = db.collection("users").document(uid).collection("cart").get().await()
        val remote = snap.documents.mapNotNull { it.data }.map {
            CartItem(
                dishId = (it["dishId"] as Number).toInt(),
                title = it["title"] as String,
                price = (it["price"] as Number).toDouble(),
                quantity = (it["quantity"] as Number).toInt()
            )
        }
        cartDao.upsert(remote.map { it.asEntity() })
    }

    suspend fun checkOut(deliveryFee: Double, serviceFee: Double) {
        val uid = uidOrNull() ?: error("Not signed in")
        val items = cartDao.observeCart().awaitFirst().map { it.asCartItem() }
        if (items.isEmpty()) return

        val subtotal = items.sumOf { it.price * it.quantity }
        val total = subtotal + deliveryFee + serviceFee
        val orders = db.collection("users").document(uid).collection("orders")
        val orderRef = orders.document()
        val summary = mapOf(
            "createdAt" to FieldValue.serverTimestamp(),
            "subtotal" to subtotal,
            "deliveryFee" to deliveryFee,
            "serviceFee" to serviceFee,
            "total" to total,
            "itemCount" to items.sumOf { it.quantity }
        )

        val batch = db.batch()
        batch.set(orderRef, summary)
        val itemCol = orderRef.collection("items")
        items.forEach { ci ->
            batch.set(
                itemCol.document(ci.dishId.toString()), mapOf(
                    "dishId" to ci.dishId,
                    "title" to ci.title,
                    "price" to ci.price,
                    "quantity" to ci.quantity,
                    "lineTotal" to ci.price * ci.quantity
                )
            )
        }
        val cartCol = db.collection("users").document(uid).collection("cart")
        db.collection("users").document(uid).collection("cart").get().await().forEach {
            batch.delete(it.reference)
        }

        batch.commit().await()
        cartDao.clear()
    }

    private fun uidOrNull() = auth.currentUser?.uid
    private fun remoteDoc(col: String, doc: String) =
        uidOrNull()?.let { uid ->
            db.collection("users").document(uid).collection(col).document(doc)
        }

    private suspend fun pushItemToRemote(item: CartItem) {
        val doc = remoteDoc("cart", item.dishId.toString()) ?: return
        doc.set(
            mapOf(
                "dishId" to item.dishId,
                "title" to item.title,
                "price" to item.price,
                "quantity" to item.quantity,
                "updatedAt" to FieldValue.serverTimestamp()
            )
        ).await()
    }

    private suspend fun <T> Flow<T>.awaitFirst(): T = first()
    private suspend fun <T> Flow<T>.awaitFirstOrNull(): T? =
        firstOrNull()
}