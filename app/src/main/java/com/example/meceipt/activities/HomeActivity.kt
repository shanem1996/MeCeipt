package com.example.meceipt.activities

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.meceipt.R
import com.example.meceipt.databinding.ActivityHomeBinding
import com.example.meceipt.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val firestore = FirebaseFirestore.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser!!.uid
        val docRef = firestore.collection("User").document(userId)
        val nameTf = binding.name
        val qrText = userId.toString()
        val qrCodeWidth = 512
        val qrCodeHeight = 512
        val qrCodeWriter = QRCodeWriter()
        val bitMatrix = qrCodeWriter.encode(qrText, BarcodeFormat.QR_CODE, qrCodeWidth, qrCodeHeight)
        val qrCodeBitmap = Bitmap.createBitmap(qrCodeWidth, qrCodeHeight, Bitmap.Config.RGB_565)
        val qrCode = binding.qrCode


        docRef.get()
            .addOnSuccessListener { documentSnapshot ->
                val name = documentSnapshot.getString("fName")
                nameTf.text = name
            }
            .addOnFailureListener { exception ->
                Log.e("myError", "No name detected")

            }

        for (x in 0 until qrCodeWidth) {
            for (y in 0 until qrCodeHeight) {
                qrCodeBitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }

        qrCode.setImageBitmap(qrCodeBitmap)

        }





    }
