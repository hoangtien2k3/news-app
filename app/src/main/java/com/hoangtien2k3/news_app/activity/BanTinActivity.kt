package com.hoangtien2k3.news_app.activity

import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.adapter.NewsMainAdapter
import com.hoangtien2k3.news_app.models.News
import com.hoangtien2k3.news_app.utils.XMLDOMParser
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.net.URLConnection
import java.util.regex.Matcher
import java.util.regex.Pattern
import org.w3c.dom.Element

class BanTinActivity : AppCompatActivity() {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mListTinTuc: ArrayList<News>
    private lateinit var mLinTucAdaper: NewsMainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ban_tin)

        initializeUI()

        Thread {
            runOnUiThread {
                GetListTinTuc().execute(intent.getStringExtra("url"))
            }
        }.start()
    }

    private fun initializeUI() {
        mRecyclerView = findViewById(R.id.banTin_recyclerView)
        mListTinTuc = ArrayList()
        mLinTucAdaper = NewsMainAdapter(this, mListTinTuc)

        val linearLayout = LinearLayoutManager(this)
        mRecyclerView.layoutManager = linearLayout
        mRecyclerView.adapter = mLinTucAdaper
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
                    val description = nodeListDescripton?.item(i + 1)?.textContent ?: ""

                    val p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>")
                    val matcher: Matcher = p.matcher(description)
                    if (matcher.find())
                        img = matcher.group(1)

                    val tinTuc = News(title, link, img)
                    mListTinTuc.add(tinTuc)
                }
            }
            mLinTucAdaper.notifyDataSetChanged()
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
}

