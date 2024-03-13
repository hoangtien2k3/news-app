package com.hoangtien2k3.news_app.ui.home.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ImageView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.data.models.BanTin
import com.hoangtien2k3.news_app.data.models.Football
import com.hoangtien2k3.news_app.databinding.FragmentHomeBinding
import com.hoangtien2k3.news_app.ui.bantin.BanTinViewModel
import com.hoangtien2k3.news_app.ui.bantin.BanTinAdapter
import com.hoangtien2k3.news_app.ui.football.FootballFragment
import com.hoangtien2k3.news_app.ui.football.FoolballAdapter
import com.hoangtien2k3.news_app.ui.football.FootballViewModel
import com.hoangtien2k3.news_app.ui.save.SaveBanTinViewModel
import com.hoangtien2k3.news_app.ui.save.ViewModelProviderFactory
import com.hoangtien2k3.news_app.ui.search.bantin.SearchBanTinFragment
import com.hoangtien2k3.news_app.utils.Constants

var ViewPager2.isUserInputEnabled: Boolean
    get() = true // Mặc định là true
    @SuppressLint("ClickableViewAccessibility")
    set(value) {
        // Override phương thức onTouchEvent để kiểm tra giá trị của biến isSwipeEnabled
        val pager = this
        pager.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN ->
                    // Chặn sự kiện vuốt nếu isSwipeEnabled là false
                    !value
                else -> false
            }
        }
    }


class HomeFragment : Fragment(), ViewModelProviderFactory {
    private lateinit var viewModelFootball: FootballViewModel
    private lateinit var binding: FragmentHomeBinding
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

        viewModelFootball = ViewModelProvider(this)[FootballViewModel::class.java]
        viewModelBanTin = ViewModelProvider(this)[BanTinViewModel::class.java]

        setupUI()
        observeViewModel()
        floatingTab()

        return rootView
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupUI() {
        // tin tức trong ngày
        mListTinTuc = ArrayList()
        mBanTinAdapter = BanTinAdapter(requireContext(), mListTinTuc, this)
        val gridLayoutManager = GridLayoutManager(requireContext(), 1)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL
        binding.NewsRecycler.layoutManager = gridLayoutManager
        binding.NewsRecycler.adapter = mBanTinAdapter

        // tin tức bóng đá
        binding.recyclerTinTuc.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false)
            footballAdapter = FoolballAdapter(emptyList(), object : FoolballAdapter.ShowDialoginterface {
                override fun itemClik(hero: Football) {
                    openDialogFootball(hero)
                }
            })
            adapter = footballAdapter
        }

        // crollview lên đầu
        binding.nestedScrollView.setOnScrollChangeListener(
            NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY > oldScrollY) {
                showAndCloseUI(true)
            } else {
                showAndCloseUI(false)
            }
        })

        binding.detailFootball.setOnClickListener { loadFragment(FootballFragment()) }
        binding.detailHot.setOnClickListener {
            val bundle = Bundle().apply {
                putString("close", "false")
            }
            val fragment = SearchBanTinFragment(Constants.full)
            fragment.arguments = bundle
            loadFragment(fragment)
        }

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun openDialogFootball(hero: Football) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_web_show, null)
        dialogBuilder.setView(dialogView)

        val mWebView: WebView = dialogView.findViewById(R.id.WebConnect)
        val back: ImageView = dialogView.findViewById(R.id.back)

        val webSettings: WebSettings = mWebView.settings
        webSettings.javaScriptEnabled = true
        mWebView.loadUrl(hero.url)

        val alertDialog: AlertDialog = dialogBuilder.create()
        back.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    private fun showAndCloseUI(boolean: Boolean) {
        if (boolean) {
            binding.apply {
                fab.setImageResource(R.drawable.ic_arrow_upward_white_24dp)
                fab.setOnClickListener {
                    nestedScrollView.smoothScrollTo(0, 0)
                    recyclerTinTuc.smoothScrollToPosition(0);
                }
            }
        } else {
            val isAtTop = !binding.nestedScrollView.canScrollVertically(-1)
            if (isAtTop) {
                binding.apply {
                    fab.setImageResource(R.drawable.ic_support_chat)
                    fab.setOnClickListener {
                        if (!isFABOpen) showFABMenu()
                        else closeFABMenu()
                    }
                }
            }
        }
    }

    private fun observeViewModel() {
        viewModelFootball.footballNews.observe(viewLifecycleOwner) { footballNews ->
            footballNews?.let {
                if (::footballAdapter.isInitialized) {
                    footballAdapter.updateData(it)
                }
            }
        }

        viewModelBanTin.fetchListTinTuc("tin-noi-bat")
        viewModelBanTin.listTinTuc.observe(viewLifecycleOwner) { tinTucList ->
            mListTinTuc.clear()
            mListTinTuc.addAll(tinTucList)
            mBanTinAdapter.notifyDataSetChanged()
        }
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
            val facebookID = "100053705482952"
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
        binding.menuZalo.animate().translationY(-resources.getDimension(R.dimen.marginBottom_zalo))
        binding.menuFacebook.animate().translationY(-resources.getDimension(R.dimen.marginBottom_facebook))
        binding.menuZalo.visibility = View.VISIBLE
        binding.menuFacebook.visibility = View.VISIBLE
        binding.overlay.visibility = View.VISIBLE
    }

    private fun closeFABMenu() {
        isFABOpen = false
        binding.menuZalo.animate().translationY(0F)
        binding.menuFacebook.animate().translationY(0F)
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

    override fun provideViewModel(): SaveBanTinViewModel {
        return ViewModelProvider(this)[SaveBanTinViewModel::class.java]
    }

}

