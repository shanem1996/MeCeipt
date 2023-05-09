package com.example.meceipt.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.meceipt.databinding.ActivityAdminHomeBinding
import com.example.meceipt.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AdminHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val firestore = FirebaseFirestore.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser!!.uid
        val docRef = firestore.collection("User").document(userId)

        val welcomeTf = binding.tfWelcomeAdmin

        docRef.get().addOnSuccessListener { documentSnaphot ->
            val name = documentSnaphot.getString("fName")
            welcomeTf.text = "Welcome back $name, choose an option below!"

        }

        val usersBtn = binding.btnUsers

        usersBtn.setOnClickListener {
            val users = Intent(this, UsersActivity::class.java)
            startActivity(users)
        }
    }
}