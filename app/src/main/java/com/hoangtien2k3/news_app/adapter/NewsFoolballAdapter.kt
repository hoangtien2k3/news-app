package com.hoangtien2k3.news_app.adapter

import android.annotation.SuppressLint
import com.hoangtien2k3.news_app.models.Football

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hoangtien2k3.news_app.R
import com.squareup.picasso.Picasso
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class NewsFoolballAdapter(
    val mCtx: Context,
    val heroList: List<Football>,
    val showDialoginterface: ShowDialoginterface

) : RecyclerView.Adapter<NewsFoolballAdapter.HeroViewHolder>() {

    interface ShowDialoginterface {
        fun itemClik(hero: Football)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        val view = LayoutInflater.from(mCtx).inflate(R.layout.item_news, parent, false)
        return HeroViewHolder(view)
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        val hero = heroList[position]

        holder.title.ellipsize = TextUtils.TruncateAt.MARQUEE
        holder.title.isSelected = true
        holder.title.isSingleLine = true

        Picasso.get().load(hero.thumbnail).into(holder.imageView)
        holder.title.text = hero.title

        val data = fromISO8601UTC(hero.date)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd, HH:mm")

        if (data != null) {
            val strDate = dateFormat.format(data)
            holder.date.text = strDate

            println(hero.date)
        } else {
            println(hero.date)
        }

        holder.itemView.setOnClickListener {
            showDialoginterface.itemClik(hero)
        }
    }

    override fun getItemCount(): Int {
        return heroList.size
    }

    inner class HeroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image)
        val title: TextView = itemView.findViewById(R.id.title)
        val date: TextView = itemView.findViewById(R.id.date)
    }

    @SuppressLint("SimpleDateFormat")
    private fun fromISO8601UTC(dateStr: String): Date? {
        val tz = TimeZone.getTimeZone("UTC")
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        df.timeZone = tz

        try {
            return df.parse(dateStr)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return null
    }
}
