package com.hoangtien2k3.news_app.ui.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.data.models.BanTin
import com.hoangtien2k3.news_app.data.models.Football
import com.hoangtien2k3.news_app.databinding.FragmentHomeBinding
import com.hoangtien2k3.news_app.ui.bantin.BanTinFragment
import com.hoangtien2k3.news_app.ui.bantin.BanTinViewModel2
import com.hoangtien2k3.news_app.ui.bantin.adapter.BanTinAdapter
import com.hoangtien2k3.news_app.ui.football.FootballFragment
import com.hoangtien2k3.news_app.ui.football.adapter.FoolballAdapter
import com.hoangtien2k3.news_app.utils.Constants

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var footballAdapter: FoolballAdapter

    private lateinit var mBanTinAdapter: BanTinAdapter
    private lateinit var viewModelBanTin: BanTinViewModel2
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
        floatingTab()

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


        viewModelBanTin = ViewModelProvider(this)[BanTinViewModel2::class.java]
        mListTinTuc = ArrayList()
        mBanTinAdapter = BanTinAdapter(requireContext(), mListTinTuc)
        val gridLayoutManager = GridLayoutManager(requireContext(), 1)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL
        binding.NewsRecycler.layoutManager = gridLayoutManager
        binding.NewsRecycler.adapter = mBanTinAdapter


        binding.recyclerTinTuc.apply {
            setHasFixedSize(true)
            val gridLayoutManager = GridLayoutManager(requireContext(), 1)
            gridLayoutManager.orientation = GridLayoutManager.HORIZONTAL
            layoutManager = gridLayoutManager
            footballAdapter = FoolballAdapter(emptyList(), object : FoolballAdapter.ShowDialoginterface {
                @SuppressLint("SetJavaScriptEnabled")
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


        binding.NewsRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                if (firstVisibleItemPosition == 0) {
                    showAndCloseUI(false)
                } else {
                    showAndCloseUI(true)
                }

            }
        })

    }


    private fun showAndCloseUI(boolean: Boolean) {
        if (boolean) {
            binding.tvFootball.visibility = View.GONE
            binding.detailFootball.visibility = View.GONE
            binding.imageBackFootball.visibility = View.GONE
            binding.recyclerTinTuc.visibility = View.GONE
            binding.fab.setImageResource(R.drawable.ic_arrow_upward_white_24dp)
            binding.fab.setOnClickListener {
                binding.NewsRecycler.smoothScrollToPosition(0);
                binding.recyclerTinTuc.smoothScrollToPosition(0);
                binding.fab.setImageResource(R.drawable.ic_support_chat)
            }
        } else {
            binding.fab.setImageResource(R.drawable.ic_support_chat)
            binding.fab.setOnClickListener {
                if (!isFABOpen)
                    showFABMenu()
                else
                    closeFABMenu()
            }
            binding.tvFootball.visibility = View.VISIBLE
            binding.detailFootball.visibility = View.VISIBLE
            binding.imageBackFootball.visibility = View.VISIBLE
            binding.recyclerTinTuc.visibility = View.VISIBLE
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

//        viewModelBanTin.fetchListTinTuc(Constants.BASE_URL_TIN_NOI_BAT)
//        viewModelBanTin.listTinTuc.observe(viewLifecycleOwner, Observer { tinTucList ->
//            mListTinTuc.clear()
//            mListTinTuc.addAll(tinTucList)
//            mBanTinAdapter.notifyDataSetChanged()
//        })

        viewModelBanTin.fetchListTinTuc()
        viewModelBanTin.listTinTuc.observe(viewLifecycleOwner, Observer { tinTucList ->
            mListTinTuc.clear()
            mListTinTuc.addAll(tinTucList)
            mBanTinAdapter.notifyDataSetChanged()
        })

    }


    var isFABOpen = false
    private fun floatingTab() {
        binding.fab.setOnClickListener {
            if (!isFABOpen) {
                showFABMenu()
            } else {
                closeFABMenu()
            }
        }
        binding.overlay.setOnClickListener {
            closeFABMenu()
            val githubUsername = "hoangtien2k3qx1"
            val githubUrl = "https://github.com/$githubUsername"
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(githubUrl))
            startActivity(browserIntent)
        }
        binding.menuZalo.setOnClickListener {
            closeFABMenu()
            val zaloID = "0828007853"
            val url = "http://zalo.me/$zaloID"
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }
        binding.menuFacebook.setOnClickListener {
            closeFABMenu()
            val facebookID = "103838294312468"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://m.me/$facebookID"))
            startActivity(intent)
        }
        binding.overlay.setOnClickListener {
            closeFABMenu()
        }
    }

    private fun showFABMenu() {
        isFABOpen = true
        binding.fab.hide()
        binding.fab.setImageResource(R.drawable.ic_floating_btn_close)
        binding.fab.show()
        binding.menuGithub.animate().translationY(-resources.getDimension(R.dimen.marginBottom_github))
        binding.menuZalo.animate().translationY(-resources.getDimension(R.dimen.marginBottom_zalo))
        binding.menuFacebook.animate().translationY(-resources.getDimension(R.dimen.marginBottom_facebook))
        binding.menuGithub.visibility = View.VISIBLE
        binding.menuZalo.visibility = View.VISIBLE
        binding.menuFacebook.visibility = View.VISIBLE
        binding.overlay.visibility = View.VISIBLE
    }

    private fun closeFABMenu() {
        isFABOpen = false
        binding.menuGithub.animate().translationY(0F)
        binding.menuZalo.animate().translationY(0F)
        binding.menuFacebook.animate().translationY(0F)
        binding.menuGithub.visibility = View.GONE
        binding.menuZalo.visibility = View.GONE
        binding.menuFacebook.visibility = View.GONE
        binding.overlay.visibility = View.GONE
        binding.fab.hide()
        binding.fab.setImageResource(R.drawable.ic_support_chat)
        binding.fab.show()
    }


    private fun loadFragment(fragmentReplace: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment, fragmentReplace)
            .addToBackStack("HomeFragment")
            .commit()
    }

}

