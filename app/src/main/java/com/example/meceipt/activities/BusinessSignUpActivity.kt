package com.example.meceipt.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import com.example.meceipt.R
import com.example.meceipt.databinding.ActivityBusinessSignUpBinding
import com.example.meceipt.databinding.ActivityHomeBinding


import com.google.android.gms.common.api.internal.ApiKey
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

class BusinessSignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBusinessSignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusinessSignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnSignUpPage.setOnClickListener {
            val companyName = binding.tfCompany.text.toString().trim()
            val password = binding.tfPassword.text.toString().trim()
            val confPassword = binding.tfConfPassword.text.toString().trim()

            if (companyName.isNotEmpty() && password.isNotEmpty() && confPassword.isNotEmpty()) {
                if (password != confPassword) {
                    Snackbar.make(view, "Passwords do not match", Snackbar.LENGTH_LONG).show()
                } else {
                    val emailLower = "$companyName@meceipt.com".lowercase()

                    firebaseAuth.createUserWithEmailAndPassword(emailLower, password).addOnCompleteListener{
                        if (it.isSuccessful){
                            val business = hashMapOf(
                                "companyName" to companyName,
                                "email" to emailLower
                            )
                            val currentUser = FirebaseAuth.getInstance().currentUser
                            val userId = currentUser!!.uid
                            val businessDocRef = firestore.collection("Business").document(userId)
                            businessDocRef.set(business).addOnSuccessListener {
                                Snackbar.make(view, "Business User Created", Snackbar.LENGTH_LONG).show()
                                val businessHome = Intent(this, BusinessHomeActivity::class.java)
                                startActivity(businessHome)

                            }

                        }
                    }

                }
            }
        }
    }
}







