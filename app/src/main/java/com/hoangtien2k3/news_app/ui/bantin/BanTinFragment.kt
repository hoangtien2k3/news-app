package com.hoangtien2k3.news_app.ui.bantin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.hoangtien2k3.news_app.data.models.BanTin
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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

        val mListTinTuc = ArrayList<BanTin>()
        mBanTinAdapter = BanTinAdapter(requireContext(), mListTinTuc)

        // Cài đặt layout cho RecyclerView
        val gridLayoutManager = GridLayoutManager(requireContext(), 1)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL
        binding.banTinRecyclerView.layoutManager = gridLayoutManager
        binding.banTinRecyclerView.adapter = mBanTinAdapter


        val bundle = arguments
        val category: String? = bundle?.getString("category")
        val title: String? = bundle?.getString("title")
        binding.title.text = title
        if (category != null) {
            viewModel.fetchListTinTuc(category)
        }
        viewModel.listTinTuc.observe(viewLifecycleOwner, Observer { tinTucList ->
            mListTinTuc.clear()
            mListTinTuc.addAll(tinTucList)
            mBanTinAdapter.notifyDataSetChanged()
        })


        // back về fragment ban đầu
        binding.back.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
