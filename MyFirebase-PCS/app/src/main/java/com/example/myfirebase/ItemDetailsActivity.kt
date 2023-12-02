package com.example.myfirebase

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ItemDetailsActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_post)

        val itemId = intent.getStringExtra("ITEM_ID")
        val itemSUB = intent.getStringExtra("ITEM_SUB")
        val itemPRICE = intent.getStringExtra("ITEM_PRICE")
        val itemSALE = intent.getStringExtra("ITEM_SALE")
        val itemCON = intent.getStringExtra("ITEM_CON")

        val subject=findViewById<TextView>(R.id.subject)
        val content=findViewById<TextView>(R.id.content)
        val name=findViewById<TextView>(R.id.saling)
        val price=findViewById<TextView>(R.id.value)
        val sale=findViewById<TextView>(R.id.isSale)

        subject.text = "$itemSUB"
        name.text = "$itemId"
        price.text = "$itemPRICE"
        sale.text = "$itemSALE"
        content.text = "$itemCON"


        val button=findViewById<Button>(R.id.send)
        button.setOnClickListener {
            val sendIntent = Intent(this, MessagesUpload::class.java)
            // 현재 보내는 사람의 아이디와 글 작성자의 아이디를 SendActivity로 전달
            sendIntent.putExtra("SUBJECT", itemSUB)
            sendIntent.putExtra("POST_AUTHOR_ID", itemId)
            startActivity(sendIntent)
            finish()
        }
    }
}
