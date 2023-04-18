package com.example.meceipt.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.meceipt.R
import com.example.meceipt.databinding.ActivitySignInBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnSignIn.setOnClickListener {
            val email = binding.tfEmailSignIn.text.toString().trim()
            val password = binding.tfPasswordSignIn.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                if (email.contains("@meceipt.com")) {
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val currentUser = FirebaseAuth.getInstance().currentUser
                            val userId = currentUser!!.uid
                            val userDocRef = firestore.collection("Business").document(userId)
                            val home = Intent(this, BusinessHomeActivity::class.java)
                            startActivity(home)
                            userDocRef.get().addOnSuccessListener { documentSnapshot ->
                                val name = documentSnapshot.getString("companyName")
                                Toast.makeText(this, "Welcome back to your $name! MeCeipt account!", Toast.LENGTH_SHORT)
                                    .show()
                            }


                        } else {
                            Snackbar.make(
                                view,
                                "Login Failed - Invalid email or password",
                                Snackbar.LENGTH_LONG
                            ).show()

                        }

                    }
                } else {
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val currentUser = FirebaseAuth.getInstance().currentUser
                            val userId = currentUser!!.uid
                            val userDocRef = firestore.collection("User").document(userId)

                            userDocRef.get().addOnSuccessListener { documentSnapshot ->
                                val admin = documentSnapshot.getBoolean("admin")
                                if (admin != null && admin) {
                                    val name = documentSnapshot.getString("fName")
                                    Toast.makeText(this, "Welcome back, $name!", Toast.LENGTH_SHORT)
                                        .show()
                                    val adminHome = Intent(this, AdminHomeActivity::class.java)
                                    startActivity(adminHome)
                                } else {
                                    val name = documentSnapshot.getString("fName")
                                    Toast.makeText(this, "Welcome back, $name!", Toast.LENGTH_SHORT)
                                        .show()
                                    val home = Intent(this, HomeActivity::class.java)
                                    startActivity(home)
                                }

                            }


                        } else {
                            Snackbar.make(
                                view,
                                "Login Failed - Invalid email or password",
                                Snackbar.LENGTH_LONG
                            ).show()

                        }
                    }

                }
            }


        }
    }
}