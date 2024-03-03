package com.hoangtien2k3.news_app.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.databinding.FragmentHomeBinding
import androidx.lifecycle.ViewModelProvider
import com.hoangtien2k3.news_app.data.models.BanTin
import com.hoangtien2k3.news_app.ui.football.adapter.FoolballAdapter
import com.hoangtien2k3.news_app.data.models.Football
import com.hoangtien2k3.news_app.ui.bantin.BanTinFragment
import com.hoangtien2k3.news_app.ui.bantin.BanTinViewModel
import com.hoangtien2k3.news_app.ui.bantin.adapter.BanTinAdapter
import com.hoangtien2k3.news_app.ui.football.FootballFragment
import com.hoangtien2k3.news_app.ui.search.SearchNewsFragment
import com.hoangtien2k3.news_app.utils.Constants

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var footballAdapter: FoolballAdapter


    private lateinit var mBanTinAdapter: BanTinAdapter
    private lateinit var viewModelBanTin: BanTinViewModel
    private lateinit var mListTinTuc: ArrayList<BanTin>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val rootView = binding.root

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        setupUI()
        observeViewModel()

        return rootView
    }

    private fun setupUI() {
        categoryAdapter = CategoryAdapter(requireContext(), emptyList())
        binding.danhMuc.apply {
            layoutManager = GridLayoutManager(requireContext(), 1).apply {
                orientation = GridLayoutManager.HORIZONTAL
            }
            adapter = categoryAdapter
        }


        binding.search.setOnClickListener {
            loadFragment(SearchNewsFragment())
        }
        binding.detail1.setOnClickListener {
            loadFragment(FootballFragment())
        }
        binding.detail2.setOnClickListener {
            val banTinFragment = BanTinFragment()
            val bundle = Bundle().apply {
                putString("url", Constants.BASE_URL_TIN_NOI_BAT)
                putString("title", "Nổi Bật")
            }
            banTinFragment.arguments = bundle
            loadFragment(banTinFragment)
        }


        // SHOW BAN TIN
        viewModelBanTin = ViewModelProvider(this)[BanTinViewModel::class.java]
        mListTinTuc = ArrayList()
        mBanTinAdapter = BanTinAdapter(requireContext(), mListTinTuc)
        val gridLayoutManager = GridLayoutManager(requireContext(), 1)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL
        binding.NewsRecycler.layoutManager = gridLayoutManager
        binding.NewsRecycler.adapter = mBanTinAdapter



        // Set up RecyclerView for news
        binding.recyclerTinTuc.apply {
            setHasFixedSize(true)
            val gridLayoutManager = GridLayoutManager(requireContext(), 1)
            gridLayoutManager.orientation = GridLayoutManager.HORIZONTAL
            layoutManager = gridLayoutManager
            footballAdapter = FoolballAdapter(requireContext(), emptyList(), object : FoolballAdapter.ShowDialoginterface {
                override fun itemClik(hero: Football) {
                    val dialogBuilder = AlertDialog.Builder(requireContext())
                    val inflater = layoutInflater
                    val dialogView = inflater.inflate(R.layout.web_show, null)
                    dialogBuilder.setView(dialogView)

                    val mWebView: WebView = dialogView.findViewById(R.id.WebConnect)
                    val button: ImageView = dialogView.findViewById(R.id.ok)

                    val webSettings: WebSettings = mWebView.settings
                    webSettings.javaScriptEnabled = true

                    mWebView.loadData(hero.embed, "text/html", "UTF-8")

                    val alertDialog: AlertDialog = dialogBuilder.create()
                    button.setOnClickListener {
                        alertDialog.dismiss()
                    }
                    alertDialog.show()
                }
            })
            adapter = footballAdapter
        }


    }

    private fun observeViewModel() {
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            categories?.let {
                categoryAdapter.updateData(it)
            }
        }

        viewModel.footballNews.observe(viewLifecycleOwner) { footballNews ->
            footballNews?.let {
                if (::footballAdapter.isInitialized) {
                    footballAdapter.updateData(it)
                    binding.swipeLayout.isRefreshing = false
                }
            }
        }

        viewModelBanTin.fetchListTinTuc(Constants.BASE_URL_TIN_NOI_BAT)
        viewModelBanTin.listTinTuc.observe(viewLifecycleOwner, Observer { tinTucList ->
            mListTinTuc.clear()
            mListTinTuc.addAll(tinTucList)
            mBanTinAdapter.notifyDataSetChanged()
        })

    }


    private fun loadFragment(fragmentReplace: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment, fragmentReplace)
            .addToBackStack("HomeFragment")
            .commit()
    }
}

