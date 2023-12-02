package com.example.myfirebase

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.ktx.Firebase

data class Messages(val id: String, val subject:String,val content :String, val recevid:String) {
    constructor(doc: QueryDocumentSnapshot) :
            this(doc.id, doc["subject"].toString(),doc["content"].toString(),doc["recevid"].toString())
    constructor(key: String, map: Map<*, *>) :
            this(key, map["subject"].toString(),map["content"].toString(),map["recevid"].toString())
}

class MyMessageHolder(val view: View) : RecyclerView.ViewHolder(view)

class MessageAdapter(private val context: Context, private var messages: List<Messages>)
    : RecyclerView.Adapter<MyMessageHolder>() {

    fun interface OnItemClickListener {
        fun onItemClick(student_id: String)
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    fun updateList(newList: List<Messages>) {
        messages = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyMessageHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.messages, parent, false)
        return MyMessageHolder(view)
    }
    override fun onBindViewHolder(holder: MyMessageHolder, position: Int) {
        val item = messages[position]
        holder.view.findViewById<TextView>(R.id.content).text = item.content
        holder.view.findViewById<TextView>(R.id.subject).text = item.subject
        holder.view.findViewById<TextView>(R.id.recevid).text = item.recevid

    }
    override fun getItemCount() = messages.size
}