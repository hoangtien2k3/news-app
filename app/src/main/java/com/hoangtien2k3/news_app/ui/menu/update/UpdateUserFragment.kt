package com.hoangtien2k3.news_app.ui.menu.update

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.databinding.FragmentUpdateUserBinding
import com.hoangtien2k3.news_app.utils.Resource
import com.hoangtien2k3.news_app.utils.viewBinding

class UpdateUserFragment : Fragment(R.layout.fragment_update_user) {
    private val binding by viewBinding(FragmentUpdateUserBinding::bind)
    private val viewModel: UpdateUserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        observeViewModel()
    }

    private fun initUI() {
        binding.btnUpdateUser.setOnClickListener {
            val name = binding.txtName.text.toString()
            val username = binding.txtUsername.text.toString()
            val email = binding.txtEmail.text.toString()
            val password = binding.txtPassword.text.toString()
            viewModel.updateUserInfo(name, username, email, password)
        }
    }

    private fun observeViewModel() {
        viewModel.updateUser.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Success -> {
                    Toast.makeText(requireContext(), R.string.update_info_successfully.toString() + result.message, Toast.LENGTH_SHORT).show()
                    requireActivity().onBackPressed()
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), R.string.update_info_failed.toString(), Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {

                }
            }
        }
    }

}