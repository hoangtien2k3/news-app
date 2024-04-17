package com.hoangtien2k3.news_app.ui.save

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.databinding.FragmentBanTinSaveBinding
import com.hoangtien2k3.news_app.ui.menu.MenuFragment
import com.hoangtien2k3.news_app.utils.viewBinding

class SaveBanTinFragment : Fragment(R.layout.fragment_ban_tin_save) {
    private val binding by viewBinding(FragmentBanTinSaveBinding::bind)
    private val viewModel: SaveBanTinViewModel by viewModels()
    private lateinit var mBanTinAdapter: SaveBanTinAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        observeViewModel()
    }

    private fun initUI() {
        mBanTinAdapter = SaveBanTinAdapter(requireContext(), mutableListOf())
        val gridLayoutManager = GridLayoutManager(requireContext(), 1)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL

        with(binding) {
            banTinRecyclerView.layoutManager = gridLayoutManager
            banTinRecyclerView.adapter = mBanTinAdapter

            val receivedValue = arguments?.getString("close")
            if (receivedValue == "false") {
                btnBack.setOnClickListener { requireActivity().onBackPressed() }
            } else {
                btnBack.setBackgroundResource(R.drawable.ic_setting)
                btnBack.setOnClickListener { loadFragment(MenuFragment()) }
            }
            btnDeleteAll.setOnClickListener {
                viewModel.deleteAllListNewsSave() // delete tất cả bản tin trong danh mục đã đọc
                viewModel.getListAllNewsSave() // load lại tin tức
            }
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            getListAllNewsSave()
            getSaveBanTin.observe(viewLifecycleOwner) { banTin ->
                banTin?.let {
                    it.data?.let {
                        it1 -> mBanTinAdapter.updateData(it1)
                    }
                }
            }
        }
    }

    private fun loadFragment(fragmentReplace: Fragment) {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment, fragmentReplace)
            .addToBackStack("MainFragment")
            .commit()
    }

}