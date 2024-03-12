package com.hoangtien2k3.news_app.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hoangtien2k3.news_app.ui.bantin.BanTinFragment
import com.hoangtien2k3.news_app.ui.home.fragment.HomeFragment
import com.hoangtien2k3.news_app.utils.Constants

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> BanTinFragment(Constants.tin_noi_bat)
            2 -> BanTinFragment(Constants.tin_moi_nhat)
            3 -> BanTinFragment(Constants.tin_the_gioi)
            4 -> BanTinFragment(Constants.tin_the_thao)
            5 -> BanTinFragment(Constants.tin_phap_luat)
            6 -> BanTinFragment(Constants.tin_giao_duc)
            7 -> BanTinFragment(Constants.tin_suc_khoe)
            8 -> BanTinFragment(Constants.tin_doi_song)
            9 -> BanTinFragment(Constants.tin_khoa_hoc)
            10 -> BanTinFragment(Constants.tin_kinh_doanh)
            11 -> BanTinFragment(Constants.tin_tam_su)
            12 -> BanTinFragment(Constants.tin_so_hoa)
            13 -> BanTinFragment(Constants.tin_du_lich)
            else -> HomeFragment()
        }
    }

    override fun getItemCount(): Int {
        return 14
    }
}
