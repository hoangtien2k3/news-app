package com.hoangtien2k3.news_app.ui.save

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.hoangtien2k3.news_app.databinding.FragmentBanTinSaveBinding

class SaveBanTinFragment : Fragment() {
    private var _binding: FragmentBanTinSaveBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var mBanTinAdapter: SaveBanTinAdapter
    private lateinit var viewModel: SaveBanTinViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBanTinSaveBinding.inflate(inflater, container, false)
        val rootView = binding.root

        viewModel = ViewModelProvider(this)[SaveBanTinViewModel::class.java]
        mBanTinAdapter = SaveBanTinAdapter(requireContext(), mutableListOf())
        val gridLayoutManager = GridLayoutManager(requireContext(), 1)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL
        binding.banTinRecyclerView.layoutManager = gridLayoutManager
        binding.banTinRecyclerView.adapter = mBanTinAdapter

        viewModel.getListAllNewsSave()
        viewModel.getSaveBanTin.observe(viewLifecycleOwner) { banTin ->
            banTin?.let {
                mBanTinAdapter.updateData(it)
            }
        }

        binding.btnBack.setOnClickListener { requireActivity().onBackPressed() }
        binding.btnDeleteAll.setOnClickListener {
            viewModel.deleteAllListNewsSave() // delete tất cả bản tin trong danh mục đã đọc
            viewModel.getListAllNewsSave() // load lại tin tức
        }

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}