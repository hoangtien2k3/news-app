package com.hoangtien2k3.news_app.ui.account.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.activity.main.MainActivity
import com.hoangtien2k3.news_app.data.models.DialogArguments
import com.hoangtien2k3.news_app.data.sharedpreferences.DataLocalManager
import com.hoangtien2k3.news_app.databinding.FragmentSignInBinding
import com.hoangtien2k3.news_app.ui.account.AccountViewModel
//import com.hoangtien2k3.news_app.ui.dialog.DialogFragmentDirections
//import com.hoangtien2k3.news_app.ui.dialog.DialogFragmentDirections

import com.hoangtien2k3.news_app.utils.LoadingScreen
import com.hoangtien2k3.news_app.utils.Resource
import com.hoangtien2k3.news_app.utils.viewBinding

class LoginFragment : Fragment(R.layout.fragment_sign_in) {
    private val TAG: String = "TOKEN_KEY"

    private val binding by viewBinding(FragmentSignInBinding::bind)
    private val viewModel: AccountViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickListener()
    }

    private fun onClickListener() {
        binding.apply {
            txtNextApp.setOnClickListener {
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


//                    LoadingScreen.hideLoading()
//                    val action =
//                        LoginFragmentDirections.actionLoginFragmentToDialogFragment(
//                            DialogArguments(
//                                getString(R.string.congratulations),
//                                getString(R.string.successful_sign_in),
//                                R.drawable.dialog_profile,
//                                DialogFragmentDirections.actionDialogFragmentToHomeFragment()
//                            )
//                        )
//                    findNavController().navigate(action)

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
                    LoadingScreen.displayLoading(requireContext(), false)
                }
            }
        }
    }

}
