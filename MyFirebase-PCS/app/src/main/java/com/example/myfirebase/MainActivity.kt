package com.example.myfirebase

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private var adapter: MyAdapter? = null
    private val db: FirebaseFirestore = Firebase.firestore
    private val postCollectionRef = db.collection("post")
    private val recyclerViewItems by lazy { findViewById<RecyclerView>(R.id.recyclerViewItems) }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Firebase.auth.currentUser == null) {
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
            finish()
        }

        recyclerViewItems.layoutManager = LinearLayoutManager(this)
        adapter = MyAdapter(this, emptyList())
        recyclerViewItems.adapter = adapter
        updateList()  // list items on recyclerview


        val button1 = findViewById<Button>(R.id.fitter)
        button1.setOnClickListener {
            fitterList()

        }

        val button2 = findViewById<Button>(R.id.write)
        button2.setOnClickListener {
            startActivity(
                Intent(this, SaleItemActivity::class.java)
            )
            finish()
        }
        val button3 = findViewById<Button>(R.id.message)
        button3.setOnClickListener {
            startActivity(
                Intent(this, SendActivity::class.java)
            )
            finish()
        }

    }
    private fun updateList() {
        postCollectionRef.get().addOnSuccessListener {
            val posts = mutableListOf<Item>()
            for (doc in it) {
                posts.add(Item(doc))
            }
            adapter?.updateList(posts)
        }
    }
    private fun fitterList() {
        // item.sale이 true인 것들만 필터링하여 가져오기
        postCollectionRef.whereEqualTo("sale", true)
            .get()
            .addOnSuccessListener { result ->
                val posts = mutableListOf<Item>()
                for (document in result) {
                    posts.add(Item(document))
                }
                adapter?.updateList(posts) // RecyclerView에 업데이트된 리스트 적용
            }
            .addOnFailureListener { exception ->
                // 실패할 경우 처리하는 코드
            }
    }
}