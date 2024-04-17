package com.hoangtien2k3.news_app.ui.search.bantin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.data.models.BanTin
import com.hoangtien2k3.news_app.databinding.FragmentSearchBanTinBinding
import com.hoangtien2k3.news_app.ui.bantin.BanTinAdapter
import com.hoangtien2k3.news_app.ui.bantin.BanTinViewModel
import com.hoangtien2k3.news_app.ui.save.SaveBanTinViewModel
import com.hoangtien2k3.news_app.ui.save.ViewModelProviderFactory
import com.hoangtien2k3.news_app.utils.Constants
import com.hoangtien2k3.news_app.utils.viewBinding
import java.util.ArrayList
import java.util.Locale

class SearchBanTinFragment(
    private val category: String
) : Fragment(R.layout.fragment_search_ban_tin), ViewModelProviderFactory {
    private val binding by viewBinding(FragmentSearchBanTinBinding::bind)
    private val viewModel: BanTinViewModel by viewModels()
    private lateinit var mBanTinAdapter: BanTinAdapter
    private lateinit var listBanTin: List<BanTin>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        actionUI()
        observeViewModel()
    }

    private fun initUI(){
        mBanTinAdapter = BanTinAdapter(requireContext(), mutableListOf(), this)
        val gridLayoutManager = GridLayoutManager(requireContext(), 1)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL
        with(binding) {
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = gridLayoutManager
            recyclerView.adapter = mBanTinAdapter
        }
    }

    private fun actionUI() {
        with(binding) {
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    filterList(newText)
                    return true
                }
            })

            // close bottom back
            val receivedValue = arguments?.getString("close")
            Log.d("Close", receivedValue.toString())
            if (receivedValue == "false") {
                back.visibility = View.VISIBLE
                back.setOnClickListener { requireActivity().onBackPressed() }
            } else {
                back.setImageResource(R.drawable.google)
                back.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(Constants.google_link)
                    startActivity(intent)
                }
            }
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            fetchDataCallAPI(category)
            listTinTuc.observe(viewLifecycleOwner) { banTin ->
                banTin?.let {
                    it.data?.let {
                            it1 -> mBanTinAdapter.updateData(it1.data)
                    }
                    listBanTin = it.data?.data ?: emptyList()
                }
            }
        }
    }

    private fun filterList(query: String?) {
        if (query != null) {
            val filteredList = ArrayList<BanTin>()
            for (i in listBanTin) {
                if (i.title.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }
            mBanTinAdapter.updateData(filteredList)
        }
    }

    override fun provideViewModel(): SaveBanTinViewModel {
        return ViewModelProvider(this)[SaveBanTinViewModel::class.java]
    }

}