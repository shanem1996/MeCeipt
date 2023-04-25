package com.example.meceipt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.meceipt.R
import com.example.meceipt.databinding.ReceiptItemBinding
import com.example.meceipt.models.UserReceipt

class UserReceiptAdapter(private val receiptList: List<UserReceipt>) : RecyclerView.Adapter<UserReceiptAdapter.UserReceiptViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserReceiptViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_receipt_item, parent, false)
        return UserReceiptViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserReceiptViewHolder, position: Int) {
        holder.bind(receiptList[position])
    }

    override fun getItemCount(): Int {
        return receiptList.size
    }

    inner class UserReceiptViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ReceiptItemBinding.bind(itemView)
        private val companyNameTv = binding.tvCompanyName
        private val dateTv = binding.tvDate
        private val addBtn = binding.btnAdd

        fun bind(userReceipt: UserReceipt) {
            companyNameTv.text = userReceipt.companyName
            dateTv.text = userReceipt.date.toString()

            addBtn.setOnClickListener{

            }
        }
    }
}