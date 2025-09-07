package com.example.littlelemon.presentation.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) {
    private fun key(username: String) = username.trim().lowercase()

    suspend fun isUsernameTaken(username: String): Boolean {
        val doc = db.collection("usernames").document(key(username)).get().await()
        return doc.exists()
    }

    suspend fun signUp(username: String, email: String, password: String) {
        if (isUsernameTaken(username)) error("Username is already taken")

        //Create auth user
        val res = auth.createUserWithEmailAndPassword(email, password).await()
        val user = res.user ?: error("Something went wrong")

        //set display name to show at homescreen
        user.updateProfile(userProfileChangeRequest { displayName = username }).await()

        //save user to firestore
        val uid = user.uid
        val uname = username.trim().lowercase()
        val batch = db.batch()
        val doc = db.collection("users").document(uid)
        val userDoc = db.collection("usernames").document(key(username))

        batch.set(
            userDoc, mapOf(
                "uid" to uid,
                "username" to username,
                "email" to email,
                "createdAt" to FieldValue.serverTimestamp()
            )
        )
        batch.set(
            userDoc, mapOf(
                "uid" to uid,
                "email" to email
            )
        )
        batch.commit().await()

        user.sendEmailVerification().await()
        auth.signOut()
    }

    suspend fun signInWithUsername(username: String, password: String) {
        val doc = db.collection("usernames").document(key(username)).get().await()
        if (!doc.exists()) error("Account not found for $username")
        val email = doc.getString("email") ?: error("Error")

        val result = auth.signInWithEmailAndPassword(email, password).await()
        val user = result.user ?: error("Sign in failed")
        user.reload().await()

        if (!user.isEmailVerified) {
            user.sendEmailVerification().await()
            auth.signOut()
            error("Email not verified.")
        }
    }

    fun currentUser() = auth.currentUser
    fun signOut() = auth.signOut()
}