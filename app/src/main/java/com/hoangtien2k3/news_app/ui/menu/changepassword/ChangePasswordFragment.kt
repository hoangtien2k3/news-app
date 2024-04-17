package com.hoangtien2k3.news_app.ui.menu.changepassword

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.databinding.FragmentChangePasswordBinding
import com.hoangtien2k3.news_app.utils.LoadingScreen
import com.hoangtien2k3.news_app.utils.Resource
import com.hoangtien2k3.news_app.utils.viewBinding

class ChangePasswordFragment : Fragment(R.layout.fragment_change_password) {
    private val binding by viewBinding(FragmentChangePasswordBinding::bind)
    private val viewModel: ChangePasswordViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        observeViewModel()
    }

    private fun initUI() {
        with(binding) {
            with(viewModel) {
                back.setOnClickListener { requireActivity().onBackPressed() }
                btnChangePassword.setOnClickListener {
                    val oldPassword = binding.txtOldPassword.text.toString()
                    val newPassword = binding.txtNewPassword.text.toString()
                    val confirmPassword = binding.txtConfirmPassword.text.toString()
                    changePassword(oldPassword, newPassword, confirmPassword)
                }
            }
        }
    }

    private fun observeViewModel() {
        viewModel.changePasswordResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Success -> {
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                    requireActivity().onBackPressed()
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    LoadingScreen.displayLoading(requireContext(), false)
                }
            }
        }
    }

}