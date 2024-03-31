package com.hoangtien2k3.news_app.ui.account.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hoangtien2k3.news_app.data.sharedpreferences.DataLocalManager
import com.hoangtien2k3.news_app.databinding.FragmentSignUpBinding
import com.hoangtien2k3.news_app.ui.account.AccountViewModel
import com.hoangtien2k3.news_app.utils.Resource

class SignupFragment : Fragment() {
    private lateinit var viewModel: AccountViewModel
    private var _binding: FragmentSignUpBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[AccountViewModel::class.java]

        binding.signUpBtn.setOnClickListener {
            val name = binding.editNameSignUp.text.toString()
            val username = binding.editUsernameSignUp.text.toString()
            val email = binding.editEmailSignUp.text.toString()
            val password = binding.editPassSignUp.text.toString()
            if (name == "" || username == "" || email == "" || password == "") {
                Toast.makeText(requireContext(), "Hãy Nhập Đầy Đủ Thông Tin", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.signup(name, username, email, password)
                observeViewModel()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.signupResult.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        DataLocalManager.getInstance()
                            .setSaveUserInfo(it.data.id,
                                it.data.name,
                                it.data.username,
                                it.data.email,
                                it.data.role.first()
                            )
                    }
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    val errorMessage = resource.message ?: "Unknown error occurred"
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

