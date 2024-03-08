package com.hoangtien2k3.news_app.ui.menu

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
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.databinding.FragmentMenuBinding
import com.hoangtien2k3.news_app.activity.main.MainFragment
import com.hoangtien2k3.news_app.data.sharedpreferences.DataLocalManager
import com.hoangtien2k3.news_app.ui.account.AccountActivity
import com.hoangtien2k3.news_app.ui.calender.CalendarFragment
import com.hoangtien2k3.news_app.ui.webview.WebviewFragment
import com.hoangtien2k3.news_app.ui.weather.WeatherFragment

class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.switchMode.isChecked =
            AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_NO
        binding.switchMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.thoiTiet.setOnClickListener {
            loadFragment(WeatherFragment())
        }
        binding.lichViet.setOnClickListener {
            loadFragment(CalendarFragment())
        }
        binding.bieuMau.setOnClickListener {
            showdialogbottom()
        }
        binding.ngonNgu.setOnClickListener {
            loadFragment(MainFragment())
        }
        binding.logout.setOnClickListener {
            DataLocalManager.getInstance().setFirstInstalled(false)
            val intent = Intent(activity, AccountActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }

    }

    private fun loadFragment(fragmentReplace: Fragment) {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment, fragmentReplace)
            .addToBackStack("MenuFragment")
            .commit()
    }

    private fun openWebviewFragment(url: String) {
        val fragment = WebviewFragment().apply {
            arguments = Bundle().apply {
                putString("linknews", url)
            }
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.main_fragment, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun showdialogbottom() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottomsheetlayout)

        val DKdangtin = dialog.findViewById<TextView>(R.id.tvDKDangtin)
        val DKtimnguoithan = dialog.findViewById<TextView>(R.id.tvDKtimnguoithan)
        val DKthamquan = dialog.findViewById<TextView>(R.id.tvDKTQToasoan)

        DKdangtin.setOnClickListener {
            val url =
                "https://docs.google.com/forms/d/e/1FAIpQLSfuKRLsZXFOJ8S3oakeDXDTKq0XAXScVJJMHU-T6znMbGz05Q/viewform?usp=sf_link"
            openWebviewFragment(url)
            dialog.dismiss()
        }
        DKtimnguoithan.setOnClickListener {
            val url =
                "https://docs.google.com/forms/d/e/1FAIpQLSdb1LLWnuzk1oGqSiJcBPPogRWhoKIH6I4s5Fo7yJAtmsgW5g/viewform?pli=1"
            openWebviewFragment(url)
            dialog.dismiss()
        }
        DKthamquan.setOnClickListener {
            val url =
                "https://docs.google.com/forms/d/e/1FAIpQLScgmYFj2NTmq53VWzNSA4OKAuxizD9UxYiaXVok_S_2k3favg/viewform"
            openWebviewFragment(url)
            dialog.dismiss()
        }

        dialog.show()
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
    }

}
