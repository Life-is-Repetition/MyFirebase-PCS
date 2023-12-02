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

data class Item(val id: String, val subject:String,val sale: String,val content :String, val price: Int, val userid:String) {
    constructor(doc: QueryDocumentSnapshot) :
            this(doc.id, doc["subject"].toString(),doc["sale"].toString(),doc["content"].toString(), doc["price"].toString().toIntOrNull() ?: 0,doc["userId"].toString())
    constructor(key: String, map: Map<*, *>) :
            this(key, map["subject"].toString(),map["sale"].toString(),map["content"].toString(), map["price"].toString().toIntOrNull() ?: 0,map["userId"].toString())
}

class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

class MyAdapter(private val context: Context, private var items: List<Item>)
    : RecyclerView.Adapter<MyViewHolder>() {

    fun interface OnItemClickListener {
        fun onItemClick(student_id: String)
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    fun updateList(newList: List<Item>) {
        items = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item, parent, false)
        return MyViewHolder(view)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = items[position]
       holder.view.findViewById<TextView>(R.id.content).text = ""

        holder.view.findViewById<TextView>(R.id.subject).text = item.subject
        holder.view.findViewById<TextView>(R.id.sale).text = item.sale
        holder.view.findViewById<TextView>(R.id.price).text = item.price.toString()

        holder.view.setOnClickListener {
            //자신이 작성한 글이면 판매 글 수정화면으로 전환 아니면 글 보기 화면으로 전환
            itemClickListener?.onItemClick(item.id)

            if(Firebase.auth.currentUser?.uid==item.userid){
                val intent = Intent(context, EditSaleItemActivity::class.java)
                intent.putExtra("ITEM_ID", item.id)
                // 기타 필요한 정보를 넘겨줄 수 있습니다.
                context.startActivity(intent)
            }
            else{
                val intent = Intent(context, ItemDetailsActivity::class.java)
                intent.putExtra("ITEM_ID", item.id) // 아이템 정보를 인텐트에 담아 전달할 수 있습니다.
                intent.putExtra("ITEM_SUB", item.subject) // 아이템 정보를 인텐트에 담아 전달할 수 있습니다.
                intent.putExtra("ITEM_SALE", item.sale) // 아이템 정보를 인텐트에 담아 전달할 수 있습니다.
                intent.putExtra("ITEM_PRICE", item.price.toString()) // 아이템 정보를 인텐트에 담아 전달할 수 있습니다.
                intent.putExtra("ITEM_CON", item.content) // 아이템 정보를 인텐트에 담아 전달할 수 있습니다.

                context.startActivity(intent)
            }
        }
    }
    override fun getItemCount() = items.size
}