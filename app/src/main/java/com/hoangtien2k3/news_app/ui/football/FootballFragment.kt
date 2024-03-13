package com.hoangtien2k3.news_app.ui.football

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.data.models.Football
import java.util.ArrayList
import java.util.Locale

class FootballFragment : Fragment() {
    private lateinit var newsRecycler: RecyclerView
    private lateinit var adapter: FoolballAdapter
    private lateinit var model: FootballViewModel
    private lateinit var swipeRefreshLayoutNews: SwipeRefreshLayout
    private lateinit var searchView: SearchView
    private lateinit var listFootball: List<Football>
    private lateinit var back: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_football, container, false)
        swipeRefreshLayoutNews = rootView.findViewById(R.id.swipe_layout)
        swipeRefreshLayoutNews.isRefreshing = true
        newsRecycler = rootView.findViewById(R.id.NewsRecycler)
        newsRecycler.setHasFixedSize(true)
        newsRecycler.layoutManager = LinearLayoutManager(requireContext())

        model = ViewModelProviders.of(this)[FootballViewModel::class.java]
        model.footballNews.observe(viewLifecycleOwner) { footballList ->
            footballList?.let {
                adapter = FoolballAdapter(
                    it,
                    object : FoolballAdapter.ShowDialoginterface {
                        override fun itemClik(hero: Football) {
                            openDialog(hero)
                        }
                    },
                )
                listFootball = it
                newsRecycler.adapter = adapter
            }
            swipeRefreshLayoutNews.isRefreshing = false
        }
        swipeRefreshLayoutNews.setOnRefreshListener {
            swipeRefreshLayoutNews.isRefreshing = true
            model.fetchFootballNews()
        }


        // search football
        searchView = rootView.findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })

        back = rootView.findViewById(R.id.back)
        back.setOnClickListener { requireActivity().onBackPressed() }

        return rootView
    }

    private fun filterList(query: String?) {
        if (query != null) {
            val filteredList = ArrayList<Football>()
            for (i in listFootball) {
                if (i.title.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }

            adapter.updateData(filteredList)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun openDialog(hero: Football) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_web_show, null)
        dialogBuilder.setView(dialogView)

        val mWebView: WebView = dialogView.findViewById(R.id.WebConnect)
        val button: ImageView = dialogView.findViewById(R.id.back)

        val webSettings: WebSettings = mWebView.settings
        webSettings.javaScriptEnabled = true

        mWebView.loadUrl(hero.url)

        val alertDialog: AlertDialog = dialogBuilder.create()
        button.setOnClickListener { alertDialog.dismiss() }
        alertDialog.show()
    }

}
