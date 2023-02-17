package com.example.meceipt.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.meceipt.R
import com.example.meceipt.databinding.ActivityHomeBinding
import com.example.meceipt.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val firestore = FirebaseFirestore.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser!!.uid
        val docRef = firestore.collection("User").document(userId)
        val nameTf = binding.name

        docRef.get()
            .addOnSuccessListener { documentSnapshot ->
                val name = documentSnapshot.getString("fName")
                nameTf.text = name
            }
            .addOnFailureListener { exception ->

            }



        }

    }
