package com.hoangtien2k3.news_app.ui.bantin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.ui.webview.WebviewFragment
import com.hoangtien2k3.news_app.data.models.BanTin
import com.hoangtien2k3.news_app.data.sharedpreferences.DataLocalManager
import com.hoangtien2k3.news_app.databinding.ItemRowArticleBinding
import com.hoangtien2k3.news_app.ui.save.SaveBanTinViewModel
import com.hoangtien2k3.news_app.ui.save.ViewModelProviderFactory

class BanTinAdapter(
    private val mContext: Context,
    private var mListTinTuc: List<BanTin>,
    private val viewModelProviderFactory: ViewModelProviderFactory
) : RecyclerView.Adapter<BanTinAdapter.ViewHolder>(), Filterable {

    private var mListTinTucFiltered: List<BanTin> = mListTinTuc.toList()

    private val viewModel: SaveBanTinViewModel by lazy {
        viewModelProviderFactory.provideViewModel()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

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
            // open web view
            openWebViewBanTin(tinTuc)

            // lưu thông tin về bản tin đã đọc (notification-service)
            saveBanTinDaDoc(tinTuc)
        }
    }

    private fun openWebViewBanTin(tinTuc: BanTin) {
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

    private fun saveBanTinDaDoc(tinTuc: BanTin) {
        val userId = DataLocalManager.getInstance().getInfoUserId()
        viewModel.postNewsSave(tinTuc.title, tinTuc.link, tinTuc.img, tinTuc.pubDate, userId)
    }

    override fun getItemCount(): Int {
        return mListTinTuc.size
    }

    fun updateData(newList: List<BanTin>) {
        mListTinTuc = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemRowArticleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val searchString = constraint?.toString()?.toLowerCase() ?: ""
            mListTinTucFiltered = if (searchString.isEmpty()) {
                mListTinTuc.toList()
            } else {
                mListTinTuc.filter {
                    it.title.toLowerCase().contains(searchString)
                }
            }
            return FilterResults().apply {
                values = mListTinTucFiltered // Gán kết quả đã lọc vào values
            }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            mListTinTucFiltered = results.values as List<BanTin>
            notifyDataSetChanged()
        }
    }

}
