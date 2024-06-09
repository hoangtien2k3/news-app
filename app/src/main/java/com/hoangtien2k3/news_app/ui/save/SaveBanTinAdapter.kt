package com.hoangtien2k3.news_app.ui.save

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.databinding.ItemRowArticleBinding
import com.hoangtien2k3.news_app.network.response.SavePosResponse
import com.hoangtien2k3.news_app.ui.webview.WebviewFragment

class SaveBanTinAdapter(
    private val mContext: Context,
    private var mListTinTuc: List<SavePosResponse>
) : RecyclerView.Adapter<SaveBanTinAdapter.ViewHolder>() {

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
        else {
            Glide.with(holder.binding.ivArticle.context)
                .load(tinTuc.img)
                .into(holder.binding.ivArticle)
        }

        holder.binding.itemTinTucConstrantlayout.setOnClickListener {
            openWebViewBanTin(tinTuc)
        }
    }

    private fun openWebViewBanTin(tinTuc: SavePosResponse) {
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

    override fun getItemCount(): Int {
        return mListTinTuc.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList: List<SavePosResponse>) {
        mListTinTuc = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemRowArticleBinding) : RecyclerView.ViewHolder(binding.root)

}
