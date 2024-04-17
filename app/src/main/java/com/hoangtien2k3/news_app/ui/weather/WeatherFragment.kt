package com.hoangtien2k3.news_app.ui.weather

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.databinding.FragmentWeatherBinding
import com.hoangtien2k3.news_app.network.response.WeatherResponse
import com.hoangtien2k3.news_app.utils.Constants
import com.hoangtien2k3.news_app.utils.viewBinding
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WeatherFragment : Fragment(R.layout.fragment_weather) {
    private val binding by viewBinding(FragmentWeatherBinding::bind)
    private val viewModel: WeatherViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        collectData()
    }

    private fun initUI() {
        with(binding) {
            btnSearch.setOnClickListener {
                val city = binding.edittextSearch.text.toString()
                viewModel.getCurrentWeatherData(if (city.isEmpty()) "Hanoi" else city)
            }

            ok.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    private fun collectData() {
        viewModel.weatherResponse.observe(viewLifecycleOwner) { weatherResponse ->
            weatherResponse?.let {
                it.data?.let { it1 -> updateUI(it1) }
            }
        }

        viewModel.getCurrentWeatherData("Hanoi")
    }

    private fun updateUI(weatherResponse: WeatherResponse) {
        binding.apply {
            textviewThanhpho.text = weatherResponse.name

            val date = Date(weatherResponse.dt * 1000L)
            val simpleDateFormat = SimpleDateFormat("EEEE yyyy-MM-dd \n           HH-mm-ss", Locale.getDefault())
            val day = simpleDateFormat.format(date)
            textviewNgayCapNhat.text = day

            val weather = weatherResponse.weather.firstOrNull()
            weather?.let {
                Picasso.get().load(Constants.iconWeather + it.icon + ".png").into(imgIcon)
                textviewTrangThai.text = it.main
            }

            val main = weatherResponse.main
            val Nhietdo = "${(main.temp.toInt() - 273)}°C"
            textviewNhietDo.text = Nhietdo
            textviewDoam.text = "${main.humidity}%"

            val wind = weatherResponse.wind
            textviewGio.text = "${wind.speed} m/s"

            val clouds = weatherResponse.clouds
            textviewMay.text = "${clouds.all}%"

            val sys = weatherResponse.sys
            textviewQuocGia.text = "Tên quốc gia : ${sys.country}"
        }
    }

}


