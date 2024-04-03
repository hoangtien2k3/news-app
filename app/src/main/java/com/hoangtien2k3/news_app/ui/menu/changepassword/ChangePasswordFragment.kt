package com.hoangtien2k3.news_app.ui.menu.changepassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hoangtien2k3.news_app.databinding.FragmentChangePasswordBinding
import com.hoangtien2k3.news_app.utils.Resource

class ChangePasswordFragment : Fragment() {
    private lateinit var binding: FragmentChangePasswordBinding
    private lateinit var viewModel: ChangePasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ChangePasswordViewModel::class.java]

        binding.back.setOnClickListener { requireActivity().onBackPressed() }
        binding.btnChangePassword.setOnClickListener {
            val oldPassword = binding.txtOldPassword.text.toString()
            val newPassword = binding.txtNewPassword.text.toString()
            val confirmPassword = binding.txtConfirmPassword.text.toString()
            viewModel.changePassword(oldPassword, newPassword, confirmPassword)
        }

        observeViewModel()
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

                }
            }
        }
    }

}