package com.hoangtien2k3.news_app.ui.home.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ImageView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.data.models.BanTin
import com.hoangtien2k3.news_app.data.models.Football
import com.hoangtien2k3.news_app.data.sharedpreferences.DataLocalManager
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
import com.hoangtien2k3.news_app.utils.viewBinding

class HomeFragment : Fragment(R.layout.fragment_home), ViewModelProviderFactory {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModelHome: HomeViewModel by viewModels()
    private val viewModelFootball: FootballViewModel by viewModels()
    private val viewModelBanTin: BanTinViewModel by viewModels()
    private lateinit var footballAdapter: FoolballAdapter
    private lateinit var mBanTinAdapter: BanTinAdapter
    private lateinit var mListTinTuc: ArrayList<BanTin>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeViewModel()
        floatingTab()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupUI() {
        initUIRecyclerViewOn()
        changeOnScrollListenerNestedScrollView()
        setOnClickListenerByFootball()
    }

    private fun initUIRecyclerViewOn() {
        mListTinTuc = ArrayList()
        mBanTinAdapter = BanTinAdapter(requireContext(), mListTinTuc, this)
        val gridLayoutManager = GridLayoutManager(requireContext(), 1)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL

        with(binding) {
            NewsRecycler.layoutManager = gridLayoutManager
            NewsRecycler.adapter = mBanTinAdapter
            recyclerTinTuc.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false)
                footballAdapter = FoolballAdapter(emptyList(), object : FoolballAdapter.ShowDialoginterface {
                    override fun itemClik(hero: Football) {
                        openDialogFootball(hero)
                    }
                })
                adapter = footballAdapter
            }
        }
    }

    private fun changeOnScrollListenerNestedScrollView() {
        binding.nestedScrollView.setOnScrollChangeListener(
            NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
                if (scrollY > oldScrollY) {
                    showAndCloseUI(true)
                } else {
                    showAndCloseUI(false)
                }
            })
    }

    private fun setOnClickListenerByFootball() {
        with(binding) {
            detailFootball.setOnClickListener { loadFragment(FootballFragment()) }
            detailHot.setOnClickListener {
                val bundle = Bundle().apply {
                    putString("close", "false")
                }
                val fragment = SearchBanTinFragment(Constants.full)
                fragment.arguments = bundle
                loadFragment(fragment)
            }
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
        observerFootball()
        observerBanTin()
        observerHome()
    }

    private fun observerFootball() {
        viewModelFootball.footballNews.observe(viewLifecycleOwner) { footballNews ->
            footballNews?.let {
                if (::footballAdapter.isInitialized) {
                    it.data?.let {
                            it1 -> footballAdapter.updateData(it1.data)
                    }
                }
            }
        }
    }

    private fun observerBanTin() {
        viewModelBanTin.fetchDataCallAPI("tin-noi-bat")
        viewModelBanTin.listTinTuc.observe(viewLifecycleOwner) { tinTucList ->
            mListTinTuc.clear()
            tinTucList.data?.let {
                mListTinTuc.addAll(it.data)
            }
            mBanTinAdapter.notifyDataSetChanged()
        }
    }

    private fun observerHome() {
        viewModelHome.accessTokenKey.observe(viewLifecycleOwner) { accessTokenKey ->
            accessTokenKey?.let {
                it.data?.data?.let { info ->
                    DataLocalManager.getInstance().setSaveUserInfo(
                        info.id,
                        info.name,
                        info.username,
                        info.email,
                        info.role.first()
                    )
                }
            }
        }
    }

    var isFABOpen = false
    private fun floatingTab() {
        with(binding) {
            fab.setOnClickListener {
                if (!isFABOpen) {
                    showFABMenu()
                } else {
                    closeFABMenu()
                }
            }
            overlay.setOnClickListener {
                closeFABMenu()
                val githubUsername = "hoangtien2k3qx1"
                val githubUrl = "https://github.com/$githubUsername"
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(githubUrl))
                startActivity(browserIntent)
            }
            menuZalo.setOnClickListener {
                closeFABMenu()
                val zaloID = "0828007853"
                val url = "http://zalo.me/$zaloID"
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(browserIntent)
            }
            menuFacebook.setOnClickListener {
                closeFABMenu()
                val facebookID = "100053705482952"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://m.me/$facebookID"))
                startActivity(intent)
            }
            binding.overlay.setOnClickListener {
                closeFABMenu()
            }
        }
    }

    private fun showFABMenu() {
        isFABOpen = true
        with(binding) {
            fab.hide()
            fab.setImageResource(R.drawable.ic_floating_btn_close)
            fab.show()
            menuZalo.animate().translationY(-resources.getDimension(R.dimen.marginBottom_zalo))
            menuFacebook.animate().translationY(-resources.getDimension(R.dimen.marginBottom_facebook))
            menuZalo.visibility = View.VISIBLE
            menuFacebook.visibility = View.VISIBLE
            overlay.visibility = View.VISIBLE
        }
    }

    private fun closeFABMenu() {
        isFABOpen = false
        with(binding) {
            menuZalo.animate().translationY(0F)
            menuFacebook.animate().translationY(0F)
            menuZalo.visibility = View.GONE
            menuFacebook.visibility = View.GONE
            overlay.visibility = View.GONE
            fab.hide()
            fab.setImageResource(R.drawable.ic_support_chat)
            fab.show()
        }
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

