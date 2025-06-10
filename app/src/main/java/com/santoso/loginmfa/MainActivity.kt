package com.santoso.loginmfa

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.santoso.loginmfa.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val otp = binding.etOtp.text.toString().trim()
            viewModel.login(username, password, otp)
        }
        viewModel.loginResult.observe(this) {
            if (it == true) {
                Toast.makeText(this, "Login Success", Toast.LENGTH_LONG).show()
                // Proceed to main app
            } else {
                Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show()
            }
        }

        binding.tvToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}