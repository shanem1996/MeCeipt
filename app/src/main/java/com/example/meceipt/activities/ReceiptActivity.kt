package com.example.meceipt.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meceipt.adapter.ReceiptAdapter
import com.example.meceipt.databinding.ActivityReceiptBinding
import com.example.meceipt.databinding.FragmentReceiptBinding
import com.example.meceipt.databinding.ReceiptItemBinding
import com.example.meceipt.models.Receipt
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ReceiptActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReceiptBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceiptBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val receiptQuery = FirebaseFirestore.getInstance().collection("Receipt").orderBy("date", Query.Direction.DESCENDING)

        receiptQuery.get().addOnSuccessListener { documents ->
            val receiptList = mutableListOf<Receipt>()
            for (document in documents) {
                val receipt = document.toObject(Receipt::class.java)
                receiptList.add(receipt)
            }

            val adapter = ReceiptAdapter(receiptList)

            val recyclerView = binding.receiptRecyclerView
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)

            val receiptItemBinding: ReceiptItemBinding = ReceiptItemBinding.inflate(layoutInflater)
            val addBtn = receiptItemBinding.btnAdd
            val tv = receiptItemBinding.tvCompanyName

            tv.setOnClickListener {
                Toast.makeText(this, "Receipt Added!", Toast.LENGTH_SHORT)
                    .show()
            }


            addBtn.setOnClickListener {
                val receipt = Intent(this, FragmentReceiptBinding::class.java)
                startActivity(receipt)
                Toast.makeText(this, "Receipt Added!", Toast.LENGTH_SHORT)
                    .show()
            }


        }


    }


}