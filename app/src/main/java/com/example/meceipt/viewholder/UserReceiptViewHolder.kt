package com.example.meceipt.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meceipt.R
import com.example.meceipt.models.Receipt
import com.example.meceipt.models.UserReceipt

class UserReceiptViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    private val companyNameTv: TextView = itemView.findViewById(R.id.tvCompanyName)
    private val dateTv: TextView = itemView.findViewById(R.id.tvDate)

    fun bind(userReceipt: UserReceipt) {
        companyNameTv.text = userReceipt.companyName
        dateTv.text = userReceipt.date
    }
}