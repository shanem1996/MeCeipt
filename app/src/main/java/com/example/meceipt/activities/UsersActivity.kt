package com.example.meceipt.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meceipt.adapter.UserAdapter
import com.example.meceipt.databinding.ActivitySignUpBinding
import com.example.meceipt.databinding.ActivityUsersBinding
import com.example.meceipt.models.User
import com.example.meceipt.models.UserReceipt
import com.google.firebase.firestore.FirebaseFirestore

class UsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUsersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsersBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val userQuery = FirebaseFirestore.getInstance().collection("User")

        userQuery.get().addOnSuccessListener { documents ->
            if (documents.isEmpty) {
                Toast.makeText(this, "There are no users to display", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val userList = mutableListOf<User>()
                for (document in documents) {
                    val user = document.toObject(User::class.java)
                    userList.add(user)
                }

                val adapter = UserAdapter(userList)
                val recyclerView = binding.userRecyclerView

                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this)

            }
        }
    }
}