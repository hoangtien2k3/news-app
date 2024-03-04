package com.hoangtien2k3.news_app.ui.home

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ImageView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.hoangtien2k3.news_app.R
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.hoangtien2k3.news_app.data.models.BanTin
import com.hoangtien2k3.news_app.ui.football.adapter.FoolballAdapter
import com.hoangtien2k3.news_app.data.models.Football
import com.hoangtien2k3.news_app.databinding.FragmentHomeBinding
import com.hoangtien2k3.news_app.ui.bantin.BanTinFragment
import com.hoangtien2k3.news_app.ui.bantin.BanTinViewModel
import com.hoangtien2k3.news_app.ui.bantin.adapter.BanTinAdapter
import com.hoangtien2k3.news_app.ui.football.FootballFragment
import com.hoangtien2k3.news_app.ui.search.SearchNewsFragment
import com.hoangtien2k3.news_app.utils.Constants

class HomeFragment : Fragment(){
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var footballAdapter: FoolballAdapter
    private lateinit var mBanTinAdapter: BanTinAdapter
    private lateinit var viewModelBanTin: BanTinViewModel
    private lateinit var mListTinTuc: ArrayList<BanTin>
    private var isFirst = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val rootView = binding.root

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        setupUI()
        observeViewModel()
        isFirst = true

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isFirst) floatingTab()
    }


    private fun setupUI() {
        // hiện thị các danh mục
        categoryAdapter = CategoryAdapter(requireContext(), emptyList())
        binding.danhMuc.apply {
            layoutManager = GridLayoutManager(requireContext(), 1).apply {
                orientation = GridLayoutManager.HORIZONTAL
            }
            adapter = categoryAdapter
        }


        // hiển thị recyclerView bản tin
        viewModelBanTin = ViewModelProvider(this)[BanTinViewModel::class.java]
        mListTinTuc = ArrayList()
        mBanTinAdapter = BanTinAdapter(requireContext(), mListTinTuc)
        val gridLayoutManager = GridLayoutManager(requireContext(), 1)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL
        binding.NewsRecycler.layoutManager = gridLayoutManager
        binding.NewsRecycler.adapter = mBanTinAdapter


        // hiển thị recyclerView Tin bóng đá
        binding.recyclerTinTuc.apply {
            setHasFixedSize(true)
            val gridLayoutManager = GridLayoutManager(requireContext(), 1)
            gridLayoutManager.orientation = GridLayoutManager.HORIZONTAL
            layoutManager = gridLayoutManager
            footballAdapter = FoolballAdapter(
                requireContext(),
                emptyList(),
                object : FoolballAdapter.ShowDialoginterface {
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


//        binding.search.setOnClickListener {
//            loadFragment(SearchNewsFragment())
//        }
        binding.detailFootball.setOnClickListener {
            loadFragment(FootballFragment())
        }
        binding.detailHot.setOnClickListener {
            val banTinFragment = BanTinFragment()
            val bundle = Bundle().apply {
                putString("url", Constants.BASE_URL_TIN_NOI_BAT)
                putString("title", "Nổi Bật")
            }
            banTinFragment.arguments = bundle
            loadFragment(banTinFragment)
        }

        // Khởi tạo RecyclerView.OnScrollListener để ẩn và hiển thị recyclerTinTuc khi cuộn
        binding.NewsRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (firstVisibleItemPosition == 0) {
                    animateViews(true)
                } else {
                    animateViews(false)
                }
            }
        })

    }


    private var isFABOpen = false
    private fun floatingTab() {
        binding.fab.setOnClickListener {
            if (!isFABOpen) {
                showFABMenu()
                binding.overlay.visibility = View.VISIBLE
                binding.overlay.setOnClickListener {
                    closeFABMenu()
                    binding.overlay.visibility = View.GONE
                }
            } else {
                closeFABMenu()
                binding.overlay.visibility = View.GONE
            }
        }
        binding.menuZalo.setOnClickListener {
            closeFABMenu()
            val zaloID = "0828007853"
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://zalo.me/$zaloID"))
            startActivity(browserIntent)
        }
        binding.menuFacebook.setOnClickListener {
            closeFABMenu()
            val facebookID = "109216984946230"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://m.me/$facebookID"))
            startActivity(intent)
        }
    }

    private fun showFABMenu() {
        isFABOpen = true
        binding.fab.apply {
            hide()
            setImageResource(R.drawable.ic_floating_btn_close)
            show()
        }
        binding.menuZalo.apply {
            animate().translationY(-resources.getDimension(R.dimen.marginBottom_zalo))
            visibility = View.VISIBLE
        }
        binding.menuFacebook.apply {
            animate().translationY(-resources.getDimension(R.dimen.marginBottom_facebook))
            visibility = View.VISIBLE
        }
    }

    private fun closeFABMenu() {
        isFABOpen = false
        binding.menuFacebook.apply {
            animate().translationY(0F)
            visibility = View.GONE
        }
        binding.menuZalo.apply {
            animate().translationY(0F)
            visibility = View.GONE
        }
        binding.fab.apply {
            hide()
            setImageResource(R.drawable.ic_support_chat)
            show()
        }
    }

    private fun animateViews(shouldShow: Boolean) {
        if (shouldShow) {
            binding.recyclerTinTuc.visibility = View.VISIBLE
            binding.tvFootball.visibility = View.VISIBLE
            binding.detailFootball.visibility = View.VISIBLE
            binding.imageBackFootball.visibility = View.VISIBLE

            binding.fab.apply {
                setImageResource(R.drawable.ic_support_chat)
                setOnClickListener {
                    if (!isFABOpen) {
                        showFABMenu()
                        binding.overlay.visibility = View.VISIBLE
                        binding.overlay.setOnClickListener {
                            closeFABMenu()
                            binding.overlay.visibility = View.GONE
                        }
                    } else {
                        closeFABMenu()
                        binding.overlay.visibility = View.GONE
                    }
                }
            }

        } else {
            binding.recyclerTinTuc.visibility = View.GONE
            binding.tvFootball.visibility = View.GONE
            binding.detailFootball.visibility = View.GONE
            binding.imageBackFootball.visibility = View.GONE

            binding.fab.apply {
                visibility = View.VISIBLE
                setImageResource(R.drawable.ic_arrow_upward_white_24dp)
                setOnClickListener {
                    binding.NewsRecycler.smoothScrollToPosition(0)
                    binding.recyclerTinTuc.smoothScrollToPosition(0)
                }
            }
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

