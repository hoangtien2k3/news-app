package com.hoangtien2k3.news_app.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.ui.account.AccountActivity
import com.hoangtien2k3.news_app.ui.menu.MenuFragment

class MainFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {
    private val FRAGMENT_HOME = 0
    private val FRAGMENT_DANHMUC = 1
    private val FRAGMENT_TINTUC = 2
    private val FRAGMENT_USER = 3
    private val FRAGMENT_PHANQUYEN = 4
    private val FRAGMENT_PHEDUYET = 5
    private val MY_REQUEST_CODE = 10
    private var mCurrentFragment = FRAGMENT_HOME
    private var mDrawerLayout: DrawerLayout? = null
    private var permission: String? = null


    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        val navigationView: NavigationView = view.findViewById(R.id.nav_view)
        mDrawerLayout = view.findViewById(R.id.drawer_layout)

        val toggle = ActionBarDrawerToggle(
            requireActivity(),
            mDrawerLayout,
            toolbar,
            R.string.open_nav,
            R.string.close_nav
        )

        mDrawerLayout!!.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener(this)


        tabLayout = view.findViewById(R.id.tabLayout)
        viewPager2 = view.findViewById(R.id.viewPager2)
        initTabLayoutAndViewPager()

        return view
    }

    private fun initTabLayoutAndViewPager() {
//        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.sign_in)))
//        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.sign_up)))

        val fragmentManager: FragmentManager = childFragmentManager // Sử dụng childFragmentManager thay vì supportFragmentManager
        adapter = ViewPagerAdapter(fragmentManager, lifecycle)
        viewPager2.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.pho_bien)
                1 -> getString(R.string.noi_bat)
                2 -> getString(R.string.moi_nhat)
                3 -> getString(R.string.the_gioi)
                4 -> getString(R.string.phap_luat)
                5 -> getString(R.string.giao_duc)
                6 -> getString(R.string.suc_khoe)
                7 -> getString(R.string.doi_song)
                8 -> getString(R.string.khoa_hoc)
                9 -> getString(R.string.kinh_doanh)
                10 -> getString(R.string.tam_su)
                11 -> getString(R.string.so_hoa)
                12 -> getString(R.string.du_lich)
                else -> getString(R.string.pho_bien)
            }
        }.attach()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val hasPermission = checkPermission()
        val id = item.itemId
        // Handle navigation view item clicks here.
        if (id == R.id.nav_trangchu_admin) {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
        } else if (id == R.id.nav_danhmuc_admin) {
            // Handle danh mục action
        } else if (id == R.id.nav_dangxuat_admin) {
            val intent = Intent(requireContext(), AccountActivity::class.java)
            startActivity(intent)
        } else if (id == R.id.nav_tintuc_admin) {
            // Handle tin tức action
        } else if (id == R.id.nav_user_admin) {
            loadFragment(MenuFragment())
        } else if (id == R.id.nav_phanquyen_admin) {
            // Handle phân quyền action
        } else if (id == R.id.nav_pheduyet_admin) {
            // Handle phê duyệt action
        }
        mDrawerLayout!!.closeDrawer(GravityCompat.START)
        return true
    }

    private fun checkPermission(): String? {
        val intent = requireActivity().intent
        if (intent != null) {
            val bundle = intent.extras
            if (bundle != null) {
                permission = bundle.getString("Permission")
            }
        }
        return permission
    }

    private fun loadFragment(fragmentReplace: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment, fragmentReplace)
            .commit()
    }

}
