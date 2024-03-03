package com.hoangtien2k3.news_app.ui.bantin.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.ui.webview.WebviewFragment
import com.hoangtien2k3.news_app.data.models.BanTin
import com.hoangtien2k3.news_app.databinding.ItemRowArticleBinding
import com.squareup.picasso.Picasso
import java.util.ArrayList

class BanTinAdapter(
    private val mContext: Context,
    private val mListTinTuc: ArrayList<BanTin>
) : RecyclerView.Adapter<BanTinAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tinTuc = mListTinTuc[position]
        holder.binding.tvArticleTitle.text = tinTuc.title
        holder.binding.tvArticlePublished.text = tinTuc.pubDate

        if (tinTuc.img.isEmpty())
            holder.binding.ivArticle.setImageResource(R.drawable.world)
        else
            Picasso.get()
                .load(tinTuc.img)
                .into(holder.binding.ivArticle)


        holder.binding.itemTinTucConstrantlayout.setOnClickListener {
            val fragment = WebviewFragment().apply {
                arguments = Bundle().apply {
                    putString("link", tinTuc.link)
                }
            }

            val fragmentManager = (mContext as AppCompatActivity).supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.main_fragment, fragment)
                .addToBackStack("BanTinFragment")
                .commit()
        }

    }

    override fun getItemCount(): Int {
        return mListTinTuc.size
    }

    inner class ViewHolder(val binding: ItemRowArticleBinding) : RecyclerView.ViewHolder(binding.root)

}
