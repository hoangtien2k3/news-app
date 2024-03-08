package com.hoangtien2k3.news_app.ui.account

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hoangtien2k3.news_app.ui.account.login.LoginFragment
import com.hoangtien2k3.news_app.ui.account.signup.SignupFragment

class MyFragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LoginFragment()
            1 -> SignupFragment()
            else -> LoginFragment()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}
