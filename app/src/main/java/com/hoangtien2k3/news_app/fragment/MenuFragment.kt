package com.hoangtien2k3.news_app.fragment

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.activity.CalenderActivity
import com.hoangtien2k3.news_app.activity.FootlballActivity
import com.hoangtien2k3.news_app.activity.WeatherActivity
import com.hoangtien2k3.news_app.activity.WebviewActivity

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MenuFragment : Fragment() {

    private var mParam1: String? = null
    private var mParam2: String? = null

    private lateinit var vietnameseCalendarTextView: TextView
    private lateinit var weatherTextView: TextView
    private lateinit var FoolballTextView: TextView
    private lateinit var tvDangkybieumauC: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mParam1 = it.getString(ARG_PARAM1)
            mParam2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity()

//        imAvatar = activity.findViewById(R.id.img_avatar)
//        tvName = activity.findViewById(R.id.tv_name)
//        tvEmail = activity.findViewById(R.id.tv_email)
//        tvDangXuat = activity.findViewById(R.id.tv_DangXuat)
//        tvTTTK = activity.findViewById(R.id.tv_TTTK)
        tvDangkybieumauC = activity.findViewById(R.id.dangkybieumauTextView)
//        tvLichSu = activity.findViewById(R.id.tv_LichSu)
        weatherTextView = activity.findViewById(R.id.weatherTextView)
//        aSwitch = activity.findViewById(R.id.switcher)
        vietnameseCalendarTextView = activity.findViewById(R.id.vietnameseCalendarTextView)
        FoolballTextView = activity.findViewById(R.id.FoolballTextView)


        weatherTextView.setOnClickListener {
            val intent = Intent(activity, WeatherActivity::class.java)
            startActivity(intent)
        }

        FoolballTextView.setOnClickListener {
            val intent = Intent(activity, FootlballActivity::class.java)
            startActivity(intent)
        }

        vietnameseCalendarTextView.setOnClickListener {
            val intent = Intent(activity, CalenderActivity::class.java)
            startActivity(intent)
        }

        tvDangkybieumauC.setOnClickListener {
            showdialogbottom()
        }

    }

    private fun showdialogbottom() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottomsheetlayout)
        val dangtinLayout = dialog.findViewById<LinearLayout>(R.id.layoutdkdangtin)
        val nguoithanLayout = dialog.findViewById<LinearLayout>(R.id.layoutTimnguoithan)
        val toasoanLayout = dialog.findViewById<LinearLayout>(R.id.layoutTQtoasoan)
        val DKdangtin = dialog.findViewById<TextView>(R.id.tvDKDangtin)
        val DKtimnguoithan = dialog.findViewById<TextView>(R.id.tvDKtimnguoithan)
        val DKthamquan = dialog.findViewById<TextView>(R.id.tvDKTQToasoan)

        DKdangtin.setOnClickListener {
            val url = "https://docs.google.com/forms/d/e/1FAIpQLSfuKRLsZXFOJ8S3oakeDXDTKq0XAXScVJJMHU-T6znMbGz05Q/viewform?usp=sf_link"
            val intent = Intent(activity, WebviewActivity::class.java)
            intent.putExtra("linknews", url)
            startActivity(intent)
            dialog.dismiss()
        }

        DKtimnguoithan.setOnClickListener {
            val url = "https://docs.google.com/forms/d/e/1FAIpQLSdb1LLWnuzk1oGqSiJcBPPogRWhoKIH6I4s5Fo7yJAtmsgW5g/viewform?pli=1"
            val intent = Intent(activity, WebviewActivity::class.java)
            intent.putExtra("linknews", url)
            startActivity(intent)
            dialog.dismiss()
        }

        DKthamquan.setOnClickListener {
            val url = "https://docs.google.com/forms/d/e/1FAIpQLScgmYFj2NTmq53VWzNSA4OKAuxizD9UxYiaXVok_S_2k3favg/viewform"
            val intent = Intent(activity, WebviewActivity::class.java)
            intent.putExtra("linknews", url)
            startActivity(intent)
            dialog.dismiss()
        }

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
    }

}
