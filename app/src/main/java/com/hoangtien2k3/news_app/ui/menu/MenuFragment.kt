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
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.databinding.FragmentMenuBinding
import com.hoangtien2k3.news_app.ui.home.MainFragment
import com.hoangtien2k3.news_app.data.sharedpreferences.DataLocalManager
import com.hoangtien2k3.news_app.ui.account.AccountActivity
import com.hoangtien2k3.news_app.ui.calender.CalendarFragment
import com.hoangtien2k3.news_app.ui.menu.changepassword.ChangePasswordFragment
import com.hoangtien2k3.news_app.ui.menu.delete.DeleteUserViewModel
import com.hoangtien2k3.news_app.ui.menu.update.UpdateUserFragment
import com.hoangtien2k3.news_app.ui.save.SaveBanTinFragment
import com.hoangtien2k3.news_app.ui.webview.WebviewFragment
import com.hoangtien2k3.news_app.ui.weather.WeatherFragment

class MenuFragment : Fragment() {
    private lateinit var binding: FragmentMenuBinding
    private lateinit var viewModel: DeleteUserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[DeleteUserViewModel::class.java]

        binding.apply {
            switchMode.isChecked =
                AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_NO
            switchMode.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                else
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            thoiTiet.setOnClickListener { loadFragment(WeatherFragment()) }
            lichViet.setOnClickListener { loadFragment(CalendarFragment()) }
            ngonNgu.setOnClickListener { loadFragment(MainFragment()) }
            txtSavefeedbackTinDaDoc.setOnClickListener {loadFragment(SaveBanTinFragment())}
            bieuMau.setOnClickListener { showdialogbottom() }
            btnProfile.setOnClickListener { showOptionsDialog() }
            logout.setOnClickListener {
                DataLocalManager.getInstance().setFirstInstalled(false)
                DataLocalManager.getInstance().removeValueFromSharedPreferences()
                val intent = Intent(activity, AccountActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
            txtName.text = DataLocalManager.getInstance().getInfoName()
        }
    }

    private fun showOptionsDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_profile_user_layout)

        // Tùy chỉnh vị trí của dialog trên màn hình
        val window = dialog.window
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window?.setGravity(Gravity.CENTER)

        // Xử lý sự kiện khi nút trong dialog được nhấn
        val btnChangePassword: LinearLayout = dialog.findViewById(R.id.btnChangePassword)
        val btnUpdateInfo: LinearLayout = dialog.findViewById(R.id.btnUpdateInfo)
        val btnDeleteInfo: LinearLayout = dialog.findViewById(R.id.btnDeleteInfo)
        btnChangePassword.setOnClickListener {
            dialog.dismiss()
            loadFragment(ChangePasswordFragment())
        }
        btnUpdateInfo.setOnClickListener {
            dialog.dismiss()
            loadFragment(UpdateUserFragment())
        }
        btnDeleteInfo.setOnClickListener {
            dialog.dismiss()
            deleteDialogCustomView()
        }

        dialog.show()
    }

    private fun deleteDialogCustomView() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_delete_user_layout)

        // Tùy chỉnh vị trí của dialog trên màn hình
        val window = dialog.window
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window?.setGravity(Gravity.CENTER)

        // Xử lý sự kiện khi nút trong dialog được nhấn
        val btnHuy: Button = dialog.findViewById(R.id.btnHuy)
        val btnXoa: Button = dialog.findViewById(R.id.btnXoa)
        val txtUsername: TextView = dialog.findViewById(R.id.txtUsername)
        txtUsername.text = DataLocalManager.getInstance().getInfoUserName()
        btnHuy.setOnClickListener {
            dialog.dismiss()
        }
        btnXoa.setOnClickListener {
            // Định danh của tài khoản cần xóa
            val accountIdToDelete: Long = DataLocalManager.getInstance().getInfoUserId()
            if (accountIdToDelete.toInt() != 0) {
                viewModel.deleteUserId(accountIdToDelete)
                viewModel.deleteUser.observe(viewLifecycleOwner) { result ->
                    Toast.makeText(requireContext(), result, Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                    DataLocalManager.getInstance().setFirstInstalled(false)
                    DataLocalManager.getInstance().removeValueFromSharedPreferences()
                }
                loadFragment(MenuFragment())
            } else {
                Toast.makeText(
                    requireContext(),
                    "Bạn Chưa Đăng Nhập Tài Khoản Nào.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        dialog.show()
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
            .addToBackStack("MenuFragment")
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
