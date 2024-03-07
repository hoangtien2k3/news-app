package com.hoangtien2k3.news_app.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.ui.bantin.BanTinFragment
import com.hoangtien2k3.news_app.data.models.Category
import com.hoangtien2k3.news_app.ui.bantin.BanTinFragment2

class CategoryAdapter(
    private val context: Context,
    private var listCategory: List<Category>
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = listCategory[position]
        holder.txtCategory.text = category.name

        holder.cardView.setOnClickListener {
            val banTinFragment = BanTinFragment2()
            val bundle = Bundle().apply {
//                putString("url", category.url)
                putString("category", category.category)
                putString("title", category.name)
            }
            banTinFragment.arguments = bundle

            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.main_fragment, banTinFragment)
                .addToBackStack("HomeFragment")
                .commit()
        }
    }

    // Update data function
    fun updateData(newList: List<Category>) {
        listCategory = newList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listCategory.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtCategory: TextView = itemView.findViewById(R.id.itemDanhMuc)
        val cardView: CardView = itemView.findViewById(R.id.cardView)
    }
}
