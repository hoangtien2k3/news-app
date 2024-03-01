package com.hoangtien2k3.news_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.hoangtien2k3.news_app.R

class CalendarAdapter(private val daysOfMonth: ArrayList<String>, private val onItemListener: OnItemListener) :
    RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.calendar_cell, parent, false)
        val layoutParams: ViewGroup.LayoutParams = view.layoutParams
        layoutParams.height = (parent.height * 0.166666666).toInt()
        return CalendarViewHolder(view, onItemListener)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.dayOfMonth.text = daysOfMonth[position]
    }

    override fun getItemCount(): Int {
        return daysOfMonth.size
    }

    interface OnItemListener {
        fun onItemClick(position: Int, dayText: String)
    }

    class CalendarViewHolder(itemView: View, onItemListener: OnItemListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var dayOfMonth: TextView = itemView.findViewById(R.id.cellDayText)
        private val onItemListener: OnItemListener = onItemListener
        private var position = 0
        override fun onClick(v: View) {
            onItemListener.onItemClick(position, dayOfMonth.text.toString())
        }

        init {
            itemView.setOnClickListener(this)
        }

    }
}
