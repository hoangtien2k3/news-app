package com.hoangtien2k3.news_app.ui.bantin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.databinding.FragmentBanTinBinding
import com.hoangtien2k3.news_app.ui.save.SaveBanTinViewModel
import com.hoangtien2k3.news_app.ui.save.ViewModelProviderFactory
import com.hoangtien2k3.news_app.utils.viewBinding

class BanTinFragment(
    private val category: String
) : Fragment(R.layout.fragment_ban_tin), ViewModelProviderFactory {
    private val binding by viewBinding(FragmentBanTinBinding::bind)
    private val viewModel: BanTinViewModel by viewModels()
    private lateinit var mBanTinAdapter: BanTinAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observerDataCallAPI()
        onclickListenerRecyclerView()
    }

    private fun initRecyclerView() {
        mBanTinAdapter = BanTinAdapter(requireContext(), mutableListOf(), this)
        val gridLayoutManager = GridLayoutManager(requireContext(), 1)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL
        with(binding) {
            banTinRecyclerView.layoutManager = gridLayoutManager
            banTinRecyclerView.adapter = mBanTinAdapter
        }
    }

    private fun observerDataCallAPI() {
        with(viewModel) {
            fetchDataCallAPI(category)
            listTinTuc.observe(viewLifecycleOwner) { banTin ->
                banTin?.let {
                    it.data?.let {
                            it1 -> mBanTinAdapter.updateData(it1.data)
                    }
                }
            }
        }
    }

    private fun onclickListenerRecyclerView() {
        with(binding) {
            fab.visibility = View.GONE
            banTinRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
    }

    override fun provideViewModel(): SaveBanTinViewModel {
        return ViewModelProvider(this)[SaveBanTinViewModel::class.java]
    }

}