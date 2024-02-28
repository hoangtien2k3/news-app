package com.hoangtien2k3.news_app.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.activity.BanTinActivity
import com.hoangtien2k3.news_app.models.Category

class CategoryAdapter(
    private val context: Context,
    private val listCategory: ArrayList<Category>
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_danh_muc, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = listCategory[position]
        holder.txtCategory.text = category.name

        holder.cardView.setOnClickListener {
            val intent = Intent(context, BanTinActivity::class.java)
            intent.putExtra("url", category.url)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return listCategory.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtCategory: TextView = itemView.findViewById(R.id.itemDanhMuc_txtDanhMuc)
        val cardView: CardView = itemView.findViewById(R.id.cardView)
    }
}
