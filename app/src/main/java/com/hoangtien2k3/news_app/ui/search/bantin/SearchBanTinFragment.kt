package com.hoangtien2k3.news_app.ui.search.bantin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.data.models.BanTin
import com.hoangtien2k3.news_app.databinding.FragmentSearchBanTinBinding
import com.hoangtien2k3.news_app.ui.bantin.BanTinAdapter
import com.hoangtien2k3.news_app.ui.bantin.BanTinViewModel
import com.hoangtien2k3.news_app.ui.menu.MenuFragment
import com.hoangtien2k3.news_app.ui.save.SaveBanTinViewModel
import com.hoangtien2k3.news_app.ui.save.ViewModelProviderFactory
import java.util.ArrayList
import java.util.Locale

class SearchBanTinFragment(
    private val category: String
) : Fragment(), ViewModelProviderFactory {
    private var _binding: FragmentSearchBanTinBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var mBanTinAdapter: BanTinAdapter
    private lateinit var viewModel: BanTinViewModel
    private lateinit var listBanTin: List<BanTin>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBanTinBinding.inflate(inflater, container, false)
        val rootView = binding.root

        viewModel = ViewModelProvider(this)[BanTinViewModel::class.java]
        mBanTinAdapter = BanTinAdapter(requireContext(), mutableListOf(), this)

        val gridLayoutManager = GridLayoutManager(requireContext(), 1)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = gridLayoutManager
        binding.recyclerView.adapter = mBanTinAdapter

        viewModel.fetchListTinTuc(category)
        viewModel.listTinTuc.observe(viewLifecycleOwner) { banTin ->
            banTin?.let {
                mBanTinAdapter.updateData(it)
                listBanTin = it
            }
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
            binding.back.visibility = View.VISIBLE
            binding.back.setOnClickListener { requireActivity().onBackPressed() }
        } else {
            binding.back.setImageResource(R.drawable.ic_support_chat)
        }

        return rootView
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun provideViewModel(): SaveBanTinViewModel {
        return ViewModelProvider(this)[SaveBanTinViewModel::class.java]
    }

}