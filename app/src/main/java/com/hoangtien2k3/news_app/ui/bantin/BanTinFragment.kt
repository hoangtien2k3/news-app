package com.hoangtien2k3.news_app.ui.bantin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hoangtien2k3.news_app.databinding.FragmentBanTinBinding
import com.hoangtien2k3.news_app.ui.save.SaveBanTinViewModel
import com.hoangtien2k3.news_app.ui.save.ViewModelProviderFactory

class BanTinFragment(
    private val category: String
) : Fragment(), ViewModelProviderFactory {
    private var _binding: FragmentBanTinBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var mBanTinAdapter: BanTinAdapter
    private lateinit var viewModel: BanTinViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBanTinBinding.inflate(inflater, container, false)
        val rootView = binding.root

        initRecyclerView()
        observerDataCallAPI()
        onclickListenerRecyclerView()

        return rootView
    }

    private fun initRecyclerView() {
        viewModel = ViewModelProvider(this)[BanTinViewModel::class.java]
        mBanTinAdapter = BanTinAdapter(requireContext(), mutableListOf(), this)
        val gridLayoutManager = GridLayoutManager(requireContext(), 1)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL
        binding.banTinRecyclerView.layoutManager = gridLayoutManager
        binding.banTinRecyclerView.adapter = mBanTinAdapter
    }

    private fun observerDataCallAPI() {
        viewModel.fetchDataCallAPI(category)
        viewModel.listTinTuc.observe(viewLifecycleOwner) { banTin ->
            banTin?.let {
                it.data?.let {
                    it1 -> mBanTinAdapter.updateData(it1.data)
                }
            }
        }
    }

    private fun onclickListenerRecyclerView() {
        binding.fab.visibility = View.GONE
        binding.banTinRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                if (firstVisibleItemPosition == 0) {
                    binding.fab.visibility = View.GONE
                } else {
                    binding.fab.visibility = View.VISIBLE
                    binding.fab.setOnClickListener {
                        binding.banTinRecyclerView.smoothScrollToPosition(0)
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun provideViewModel(): SaveBanTinViewModel {
        return ViewModelProvider(this)[SaveBanTinViewModel::class.java]
    }

}