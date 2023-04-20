package com.example.meceipt.models

data class UserReceipt(
    val companyName: String = "",
    val address: String = "",
    val date: String = "",
    val transactionNumber: Int? = null
)
