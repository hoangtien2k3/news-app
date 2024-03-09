package com.hoangtien2k3.news_app.ui.menu.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hoangtien2k3.news_app.databinding.FragmentUpdateUserBinding
import com.hoangtien2k3.news_app.network.result.UpdateUserResult

class UpdateUserFragment : Fragment() {

    private lateinit var binding: FragmentUpdateUserBinding
    private lateinit var viewModel: UpdateUserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[UpdateUserViewModel::class.java]

        binding.btnUpdateUser.setOnClickListener {
            val name = binding.txtName.text.toString()
            val username = binding.txtUsername.text.toString()
            val email = binding.txtEmail.text.toString()
            val password = binding.txtPassword.text.toString()
            viewModel.updateUserInfo(name, username, email, password)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.updateUser.observe(viewLifecycleOwner) { result ->
            when (result) {
                is UpdateUserResult.Success -> {
                    Toast.makeText(requireContext(), "Cập nhật thông tin thành công với: ${result.updateUserResponse.email}", Toast.LENGTH_SHORT).show()
                    requireActivity().onBackPressed()
                }
                is UpdateUserResult.Error -> {
                    Toast.makeText(requireContext(), "Cập nhật thông tin thấy bại, hãy thử lại.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}