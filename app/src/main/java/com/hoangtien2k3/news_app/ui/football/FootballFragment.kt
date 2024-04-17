package com.hoangtien2k3.news_app.ui.football

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.data.models.Football
import com.hoangtien2k3.news_app.databinding.FragmentFootballBinding
import com.hoangtien2k3.news_app.utils.viewBinding
import java.util.ArrayList
import java.util.Locale

class FootballFragment : Fragment(R.layout.fragment_football) {
    private val binding by viewBinding(FragmentFootballBinding::bind)
    private val model: FootballViewModel by viewModels()
    private lateinit var listFootball: List<Football>
    private lateinit var adapter: FoolballAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        collectData()
    }

    private fun initUI() {
        with(binding) {
            swipeLayout.isRefreshing = true
            NewsRecycler.setHasFixedSize(true)
            NewsRecycler.layoutManager = LinearLayoutManager(requireContext())

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    filterList(newText)
                    return true
                }
            })

            back.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    private fun collectData() {
        with(binding) {
            with(model) {
                footballNews.observe(viewLifecycleOwner) { footballList ->
                    footballList?.let {
                        adapter = it.data?.let { it1 ->
                            FoolballAdapter(
                                it1.data,
                                object : FoolballAdapter.ShowDialoginterface {
                                    override fun itemClik(hero: Football) {
                                        openDialog(hero)
                                    }
                                },
                            )
                        } ?: return@let
                        listFootball = it.data.data
                        NewsRecycler.adapter = adapter
                    }
                    swipeLayout.isRefreshing = false
                }
                swipeLayout.setOnRefreshListener {
                    swipeLayout.isRefreshing = true
                    fetchDataCallAPI()
                }
            }
        }
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
        Log.d("linkFootball", hero.url)

        val alertDialog: AlertDialog = dialogBuilder.create()
        button.setOnClickListener { alertDialog.dismiss() }
        alertDialog.show()
    }

}
