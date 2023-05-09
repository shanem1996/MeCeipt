package com.example.meceipt.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meceipt.R
import com.example.meceipt.models.User

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val userNameTv: TextView = itemView.findViewById(R.id.tvUserName)
    private val userEmailTv: TextView = itemView.findViewById(R.id.tvUserEmail)

    fun bind(user: User) {
        userNameTv.text = user.fName
        userEmailTv.text = user.email
    }
}