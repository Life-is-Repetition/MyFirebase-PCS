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

data class Witre(
    val subject: String,
    val content: String,
    val recevid: String
)

class MessagesUpload : AppCompatActivity() {
    private val db: FirebaseFirestore = Firebase.firestore
    private val MessagesCollectionRef = db.collection("Messages")

    private val MESSAGES_COLLECTION = "Messages"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.send)
        val recevidText =  intent.getStringExtra("POST_AUTHOR_ID")
        val subjectText = intent.getStringExtra("SUBJECT")


        val subject = findViewById<TextView>(R.id.textView)
        val content = findViewById<TextView>(R.id.textView4)
        val button = findViewById<Button>(R.id.send)
        val recevid = findViewById<TextView>(R.id.textView2)
        subject.text=subjectText
        recevid.text=recevidText


        button.setOnClickListener {
            // Input validation

            val contentText = content.text.toString()

            if (contentText.isEmpty() ) {
                Toast.makeText(this, R.string.empty_fields, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create a new Messages
            val newMessages = Witre(
                subject = subjectText.toString(),
                content = contentText,
                recevid = recevidText.toString()
                )


            val newMessagesRef = MessagesCollectionRef.document()

            newMessagesRef.set(newMessages)
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
        private const val TAG = "MessagesUpload"
    }
}