package com.hoangtien2k3.news_app.ui.home

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
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
import com.hoangtien2k3.news_app.data.sharedpreferences.DataLocalManager
import com.hoangtien2k3.news_app.ui.account.AccountActivity
import com.hoangtien2k3.news_app.ui.bantin.BanTinFragment
import com.hoangtien2k3.news_app.ui.home.fragment.PostNewsLetterFragment
import com.hoangtien2k3.news_app.ui.menu.MenuFragment

class MainFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {
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
        val fragmentManager: FragmentManager = childFragmentManager
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
//            loadFragment(MainFragment())
        } else if (id == R.id.nav_danhmuc_admin) {
            loadFragment(BanTinFragment("full"))
        } else if (id == R.id.nav_dangxuat_admin) {
            DataLocalManager.getInstance().setFirstInstalled(false)
            val intent = Intent(requireContext(), AccountActivity::class.java)
            startActivity(intent)
        } else if (id == R.id.nav_tintuc_admin) {
            // đăng tin tức admin
            if (DataLocalManager.getInstance().getInfoUserRole() == "ADMIN") {
                loadFragment(PostNewsLetterFragment())
            } else {
                Toast.makeText(requireContext(), "Chỉ Có Admin Mới Có Quyền Truy Cập", Toast.LENGTH_SHORT).show()
            }
        } else if (id == R.id.nav_user_admin) {
            loadFragment(MenuFragment())
        } else if (id == R.id.nav_phanquyen_admin) {
            phanQuyenDialog()
        } else if (id == R.id.nav_pheduyet_admin) {
            // Handle phê duyệt action
        }
        mDrawerLayout!!.closeDrawer(GravityCompat.START)
        return true
    }

    private fun phanQuyenDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_item_phanquyen)

        val window = dialog.window
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window?.setGravity(Gravity.CENTER)

        val txtName: TextView = dialog.findViewById(R.id.txtName)
        val txtUserName: TextView = dialog.findViewById(R.id.txtUsername)
        val txtEmail: TextView = dialog.findViewById(R.id.txtEmail)
        val txtRole: TextView = dialog.findViewById(R.id.txtRole)
        txtName.text = DataLocalManager.getInstance().getInfoName()
        txtUserName.text = DataLocalManager.getInstance().getInfoUserName()
        txtEmail.text = DataLocalManager.getInstance().getInfoEmail()
        txtRole.text = DataLocalManager.getInstance().getInfoUserRole()

        dialog.show()
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
