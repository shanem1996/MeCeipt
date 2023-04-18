package com.example.meceipt.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.meceipt.databinding.ActivityAdminHomeBinding
import com.example.meceipt.databinding.ActivityCreateReceiptBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import kotlin.random.Random

class CreateReceiptActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateReceiptBinding
    private val firestore = FirebaseFirestore.getInstance()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateReceiptBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val submitBtn = binding.btnSubmit

        submitBtn.setOnClickListener {
            val currentDate = LocalDate.now()
            val format = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val date = currentDate.format(format)

            val companyName = binding.tfCompanyName.text.toString().trim()
            val address = binding.tfCompanyAddress.text.toString().trim()

            val transaction = randomNumber()

            val receiptCollectionRef = firestore.collection("Receipt")

            val  newReceipt = hashMapOf(
                "companyName" to companyName,
                "address" to address,
                "transactionNumber" to transaction,
                "date" to date,
            )

            val documentId = transaction.toString()

            receiptCollectionRef.document(documentId).set(newReceipt).addOnSuccessListener {
                Log.d(TAG, "Document added with ID: $documentId")
            }
        }

    }


    private fun randomNumber(): Int {
        val number = java.util.Random()
        return number.nextInt(90000000) + 10000000
    }

}