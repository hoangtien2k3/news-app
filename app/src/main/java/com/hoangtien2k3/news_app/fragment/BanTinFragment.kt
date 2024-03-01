package com.hoangtien2k3.news_app.fragment

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.adapter.BanTinAdapter
import com.hoangtien2k3.news_app.adapter.CategoryAdapter
import com.hoangtien2k3.news_app.models.BanTin
import com.hoangtien2k3.news_app.utils.XMLDOMParser
import org.w3c.dom.Element
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

class BanTinFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var banTin_recyclerView: RecyclerView
    private lateinit var mBanTinAdapter: BanTinAdapter
    private lateinit var mListTinTuc: ArrayList<BanTin>

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
    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_ban_tin, container, false)

        mListTinTuc = ArrayList()
        banTin_recyclerView = rootView.findViewById(R.id.banTin_recyclerView)
        mBanTinAdapter = BanTinAdapter(requireContext(), mListTinTuc)

        // Cài đặt layout cho RecyclerView
        val gridLayoutManager = GridLayoutManager(requireContext(), 1)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL
        banTin_recyclerView.layoutManager = gridLayoutManager

        // Đặt Adapter cho RecyclerView
        banTin_recyclerView.adapter = mBanTinAdapter


        // Lấy Bundle từ arguments
        val bundle = arguments
        val url: String? = bundle?.getString("url")
        if (url != null) {
            GetListTinTuc().execute(url)
        }

        return rootView
    }

    inner class GetListTinTuc : AsyncTask<String, Int, String>() {
        override fun doInBackground(vararg strings: String): String {
            return getData(strings[0])
        }

        override fun onPostExecute(s: String) {
            val parser = XMLDOMParser()
            val document = parser.getDocument(s)
            val nodeListItem = document?.getElementsByTagName("item")
            val nodeListDescripton = document?.getElementsByTagName("description")

            nodeListItem?.let {
                for (i in 0 until it.length) {
                    val element = it.item(i) as Element

                    val title = parser.getValue(element, "title")
                    val link = parser.getValue(element, "link")
                    var img = ""

                    var txtpubDate = parser.getValue(element, "pubDate")
                    val inputFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
                    val zonedDateTime = ZonedDateTime.parse(txtpubDate, inputFormatter)
                    val outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)
                    val pubDate = outputFormatter.format(zonedDateTime)

                    val description = nodeListDescripton?.item(i + 1)?.textContent ?: ""

                    val p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>")
                    val matcher: Matcher = p.matcher(description)
                    if (matcher.find())
                        img = matcher.group(1)

                    val tinTuc = BanTin(title, link, img, pubDate)
                    mListTinTuc.add(tinTuc)
                }
            }
            mBanTinAdapter.notifyDataSetChanged() // Sửa lại tên adapter
            super.onPostExecute(s)
        }

        private fun getData(theUrl: String): String {
            val content = StringBuilder()
            try {
                val url = URL(theUrl)
                val urlConnection: URLConnection = url.openConnection()
                val bufferedReader = BufferedReader(InputStreamReader(urlConnection.getInputStream()))
                var line: String?
                while (bufferedReader.readLine().also { line = it } != null) {
                    content.append("$line\n")
                }
                bufferedReader.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return content.toString()
        }
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BanTinFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
