package com.santoso.loginmfa.ui.register

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.santoso.loginmfa.viewmodel.AuthViewModel
import com.santoso.loginmfa.util.GoogleMFAHelper
import com.santoso.loginmfa.util.QRCodeHelper
import com.santoso.loginmfa.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var secret: String
    private lateinit var binding: ActivityRegisterBinding
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        secret = GoogleMFAHelper.generateSecret()
        val otpUrl = GoogleMFAHelper.getOtpAuthUrl("newuser", "MyApp", secret)
        binding.ivMfaQr.setImageBitmap(QRCodeHelper.generateQRCode(otpUrl))

        binding.btnRegister.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username and password cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.register(username, password, secret)
        }

        viewModel.registerResult.observe(this) {
            if (it == true) {
                Toast.makeText(this, "Register Success", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
}