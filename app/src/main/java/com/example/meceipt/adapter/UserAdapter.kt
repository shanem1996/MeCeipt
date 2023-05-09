package com.example.meceipt.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.meceipt.R
import com.example.meceipt.databinding.UserReceiptItemBinding
import com.example.meceipt.models.User

class UserAdapter(private val userList: List<User>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserViewHolder(view)
    }


    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = UserReceiptItemBinding.bind(itemView)
        private val deleteBtn = binding.btnDelete
        private val userNameTv: TextView = itemView.findViewById(R.id.tvUserName)
        private val userEmailTv: TextView = itemView.findViewById(R.id.tvUserEmail)

        fun bind(user: User) {
            userNameTv.text = user.fName
            userEmailTv.text = user.email

            deleteBtn.setOnClickListener{
                val deleteUserDialogBuilder = AlertDialog.Builder(itemView.context)
                deleteUserDialogBuilder.setTitle("Delete User")
                deleteUserDialogBuilder.setMessage("Are you sure you want to delete this user?")
                deleteUserDialogBuilder.setPositiveButton("Delete User") { dialog, _ ->

                }

                deleteUserDialogBuilder.setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()

                }

                val deleteUserDialog = deleteUserDialogBuilder.create()
                deleteUserDialog.setOnShowListener {
                    deleteUserDialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(
                        ContextCompat.getColor(itemView.context, R.color.black))
                    deleteUserDialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(
                        ContextCompat.getColor(itemView.context, R.color.black))
                }

                deleteUserDialog.show()
            }

        }
    }
}