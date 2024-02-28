package com.hoangtien2k3.news_app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.adapter.CategoryAdapter
import com.hoangtien2k3.news_app.adapter.NewsFoolballAdapter
import com.hoangtien2k3.news_app.api.FootballService
import com.hoangtien2k3.news_app.models.Category
import com.hoangtien2k3.news_app.models.Football
import com.hoangtien2k3.news_app.utils.Constants
import com.hoangtien2k3.news_app.viewmodel.NewsViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class NewsFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerViewDanhMuc: RecyclerView
    private lateinit var mDanhMucAdapter: CategoryAdapter
    private lateinit var mListDanhMuc: ArrayList<Category>

    private lateinit var newsRecycler: RecyclerView
    private lateinit var adapter: NewsFoolballAdapter
    private lateinit var model: NewsViewModel
    private lateinit var swipeRefreshLayoutNews: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val rootView: View = inflater.inflate(R.layout.fragment_news, container, false)

        recyclerViewDanhMuc = rootView.findViewById(R.id.danh_muc)
        mListDanhMuc = getListDanhMuc()
        mDanhMucAdapter = CategoryAdapter(requireContext(), mListDanhMuc)

        val gridLayoutManager = GridLayoutManager(requireContext(), 1)
        gridLayoutManager.orientation = GridLayoutManager.HORIZONTAL
        recyclerViewDanhMuc.layoutManager = gridLayoutManager
        recyclerViewDanhMuc.adapter = mDanhMucAdapter



        swipeRefreshLayoutNews = rootView.findViewById(R.id.swipe_layout)
        swipeRefreshLayoutNews.isRefreshing = true
        newsRecycler = rootView.findViewById(R.id.NewsRecycler)
        newsRecycler.setHasFixedSize(true)
        newsRecycler.layoutManager = LinearLayoutManager(requireContext())

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_Foolball)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(FootballService::class.java)
        val call = service.getFootballData()

        call.enqueue(object : Callback<List<Football>> {
            override fun onResponse(call: Call<List<Football>>, response: Response<List<Football>>) {
                if (response.isSuccessful) {
                    val footballList = response.body()
                    footballList?.let {
                        adapter = NewsFoolballAdapter(requireContext(), it, object : NewsFoolballAdapter.ShowDialoginterface {
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
                        newsRecycler.adapter = adapter
                    }
                }
                swipeRefreshLayoutNews.isRefreshing = false
            }

            override fun onFailure(call: Call<List<Football>>, t: Throwable) {
                swipeRefreshLayoutNews.isRefreshing = false
                t.printStackTrace()
            }
        })


        return rootView
    }

    private fun getListDanhMuc(): ArrayList<Category> {
        return arrayListOf(
            Category("Nổi bật", Constants.BASE_URL_TIN_NOI_BAT),
            Category("Mới nhất", Constants.BASE_URL_TIN_MOI_NHAT),
            Category("Thế giới", Constants.BASE_URL_TIN_THE_GIOI),
            Category("Startup", Constants.BASE_URL_TIN_STARUP),
            Category("Giải trí", Constants.BASE_URL_TIN_GIAI_TRI),
            Category("Thể thao", Constants.BASE_URL_TIN_THE_THAO),
            Category("Pháp luật", Constants.BASE_URL_TIN_PHAP_LUAT),
            Category("Giáo dục", Constants.BASE_URL_TIN_GIAO_DUC),
            Category("Sức khỏe", Constants.BASE_URL_TIN_SUC_KHOE),
            Category("Đời sống", Constants.BASE_URL_TIN_DOI_SONG),
            Category("Khoa học", Constants.BASE_URL_TIN_KHOA_HOC),
            Category("Kinh doanh", Constants.BASE_URL_TIN_KINH_DOANH),
            Category("Tâm sự", Constants.BASE_URL_TIN_TAM_SU),
            Category("Số hóa", Constants.BASE_URL_TIN_SO_HOA),
            Category("Du lịch", Constants.BASE_URL_TIN_DU_LICH)
        )
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}