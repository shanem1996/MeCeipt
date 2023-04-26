package com.example.meceipt.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.controls.ControlsProviderService
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.meceipt.R
import com.example.meceipt.databinding.ActivityBusinessHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.zxing.integration.android.IntentIntegrator
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class BusinessHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBusinessHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusinessHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val statsTf = binding.tfStats
        val currentUser = FirebaseAuth.getInstance().currentUser?.uid.toString()

        val userDocRef = FirebaseFirestore.getInstance().collection("Business").document(currentUser)
        userDocRef.get().addOnSuccessListener { documentSnapshot ->
            val name = documentSnapshot.getString("companyName").toString()
            val query = FirebaseFirestore.getInstance().collection("Receipt").whereEqualTo("companyName", name)
            query.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val count = task.result.count().toString()
                    statsTf.text = "$name, your company has digitally distributed $count receipts! Keep up the good work!"
                }

            }
        }

        val scanBtn = binding.btnScan
        scanBtn.setOnClickListener {
            val integrator = IntentIntegrator(this)
            integrator.initiateScan()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {

            } else {
                val scannedResult = result.contents
                val userCollectionRef = FirebaseFirestore.getInstance().collection("User")
                val userDocumentRef = userCollectionRef.document(scannedResult)

                userDocumentRef.get().addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val firebaseAuth = FirebaseAuth.getInstance().currentUser
                        val uid = firebaseAuth?.uid.toString()
                        val docRef = FirebaseFirestore.getInstance().collection("Business").document(uid)
                        val receiptDocRef = FirebaseFirestore.getInstance().collection("Receipt")

                        docRef.get().addOnSuccessListener { businessDocumentSnapshot ->
                            val userReceiptColRef = FirebaseFirestore.getInstance().collection("User").document(scannedResult).collection("Receipt")
                            val name = businessDocumentSnapshot.getString("companyName").toString()

                            val transaction = randomNumber()

                            val currentDate = LocalDate.now()
                            val format = DateTimeFormatter.ofPattern("dd-MM-yyyy")
                            val date = currentDate.format(format)
                            val totalCost = randomDouble()

                            val  newReceipt = hashMapOf(
                                "companyName" to name,
                                "address" to null,
                                "transactionNumber" to transaction,
                                "date" to date,
                                "totalCost" to totalCost
                            )

                            val basicReceipt = hashMapOf(
                                "companyName" to name
                            )

                            val documentId = transaction.toString()

                            userReceiptColRef.document(documentId).set(newReceipt).addOnSuccessListener {
                                val receipt = Intent(this, BusinessHomeActivity::class.java)
                                startActivity(receipt)
                                Toast.makeText(this, "Receipt Added!", Toast.LENGTH_SHORT)
                                    .show()
                                receiptDocRef.document(documentId).set(basicReceipt)
                            }


                        }

                    } else {
                        Toast.makeText(this, "Document Not Found", Toast.LENGTH_SHORT)
                            .show()
                    }

                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun randomNumber(): Int {
        val number = java.util.Random()
        return number.nextInt(90000000) + 10000000
    }

    private fun randomDouble(): Double {
        val number1 = java.util.Random()
        val number2 = java.util.Random()
        val beforePoint = number1.nextInt(900) + 100
        val afterPoint = number2.nextInt(90) + 10
        return "$beforePoint.$afterPoint".toDouble()
    }
}