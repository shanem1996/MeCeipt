package com.example.meceipt.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.meceipt.R

class SignUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val btnSignUpPage = findViewById<Button>(R.id.btnSignUpPage)
        btnSignUpPage.setOnClickListener {
            registerUser()
        }

    }




    private fun registerUser(){
        val fName: String = findViewById<EditText>(R.id.tfFName).text.toString().trim() { it <= ' '}
        val lName: String = findViewById<EditText>(R.id.tfLName).text.toString().trim() { it <= ' '}
        val email: String = findViewById<EditText>(R.id.tfEmail).text.toString().trim() { it <= ' '}
        val password: String = findViewById<EditText>(R.id.tfPassword).text.toString().trim()

        if(validateForm(fName, lName,  email, password)) {
            Toast.makeText(
                this@SignUpActivity,
                "Now we can register a new user.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

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