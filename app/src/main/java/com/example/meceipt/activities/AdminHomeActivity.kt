package com.example.meceipt.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.meceipt.databinding.ActivityAdminHomeBinding
import com.example.meceipt.databinding.ActivitySignUpBinding

class AdminHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}