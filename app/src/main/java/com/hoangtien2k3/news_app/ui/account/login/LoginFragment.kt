package com.hoangtien2k3.news_app.ui.account.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hoangtien2k3.news_app.activity.main.MainActivity
import com.hoangtien2k3.news_app.data.sharedpreferences.DataLocalManager
import com.hoangtien2k3.news_app.databinding.FragmentSignInBinding
import com.hoangtien2k3.news_app.ui.account.AccountViewModel
import com.hoangtien2k3.news_app.utils.Resource

class LoginFragment : Fragment() {
    private lateinit var viewModel: AccountViewModel
    private var _binding: FragmentSignInBinding? = null
    private val binding
        get() = _binding!!

    private val TAG: String = "TOKEN_KEY"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[AccountViewModel::class.java]
        onClickListener()
    }

    private fun onClickListener() {
        binding.apply {
            txtNextApp.setOnClickListener {
                DataLocalManager.getInstance().setSaveUserInfo(0, "", "", "", "")
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
            }
            imgNextApp.setOnClickListener {
                DataLocalManager.getInstance().setSaveUserInfo(0, "", "", "", "")
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
            }
            signInBtn.setOnClickListener {
                val username = binding.editEmailSignIN.text.toString()
                val password = binding.editPassSignIn.text.toString()
                if (username == "" || password == "") {
                    Toast.makeText(requireContext(), "Hãy Nhập Đầy Đủ Thông Tin", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.login(username, password)
                    observeViewModel()
                }
            }
            fbLoginBtn.setOnClickListener {

            }
            googleLoginBtn.setOnClickListener {

            }

        }
    }

    private fun observeViewModel() {
        viewModel.loginResult.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    // user login successfully and save shareFerference
                    DataLocalManager.getInstance().setFirstInstalled(true)

                    resource.data?.let {
                        DataLocalManager.getInstance().setSaveTokenKey(it.data.token)

                        Log.d(TAG,it.data.token )

                    }

                    // start MainActivity
                    startActivity(Intent(
                        requireContext(),
                        MainActivity::class.java)
                    )
                    requireActivity().finish()
                }
                is Resource.Error -> {
                    val errorMessage = resource.message ?: "Unknown error occurred"
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    // Thích nghi với việc tải dữ liệu
                    // Có thể hiển thị một tiến trình tải ở đây nếu cần
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
