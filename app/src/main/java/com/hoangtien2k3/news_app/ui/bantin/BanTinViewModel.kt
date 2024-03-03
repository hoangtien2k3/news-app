package com.hoangtien2k3.news_app.ui.bantin

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hoangtien2k3.news_app.data.models.BanTin
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

class BanTinViewModel : ViewModel() {

    private val _listTinTuc = MutableLiveData<List<BanTin>>()
    val listTinTuc: LiveData<List<BanTin>>
        get() = _listTinTuc

    fun fetchListTinTuc(url: String) {
        GetListTinTucAsyncTask { tinTucList ->
            _listTinTuc.value = tinTucList
        }.execute(url)
    }

    private inner class GetListTinTucAsyncTask(val callback: (List<BanTin>) -> Unit) :
        AsyncTask<String, Int, List<BanTin>>() {

        override fun doInBackground(vararg strings: String): List<BanTin> {
            val data = getData(strings[0])
            return parseData(data)
        }

        override fun onPostExecute(result: List<BanTin>) {
            callback(result)
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

        private fun parseData(data: String): List<BanTin> {
            val tinTucList = mutableListOf<BanTin>()
            val parser = XMLDOMParser()
            val document = parser.getDocument(data)
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
                    tinTucList.add(tinTuc)
                }
            }
            return tinTucList
        }
    }
}
