package com.hoangtien2k3.news_app.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.activity.WebviewActivity
import com.hoangtien2k3.news_app.models.News
import com.squareup.picasso.Picasso
import java.util.ArrayList

class NewsMainAdapter(
    private val mContext: Context,
    private val mListTinTuc: ArrayList<News>
) : RecyclerView.Adapter<NewsMainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_tin_tuc, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tinTuc = mListTinTuc[position]
        holder.txtTitle.text = tinTuc.title

        if (tinTuc.img.isEmpty())
            holder.image.setImageResource(R.drawable.world)
        else
            Picasso.get()
                .load(tinTuc.img)
                .into(holder.image)

        holder.layout.setOnClickListener {
            val intent = Intent(mContext, WebviewActivity::class.java)
            intent.putExtra("link", tinTuc.link)
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mListTinTuc.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.itemTinTuc_img)
        var txtTitle: TextView = itemView.findViewById(R.id.itemTinTuc_title)
        var layout: LinearLayout = itemView.findViewById(R.id.itemTinTuc_layout)
    }
}
