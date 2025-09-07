package com.example.littlelemon.data.orders

import com.example.littlelemon.domain.OrderLineItem
import com.example.littlelemon.domain.OrderSummary
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) {
    private fun uid() = auth.currentUser?.uid ?: throw IllegalStateException("Not signed in")

    fun observeOrders(): Flow<List<OrderSummary>> = callbackFlow {
        val ref = db.collection("users").document(uid()).collection("orders")
            .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)

        val reg = ref.addSnapshotListener { snap, err ->
            if (err != null) {
                close(err); return@addSnapshotListener
            }
            val list = snap?.documents?.map { d ->
                OrderSummary(
                    id = d.id,
                    createdAt = d.getTimestamp("createdAt"),
                    subtotal = (d.getDouble("subtotal") ?: 0.0),
                    deliveryFee = (d.getDouble("deliveryFee") ?: 0.0),
                    serviceFee = (d.getDouble("serviceFee") ?: 0.0),
                    total = (d.getDouble("total") ?: 0.0),
                    itemCount = (d.getLong("itemCount") ?: 0L).toInt()
                )
            }.orEmpty()
            trySend(list)
        }
        awaitClose {
            reg.remove()
        }
    }
    suspend fun getOrderItems(orderId: String): List<OrderLineItem> {
        val col = db.collection("users").document(uid())
            .collection("orders").document(orderId).collection("items")
        val snap = col.get().await()
        return snap.documents.map { d ->
            OrderLineItem(
                dishId = (d.getLong("dishId") ?: 0L).toInt(),
                title = d.getString("title") ?: "",
                price = d.getDouble("price") ?: 0.0,
                quantity = (d.getLong("quantity") ?: 0L).toInt(),
                lineTotal = d.getDouble("lineTotal") ?: 0.0
            )
        }
    }
}