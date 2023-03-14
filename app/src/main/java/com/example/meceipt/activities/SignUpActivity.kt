package com.example.meceipt.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.controls.ControlsProviderService.TAG
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import java.sql.Timestamp
import java.time.Instant.now
import java.time.LocalDate
import java.time.LocalDate.now
import java.time.LocalDateTime
import java.time.LocalTime.now

class SignUpActivity : AppCompatActivity() {

    private val firestore = FirebaseFirestore.getInstance()
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    @RequiresApi(Build.VERSION_CODES.O)
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



                } else if (email.contains("@meceipt.com", ignoreCase = true)) {
                    Snackbar.make(view, "Email address cannot be an MeCeipt email", Snackbar.LENGTH_LONG).show()

                } else {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
                        if (it.isSuccessful){

                            val currentUser = FirebaseAuth.getInstance().currentUser
                            val userId = currentUser!!.uid
                            val userDocRef = firestore.collection("User").document(userId)

                            val user = hashMapOf(
                                "fName" to fName,
                                "lName" to lName,
                                "email" to email
                            )

                            val receipt = hashMapOf(
                                "date" to LocalDateTime.now(),
                                "total" to 10.00,
                                "items" to listOf(
                                    hashMapOf(
                                        "name" to "Item 1",
                                        "price" to 5.00
                                    ),
                                    hashMapOf(
                                        "name" to "Item 2",
                                        "price" to 5.00
                                    )
                                )
                            )

                            val batch = FirebaseFirestore.getInstance().batch()

                            batch.set(userDocRef, user)

                            val receiptCollectionRef = userDocRef.collection("Receipt")
                            batch.set(receiptCollectionRef.document(), receipt)
                            batch.commit()

                            userDocRef.set(user).addOnSuccessListener {

                                val home = Intent(this, HomeActivity::class.java)
                                startActivity(home)
                            }



                        } else {
                            Snackbar.make(view, it.exception.toString(), Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            } else {
                Snackbar.make(view, "All fields must be filled", Snackbar.LENGTH_LONG).show()

            }
        }
        binding.btnSignUpBusiness.setOnClickListener {
            val business = Intent(this, BusinessSignUpActivity::class.java)
            startActivity(business)
        }


    }
}