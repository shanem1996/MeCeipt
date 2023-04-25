package com.example.meceipt.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.meceipt.R
import com.example.meceipt.databinding.ActivityBusinessHomeBinding



class BusinessHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBusinessHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusinessHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        

    }
}