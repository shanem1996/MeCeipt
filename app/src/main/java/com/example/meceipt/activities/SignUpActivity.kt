package com.example.meceipt.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.meceipt.R
import com.example.meceipt.firebase.FirestoreClass
import com.example.meceipt.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val btnSignUpPage = findViewById<Button>(R.id.btnSignUpPage)
        btnSignUpPage.setOnClickListener {
            registerUser()
        }

        val btnSignUpBusiness = findViewById<Button>(R.id.btnSignUpBusiness)
        btnSignUpBusiness.setOnClickListener {
            startActivity(Intent(this, BusinessSignUpActivity::class.java))
        }

    }

    fun userRegisteredSuccess() {
        Toast.makeText(this, "Registration Successful!", Toast.LENGTH_LONG).show()
        FirebaseAuth.getInstance().signOut()
        finish()

    }



//    Getting the text field inputs and assigning them to variables
    private fun registerUser(){
        val fName: String = findViewById<EditText>(R.id.tfFName).text.toString().trim() { it <= ' '}
        val lName: String = findViewById<EditText>(R.id.tfLName).text.toString().trim() { it <= ' '}
        val email: String = findViewById<EditText>(R.id.tfEmail).text.toString().trim() { it <= ' '}
        val password: String = findViewById<EditText>(R.id.tfPassword).text.toString().trim()

//        Checking for validation of sign up and registering user
        if(validateForm(fName, lName,  email, password)) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val firebaseUser : FirebaseUser = task.result!!.user!!
                    val user = User(firebaseUser.uid, fName, lName, email)
                    FirestoreClass().registerUser(this, user)
                    val btnSignUpPage = findViewById<Button>(R.id.btnSignUpPage)
                    btnSignUpPage.setOnClickListener {
                        startActivity(Intent(this, HomeActivity::class.java))
                    }


                } else {
                    Toast.makeText(this, "Registration failed. Please try again", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

//    Validations for sign up page
    private fun validateForm(fName: String, lName: String, email: String, password: String) : Boolean {
        return when {
            TextUtils.isEmpty(fName)->{
                showErrorSnackBar("Please enter a first name")
                false
            }
            TextUtils.isEmpty(lName)->{
                showErrorSnackBar("Please enter a last name")
                false
            }
            TextUtils.isEmpty(email)->{
                showErrorSnackBar("Please enter an email address")
                false
            }
            TextUtils.isEmpty(password)->{
                showErrorSnackBar("Please enter a password")
                false
            } else -> {
                true
            }

        }
    }
}