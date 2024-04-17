package com.hoangtien2k3.news_app.ui.menu

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
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
import androidx.fragment.app.viewModels
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.databinding.FragmentMenuBinding
import com.hoangtien2k3.news_app.data.sharedpreferences.DataLocalManager
import com.hoangtien2k3.news_app.ui.account.AccountActivity
import com.hoangtien2k3.news_app.ui.calender.CalendarFragment
import com.hoangtien2k3.news_app.ui.football.FootballFragment
import com.hoangtien2k3.news_app.ui.menu.changepassword.ChangePasswordFragment
import com.hoangtien2k3.news_app.ui.menu.delete.DeleteUserViewModel
import com.hoangtien2k3.news_app.ui.menu.update.UpdateUserFragment
import com.hoangtien2k3.news_app.ui.save.SaveBanTinFragment
import com.hoangtien2k3.news_app.ui.search.news.SearchNewsFragment
import com.hoangtien2k3.news_app.ui.webview.WebviewFragment
import com.hoangtien2k3.news_app.ui.weather.WeatherFragment
import com.hoangtien2k3.news_app.utils.CallHotLine
import com.hoangtien2k3.news_app.utils.Constants
import com.hoangtien2k3.news_app.utils.viewBinding

class MenuFragment : Fragment(R.layout.fragment_menu) {
    private val binding by viewBinding(FragmentMenuBinding::bind)
    private val viewModel: DeleteUserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingLightAndDartMode()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.apply {
            thoiTiet.setOnClickListener { loadFragment(WeatherFragment()) }
            lichViet.setOnClickListener { loadFragment(CalendarFragment()) }
            txtSavefeedbackTinDaDoc.setOnClickListener {
                val bundle = Bundle().apply {
                    putString("close", "false")
                }
                val fragment = SaveBanTinFragment()
                fragment.arguments = bundle
                loadFragment(fragment)
            }
            lnCall.setOnClickListener { hotlineHomescreen() }
            menuFacebook.setOnClickListener { socialNetwork("facebook") }
            menuMessage.setOnClickListener { socialNetwork("message") }
            menuZalo.setOnClickListener { socialNetwork("zalo") }
            menuGithub.setOnClickListener { socialNetwork("github") }
            bieuMau.setOnClickListener { showdialogbottom() }
            football.setOnClickListener {loadFragment(FootballFragment())}
            tinQuocTe.setOnClickListener {loadFragment(SearchNewsFragment())}
            btnProfile.setOnClickListener {
                if (DataLocalManager.getInstance().getInfoUserId().toInt() == 0) {
                    showOptionsDialogNotifyLoginUser()
                } else {
                    showOptionsDialog()
                }
            }

            logout.setOnClickListener {
                DataLocalManager.getInstance().setFirstInstalled(false)
                DataLocalManager.getInstance().removeValueFromSharedPreferences()
                val intent = Intent(activity, AccountActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }

            txtName.text = DataLocalManager.getInstance().getInfoName()
            txtEmail.text = DataLocalManager.getInstance().getInfoEmail()
        }
    }


    private fun settingLightAndDartMode() {
        binding.apply {
            switchMode.isChecked =
                AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_NO
            switchMode.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                else
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun showOptionsDialogNotifyLoginUser() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_notify_layout)

        // Tùy chỉnh vị trí của dialog trên màn hình
        val window = dialog.window
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window?.setGravity(Gravity.CENTER)

        val btnHuy: Button = dialog.findViewById(R.id.btnHuy)
        val btnLogin: Button = dialog.findViewById(R.id.btnLogin)
        btnHuy.setOnClickListener {
            dialog.dismiss()
        }
        btnLogin.setOnClickListener {
            DataLocalManager.getInstance().setFirstInstalled(false)
            DataLocalManager.getInstance().removeValueFromSharedPreferences()
            val intent = Intent(activity, AccountActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }

        dialog.show()
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
                    Toast.makeText(requireContext(), result.toString(), Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                    DataLocalManager.getInstance().setFirstInstalled(false)
                    DataLocalManager.getInstance().removeValueFromSharedPreferences()
                }
                loadFragment(MenuFragment())
            } else {
                Toast.makeText(
                    requireContext(),
                    R.string.not_login_account.toString(),
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

    private fun hotlineHomescreen() {
        activity?.let { it1 -> CallHotLine.call(it1) }
    }

    private fun socialNetwork(title: String) {
        val socialNetworkMap = mapOf(
            "zalo" to Pair("0828007853", "http://zalo.me/"),
            "message" to Pair("100053705482952", "http://m.me/"),
            "facebook" to Pair("100077499696008", "http://m.me/"),
            "github" to Pair("hoangtien2k3qx1", "https://github.com/")
        )

        socialNetworkMap[title]?.let { (id, baseUrl) ->
            val url = "$baseUrl$id"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }

    private fun showdialogbottom() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottomsheetlayout)

        val DKdangtin = dialog.findViewById<TextView>(R.id.tvDKDangtin)
        val DKtimnguoithan = dialog.findViewById<TextView>(R.id.tvDKtimnguoithan)
        val DKthamquan = dialog.findViewById<TextView>(R.id.tvDKTQToasoan)

        DKdangtin.setOnClickListener {
            val url = Constants.Link_Dang_Ky
            openWebviewFragment(url)
            dialog.dismiss()
        }
        DKtimnguoithan.setOnClickListener {
            val url = Constants.Link_Tim_Nguoi_Than
            openWebviewFragment(url)
            dialog.dismiss()
        }
        DKthamquan.setOnClickListener {
            val url = Constants.Link_Tham_Quan
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
