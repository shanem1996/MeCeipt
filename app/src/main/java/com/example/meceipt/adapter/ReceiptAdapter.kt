package com.example.meceipt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meceipt.R
import com.example.meceipt.models.Receipt

class ReceiptAdapter(private val receiptList: List<Receipt>) : RecyclerView.Adapter<ReceiptAdapter.ReceiptViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiptViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.receipt_item, parent, false)
        return ReceiptViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReceiptViewHolder, position: Int) {
        holder.bind(receiptList[position])
    }

    override fun getItemCount(): Int {
        return receiptList.size
    }

    inner class ReceiptViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val companyNameTv: TextView = itemView.findViewById(R.id.tvCompanyName)
        private val dateTv: TextView = itemView.findViewById(R.id.tvDate)

        fun bind(receipt: Receipt) {
            companyNameTv.text = receipt.companyName
            dateTv.text = receipt.date
        }
    }
}