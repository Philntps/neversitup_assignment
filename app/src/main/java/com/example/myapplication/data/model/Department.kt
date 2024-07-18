package com.example.myapplication.data.model

data class Department(
    val id: String,
    val imageUrl: String,
    val name: String
)

fun MockDepartment(): Department {
    return Department(
        id="1",
        imageUrl="",
        name="department"
    )
}