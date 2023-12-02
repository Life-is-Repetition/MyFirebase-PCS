package com.example.myfirebase

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class Post(
    val subject: String,
    val price: String,
    val content: String,
    val sale: Boolean,
    val userId: String?
)

class SaleItemActivity : AppCompatActivity() {
    private val db: FirebaseFirestore = Firebase.firestore
    private val postCollectionRef = db.collection("post")

    private val POST_COLLECTION = "post"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_post)

        val subject = findViewById<TextView>(R.id.subject)
        val price = findViewById<TextView>(R.id.value)
        val content = findViewById<TextView>(R.id.content)
        val button = findViewById<Button>(R.id.send)
        val currentUser = Firebase.auth.currentUser
        val id= currentUser?.email
        if (currentUser == null) {

            return
        }

        button.setOnClickListener {
            // Input validation
            val subjectText = subject.text.toString()
            val priceText = price.text.toString()
            val contentText = content.text.toString()

            if (subjectText.isEmpty() || priceText.isEmpty() || contentText.isEmpty()) {
                Toast.makeText(this, R.string.empty_fields, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create a new post
            val newPost = Post(
                subject = subjectText,
                price = priceText,
                content = contentText,
                sale = true,
                userId = id

            )


            val newPostRef = postCollectionRef.document()

            newPostRef.set(newPost)
                .addOnSuccessListener {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                .addOnFailureListener { e ->

                    Log.e(TAG, "Error adding document", e)
                    Toast.makeText(
                        this,
                        getString(R.string.post_add_error, e.message),
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

    companion object {
        private const val TAG = "SaleItemActivity"
    }
}