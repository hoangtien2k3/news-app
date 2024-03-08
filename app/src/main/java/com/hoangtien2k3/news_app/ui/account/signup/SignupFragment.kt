package com.hoangtien2k3.news_app.ui.account.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hoangtien2k3.news_app.databinding.FragmentSignUpBinding
import com.hoangtien2k3.news_app.network.result.SignupResult

class SignupFragment : Fragment() {
    private lateinit var viewModel: SignupViewModel
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(SignupViewModel::class.java)

        binding.signUpBtn.setOnClickListener {
            val name = binding.editNameSignUp.text.toString()
            val username = binding.editUsernameSignUp.text.toString()
            val email = binding.editEmailSignUp.text.toString()
            val password = binding.editPassSignUp.text.toString()

            viewModel.signup(name, username, email, password)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.signupResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is SignupResult.Success -> {
                    Toast.makeText(requireContext(), result.signupResponse.message, Toast.LENGTH_SHORT).show()
                }
                is SignupResult.Error -> {
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
