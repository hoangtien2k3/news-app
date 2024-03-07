package com.hoangtien2k3.news_app.ui.bantin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.data.models.BanTin
import com.hoangtien2k3.news_app.databinding.FragmentBanTinBinding
import com.hoangtien2k3.news_app.ui.bantin.adapter.BanTinAdapter

class BanTinFragment : Fragment() {
    private var _binding: FragmentBanTinBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var mBanTinAdapter: BanTinAdapter
    private lateinit var viewModel: BanTinViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBanTinBinding.inflate(inflater, container, false)
        val rootView = binding.root

        viewModel = ViewModelProvider(this)[BanTinViewModel::class.java]
        mBanTinAdapter = BanTinAdapter(requireContext(), mutableListOf())
        val gridLayoutManager = GridLayoutManager(requireContext(), 1)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL
        binding.banTinRecyclerView.layoutManager = gridLayoutManager
        binding.banTinRecyclerView.adapter = mBanTinAdapter


        // getString in bundle -> CategoryAdapter
        val bundle = arguments
        val title: String? = bundle?.getString("title")
        binding.title.text = title
        val category: String? = bundle?.getString("category")
        if (category != null) {
            viewModel.fetchListTinTuc(category)
        }
        viewModel.listTinTuc.observe(viewLifecycleOwner) { banTin ->
            banTin?.let {
                mBanTinAdapter.updateData(it)
            }
        }

        binding.fab.visibility = View.GONE
        binding.back.setOnClickListener { requireActivity().onBackPressed() }
        binding.banTinRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    binding.fab.visibility = View.VISIBLE
                    binding.fab.setImageResource(R.drawable.ic_arrow_upward_white_24dp)
                    binding.fab.setOnClickListener {
                        binding.banTinRecyclerView.smoothScrollToPosition(0);
                        binding.fab.visibility = View.GONE
                    }
                }
            }
        })

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}