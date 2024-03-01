package com.hoangtien2k3.news_app.fragment

import android.os.AsyncTask
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
import com.hoangtien2k3.news_app.adapter.BanTinAdapter
import com.hoangtien2k3.news_app.adapter.CategoryAdapter
import com.hoangtien2k3.news_app.adapter.NewsFoolballAdapter
import com.hoangtien2k3.news_app.api.FootballService
import com.hoangtien2k3.news_app.databinding.FragmentHomeBinding
import com.hoangtien2k3.news_app.models.BanTin
import com.hoangtien2k3.news_app.models.Category
import com.hoangtien2k3.news_app.models.Football
import com.hoangtien2k3.news_app.utils.Constants
import com.hoangtien2k3.news_app.utils.XMLDOMParser
import org.w3c.dom.Element
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.net.URLConnection
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.regex.Matcher
import java.util.regex.Pattern

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var mListDanhMuc: ArrayList<Category>
    private lateinit var mDanhMucAdapter: CategoryAdapter
    private lateinit var adapter: NewsFoolballAdapter
    private lateinit var banTin_recyclerView: RecyclerView
    private lateinit var mBanTinAdapter: BanTinAdapter
    private lateinit var mListTinTuc: ArrayList<BanTin>

    private lateinit var binding: FragmentHomeBinding

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
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val rootView = binding.root

        // Initialize list of categories and adapter
        mListDanhMuc = getListDanhMuc()
        mDanhMucAdapter = CategoryAdapter(requireContext(), mListDanhMuc)

        // Set up RecyclerView for categories
        binding.danhMuc.apply {
            layoutManager = GridLayoutManager(requireContext(), 1).apply {
                orientation = GridLayoutManager.HORIZONTAL
            }
            adapter = mDanhMucAdapter
        }

        // Set up click listener for search ImageView
        binding.search.setOnClickListener {
            loadFragment(SearchNewsFragment())
        }

        // Set up RecyclerView for news
        binding.swipeLayout.isRefreshing = true
        binding.NewsRecycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }

//        binding.swipeLayout.isRefreshing = true
//        binding.recyclerViewDanhMuc.apply {
//            setHasFixedSize(true)
//            layoutManager = LinearLayoutManager(requireContext())
//        }


        /*
        * cal api tin tức
        * */



        /*
        * call api FootBall
        * */
        // Fetch football news data using Retrofit
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
                        binding.NewsRecycler.adapter = adapter
//                        binding.recyclerViewDanhMuc.adapter = adapter
                    }
                }
                binding.swipeLayout.isRefreshing = false
            }

            override fun onFailure(call: Call<List<Football>>, t: Throwable) {
                binding.swipeLayout.isRefreshing = false
                t.printStackTrace()
            }
        })

        return rootView
    }


    private fun loadFragment(fragmentReplace: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment, fragmentReplace)
            .addToBackStack(null)
            .commit()
    }

    private fun getListDanhMuc(): ArrayList<Category> {
        return arrayListOf(
            Category("Nổi Bật", Constants.BASE_URL_TIN_NOI_BAT),
            Category("Mới Nhất", Constants.BASE_URL_TIN_MOI_NHAT),
            Category("Thế Giới", Constants.BASE_URL_TIN_THE_GIOI),
            Category("Startup", Constants.BASE_URL_TIN_STARUP),
            Category("Giải Trí", Constants.BASE_URL_TIN_GIAI_TRI),
            Category("Thể Thao", Constants.BASE_URL_TIN_THE_THAO),
            Category("Pháp Luật", Constants.BASE_URL_TIN_PHAP_LUAT),
            Category("Giáo Dục", Constants.BASE_URL_TIN_GIAO_DUC),
            Category("Sức Khỏe", Constants.BASE_URL_TIN_SUC_KHOE),
            Category("Đời Sống", Constants.BASE_URL_TIN_DOI_SONG),
            Category("Khoa Học", Constants.BASE_URL_TIN_KHOA_HOC),
            Category("Kinh Doanh", Constants.BASE_URL_TIN_KINH_DOANH),
            Category("Tâm Sự", Constants.BASE_URL_TIN_TAM_SU),
            Category("Số Hóa", Constants.BASE_URL_TIN_SO_HOA),
            Category("Du Lịch", Constants.BASE_URL_TIN_DU_LICH)
        )
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
