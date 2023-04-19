package com.example.meceipt.viewholder

import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meceipt.R
import com.example.meceipt.models.Receipt
import org.w3c.dom.Text

class ReceiptViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val companyNameTv: TextView = itemView.findViewById(R.id.tvCompanyName)
    private val dateTv: TextView = itemView.findViewById(R.id.tvDate)

    fun bind(receipt: Receipt) {
        companyNameTv.text = receipt.companyName
        dateTv.text = receipt.date
    }


}