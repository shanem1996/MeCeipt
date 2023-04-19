package com.example.meceipt.models

data class Receipt(
    val companyName: String = "",
    val address: String = "",
    val date: String = "",
    val transactionNumber: Int? = null
)
