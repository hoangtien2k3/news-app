package com.hoangtien2k3.news_app.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hoangtien2k3.news_app.ui.account.login.LoginFragment
import com.hoangtien2k3.news_app.ui.bantin.BanTinFragment
import com.hoangtien2k3.news_app.ui.home.fragment.HomeFragment

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> BanTinFragment("tin-noi-bat")
            2 -> BanTinFragment("tin-moi-nhat")
            3 -> BanTinFragment("tin-the-gioi")
            4 -> BanTinFragment("tin-phap-luat")
            5 -> BanTinFragment("tin-giao-duc")
            6 -> BanTinFragment("tin-suc-khoe")
            7 -> BanTinFragment("tin-doi-song")
            8 -> BanTinFragment("tin-khoa-hoc")
            9 -> BanTinFragment("tin-kinh-doanh")
            10 -> BanTinFragment("tin-tam-su")
            11 -> BanTinFragment("tin-so-hoa")
            12 -> BanTinFragment("tin-du-lich")
            else -> HomeFragment()
        }
    }

    override fun getItemCount(): Int {
        return 13
    }
}
