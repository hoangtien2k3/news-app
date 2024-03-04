package com.hoangtien2k3.news_app.ui.main

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
import com.google.android.material.navigation.NavigationView
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.ui.home.HomeFragment

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

        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, HomeFragment())
            .addToBackStack("HomeFragment")
            .commit()

        return view
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val hasPermission = checkPermission()
        val id = item.itemId
        // Handle navigation view item clicks here.
        if (id == R.id.nav_trangchu_admin) {
            // Handle the home action
        } else if (id == R.id.nav_danhmuc_admin) {
            // Handle danh mục action
        } else if (id == R.id.nav_dangxuat_admin) {
            // Handle đăng xuất action
        } else if (id == R.id.nav_tintuc_admin) {
            // Handle tin tức action
        } else if (id == R.id.nav_user_admin) {
            // Handle người dùng action
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

}
