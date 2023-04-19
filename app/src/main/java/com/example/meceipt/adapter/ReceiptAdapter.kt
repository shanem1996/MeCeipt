package com.example.meceipt.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.meceipt.R
import com.example.meceipt.activities.HomeActivity
import com.example.meceipt.activities.ReceiptFragment
import com.example.meceipt.databinding.FragmentReceiptBinding
import com.example.meceipt.databinding.ReceiptItemBinding
import com.example.meceipt.models.Receipt
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

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
        private val binding = ReceiptItemBinding.bind(itemView)
        private val companyNameTv = binding.tvCompanyName
        private val dateTv = binding.tvDate
        private val addBtn = binding.btnAdd

        fun bind(receipt: Receipt) {
            companyNameTv.text = receipt.companyName
            dateTv.text = receipt.date


            addBtn.setOnClickListener {

                val transaction = receipt.transactionNumber
                val transactionString = transaction.toString()
                val companyName = receipt.companyName
                val date = receipt.date
                val address = receipt.address

                val currentUser = FirebaseAuth.getInstance().currentUser
                val uid = currentUser?.uid.toString()
                val userReceiptColRef = FirebaseFirestore.getInstance().collection("User").document(uid).collection("Receipt")

                val receiptData = hashMapOf(
                    "companyName" to companyName,
                    "address" to address,
                    "transactionNumber" to transaction,
                    "date" to date,
                )

                userReceiptColRef.document(transactionString).set(receiptData).addOnSuccessListener {
                    val receipt = Intent(itemView.context, HomeActivity::class.java)
                    itemView.context.startActivity(receipt)
                    Toast.makeText(itemView.context, "Receipt Added!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}