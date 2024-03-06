package com.hoangtien2k3.news_app.ui.bantin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.hoangtien2k3.news_app.databinding.FragmentBanTinBinding
import com.hoangtien2k3.news_app.ui.bantin.adapter.BanTinAdapter

class BanTinFragment2 : Fragment() {
    private var _binding: FragmentBanTinBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var mBanTinAdapter: BanTinAdapter
    private lateinit var viewModel: BanTinViewModel2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBanTinBinding.inflate(inflater, container, false)
        val rootView = binding.root

        viewModel = ViewModelProvider(this)[BanTinViewModel2::class.java]

        mBanTinAdapter = BanTinAdapter(requireContext(), mutableListOf())
        val gridLayoutManager = GridLayoutManager(requireContext(), 1)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL
        binding.banTinRecyclerView.layoutManager = gridLayoutManager
        binding.banTinRecyclerView.adapter = mBanTinAdapter


        viewModel.listTinTuc.observe(viewLifecycleOwner, Observer { banTin->
            banTin?.let {
                mBanTinAdapter.updateData(it)
            }
        })

        binding.back.setOnClickListener { requireActivity().onBackPressed() }

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}