package com.hoangtien2k3.news_app.ui.auth.viewpager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.hoangtien2k3.news_app.databinding.ActivitySignupBinding
import com.hoangtien2k3.news_app.network.result.SignupResult
import com.hoangtien2k3.news_app.ui.main.MainActivity

class SignupActivity : AppCompatActivity() {

    private lateinit var viewModel: SignupViewModel
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[SignupViewModel::class.java]

        binding.sigUpButton.setOnClickListener {
            val name = binding.txtName.text.toString()
            val username = binding.txtUsername.text.toString()
            val email = binding.txtEmail.text.toString()
            val password = binding.txtPassword.text.toString()

            viewModel.signup(name, username, email, password)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.signupResult.observe(this) { result ->
            when (result) {
                is SignupResult.Success -> {
//                    val intent = Intent(this, MainActivity::class.java)
//                    startActivity(intent)
//                    finish()

                    Toast.makeText(this, result.signupResponse.message, Toast.LENGTH_SHORT).show()
                }
                is SignupResult.Error -> {
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}