package com.hoangtien2k3.news_app.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hoangtien2k3.news_app.R

import android.widget.*
import com.hoangtien2k3.news_app.api.WeatherService
import com.hoangtien2k3.news_app.response.WeatherResponse
import com.hoangtien2k3.news_app.utils.Constants
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity() {
    private lateinit var edtSearch: EditText
    private lateinit var btnSearch: ImageView
    private lateinit var txtThanhpho: TextView
    private lateinit var txtQuocGia: TextView
    private lateinit var txtNhietDo: TextView
    private lateinit var txtTrangThai: TextView
    private lateinit var txtDoam: TextView
    private lateinit var txtGio: TextView
    private lateinit var txtMay: TextView
    private lateinit var txtNgayCapNhat: TextView
    private lateinit var imgIcon: ImageView
    private lateinit var oke: ImageView

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL_Weather)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service = retrofit.create(WeatherService::class.java)
    private val apiKey = Constants.apiKeyWeather

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thoi_tiet)

        initUI()
        GetCurrentWeatherData("Hanoi")

        btnSearch.setOnClickListener {
            val city = edtSearch.text.toString()
            GetCurrentWeatherData(if (city.isEmpty()) "Hanoi" else city)
        }

        oke = findViewById(R.id.ok)
        oke.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
        }

    }

    private fun initUI() {
        edtSearch = findViewById(R.id.edittextSearch)
        btnSearch = findViewById(R.id.btnSearch)
        txtThanhpho = findViewById(R.id.textviewThanhpho)
        txtQuocGia = findViewById(R.id.textviewQuocGia)
        txtNhietDo = findViewById(R.id.textviewNhietDo)
        txtTrangThai = findViewById(R.id.textviewTrangThai)
        txtGio = findViewById(R.id.textviewGio)
        txtDoam = findViewById(R.id.textviewDoam)
        txtMay = findViewById(R.id.textviewMay)
        txtNgayCapNhat = findViewById(R.id.textviewNgayCapNhat)
        imgIcon = findViewById(R.id.imgIcon)
        oke = findViewById(R.id.ok)
    }

    private fun GetCurrentWeatherData(data: String) {
        val call = service.getCurrentWeather(data, apiKey)
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    weatherResponse?.let { updateUI(it) }
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun updateUI(weatherResponse: WeatherResponse) {
        txtThanhpho.text = weatherResponse.name

        val date = Date(weatherResponse.dt * 1000L)
        val simpleDateFormat =
            SimpleDateFormat("EEEE yyyy-MM-dd \n           HH-mm-ss", Locale.getDefault())
        val day = simpleDateFormat.format(date)
        txtNgayCapNhat.text = day

        val weather = weatherResponse.weather.firstOrNull()
        weather?.let {
            Picasso.get().load(Constants.iconWeather + it.icon + ".png").into(imgIcon)
            txtTrangThai.text = it.main
        }

        val main = weatherResponse.main
        val Nhietdo = "${(main.temp.toInt() - 273)}°C"
        txtNhietDo.text = Nhietdo
        txtDoam.text = "${main.humidity}%"

        val wind = weatherResponse.wind
        txtGio.text = "${wind.speed} m/s"

        val clouds = weatherResponse.clouds
        txtMay.text = "${clouds.all}%"

        val sys = weatherResponse.sys
        txtQuocGia.text = "Tên quốc gia : ${sys.country}"
    }
}
