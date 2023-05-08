package com.example.meceipt.adapter

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.meceipt.R
import com.example.meceipt.activities.HomeActivity
import com.example.meceipt.activities.ReceiptFragment
import com.example.meceipt.databinding.ReceiptItemBinding
import com.example.meceipt.databinding.UserReceiptItemBinding
import com.example.meceipt.models.UserReceipt
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

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
        private val binding = UserReceiptItemBinding.bind(itemView)
        private val companyNameTv = binding.tvCompanyName
        private val dateTv = binding.tvDate
        private val deleteBtn = binding.btnDelete


        fun bind(userReceipt: UserReceipt) {
            companyNameTv.text = userReceipt.companyName
            dateTv.text = userReceipt.date.toString()


            val transactionNumber = userReceipt.transactionNumber.toString()

            companyNameTv.setOnClickListener {
                val user = FirebaseAuth.getInstance().currentUser?.uid.toString()
                val documentRef = FirebaseFirestore.getInstance().collection("User").document(user).collection("Receipt").document(transactionNumber)

                documentRef.get().addOnSuccessListener { documentSnapshot ->
                    val name = documentSnapshot.getString("companyName")
                    val date = documentSnapshot.getString("date")
                    val totalCost = documentSnapshot.getDouble("totalCost").toString()
                    val transNumber = documentSnapshot.get("transactionNumber")

                    val receiptDialogBuilder = AlertDialog.Builder(itemView.context)
                    receiptDialogBuilder.setTitle("Receipt from $name on $date")
                    receiptDialogBuilder.setMessage("Store: $name\n" +
                            "Date: $date\n" +
                            "Transaction No.: $transNumber\n" +
                            "Total Cost: €$totalCost")
                    receiptDialogBuilder.setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()

                    }
                    val receiptDialog = receiptDialogBuilder.create()
                    receiptDialog.setOnShowListener {
                        receiptDialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
                    }
                    receiptDialog.show()


                }



            }


            deleteBtn.setOnClickListener{
                val deleteReceiptDialogBuilder = AlertDialog.Builder(itemView.context)
                deleteReceiptDialogBuilder.setTitle("Delete Receipt")
                deleteReceiptDialogBuilder.setMessage("Are you sure you want to delete this receipt?")
                deleteReceiptDialogBuilder.setPositiveButton("Delete Receipt") { dialog, _ ->
                    val user = FirebaseAuth.getInstance().currentUser?.uid.toString()
                    val documentRef = FirebaseFirestore.getInstance().collection("User").document(user).collection("Receipt").document(transactionNumber)
                    val receiptDocRef = FirebaseFirestore.getInstance().collection("Receipt").document(transactionNumber)


                    receiptDocRef.delete()
                    documentRef.delete()
                    Toast.makeText(itemView.context, "Receipt Deleted!", Toast.LENGTH_SHORT)
                        .show()
                    val receiptFragment = Intent(itemView.context, HomeActivity::class.java)
                    itemView.context.startActivity(receiptFragment)

                }

                deleteReceiptDialogBuilder.setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()

                }

                val deleteReceiptDialog = deleteReceiptDialogBuilder.create()
                deleteReceiptDialog.setOnShowListener {
                    deleteReceiptDialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
                    deleteReceiptDialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
                }

                deleteReceiptDialog.show()

            }
        }
    }
}