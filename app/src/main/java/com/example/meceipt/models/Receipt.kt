package com.example.meceipt.models

data class Receipt(
    val companyName: String = "",
    val address: String = "",
    val date: String = "",
    val transactionNumber: Int? = null,
    val totalAmount: Double = 0.0,
    val items: List<Item>? = null

)

data class Item(
    val name: String = "",
    val idCode: Int? = null,
    val price: Double = 0.0,
    val quantity: Int = 1
)
