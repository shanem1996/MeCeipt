package com.example.meceipt.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.controls.ControlsProviderService.TAG
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.meceipt.R
import com.example.meceipt.databinding.ActivitySignUpBinding
import com.example.meceipt.firebase.FirestoreClass
import com.example.meceipt.models.User
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    val firestore = FirebaseFirestore.getInstance()
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        firebaseAuth = FirebaseAuth.getInstance()

        //Listening for the Sign Up button to be pressed
        binding.btnSignUpPage.setOnClickListener {
            val fName = binding.tfFName.text.toString().trim()
            val lName = binding.tfLName.text.toString().trim()
            val email = binding.tfEmail.text.toString().trim()
            val password = binding.tfPassword.text.toString().trim()
            val confPassword = binding.tfConfPassword.text.toString().trim()

            if (fName.isNotEmpty() && lName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confPassword.isNotEmpty()){
                if (password != confPassword) {
                    Snackbar.make(view, "Passwords do not match", Snackbar.LENGTH_LONG).show()

                } else {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
                        if (it.isSuccessful){

                            val currentUser = FirebaseAuth.getInstance().currentUser
                            val userId = currentUser!!.uid
                            val docRef = firestore.collection("User").document(userId)

                            val user = hashMapOf(
                                "fName" to fName,
                                "lName" to lName,
                                "email" to email
                            )

                            docRef.set(user).addOnSuccessListener {

                                    val intent = Intent(this, HomeActivity::class.java)
                                    startActivity(intent)
                            }



//                                .addOnSuccessListener {
//                                    val intent = Intent(this, HomeActivity::class.java)
//                                    startActivity(intent)
//                                }
//                                .addOnFailureListener { e ->
//                                    val intent = Intent(this, SignUpActivity::class.java)
//                                    startActivity(intent)
//                                }




                        } else {
                            Snackbar.make(view, it.exception.toString(), Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            } else {
                Snackbar.make(view, "All fields must be filled", Snackbar.LENGTH_LONG).show()

            }
        }


    }
}