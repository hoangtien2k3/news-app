package com.hoangtien2k3.news_app.ui.calender.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hoangtien2k3.news_app.databinding.CalendarCellBinding

class CalendarAdapter(private val daysOfMonth: ArrayList<String>, private val onItemListener: OnItemListener) :
    RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val binding = CalendarCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val layoutParams: ViewGroup.LayoutParams = binding.root.layoutParams
        layoutParams.height = (parent.height * 0.166666666).toInt()
        return CalendarViewHolder(binding, onItemListener)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind(daysOfMonth[position])
    }

    override fun getItemCount(): Int {
        return daysOfMonth.size
    }

    interface OnItemListener {
        fun onItemClick(position: Int, dayText: String)
    }

    class CalendarViewHolder(private val binding: CalendarCellBinding, private val onItemListener: OnItemListener) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(dayText: String) {
            binding.cellDayText.text = dayText
        }

        override fun onClick(v: View) {
            onItemListener.onItemClick(adapterPosition, binding.cellDayText.text.toString())
        }
    }
}
