package com.example.myapplication.data.model

data class Product(
    val departmentId: String,
    val desc: String,
    val id: String,
    val imageUrl: String,
    val name: String,
    val price: String,
    val type: String
)

fun MockProduct(): Product {
    return Product(
        departmentId = "1",
        desc = "desc",
        id ="1",
        imageUrl = "",
        name = "name",
        price = "price",
        type = "type"
    )
}