package com.example.myfirebase

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SendActivity : AppCompatActivity() {
    private var adapter: MessageAdapter? = null
    private val db: FirebaseFirestore = Firebase.firestore
    private val postCollectionRef = db.collection("Messages")
    private val recyclerViewItems by lazy { findViewById<RecyclerView>(R.id.recyclerViewItems2) }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.messages_view)


        recyclerViewItems.layoutManager = LinearLayoutManager(this)
        adapter = MessageAdapter(this, emptyList())
        recyclerViewItems.adapter = adapter
        updateList()  // list items on recyclerview



    }
    private fun updateList() {
        postCollectionRef.get().addOnSuccessListener {
            val posts = mutableListOf<Messages>()
            for (doc in it) {
                posts.add(Messages(doc))
            }
            adapter?.updateList(posts)
        }
    }
}
