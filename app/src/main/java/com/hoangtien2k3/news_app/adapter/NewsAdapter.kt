package com.hoangtien2k3.news_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.models.Article

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivArticle = itemView.findViewById<ImageView>(R.id.ivArticle)
        val tvArticleTitle = itemView.findViewById<TextView>(R.id.tvArticleTitle)
        val tvArticleSource = itemView.findViewById<TextView>(R.id.tvArticleSource)
        val tvArticlePublished = itemView.findViewById<TextView>(R.id.tvArticlePublished)
    }

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_article, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]

        Glide.with(holder.itemView)
            .load(article.urlToImage)
            .into(holder.ivArticle)

        holder.tvArticleTitle.text = article.title
        holder.tvArticleSource.text = article.source.name
        holder.tvArticlePublished.text = article.publishedAt

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(article)
        }
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article)-> Unit){
        onItemClickListener = listener
    }
}