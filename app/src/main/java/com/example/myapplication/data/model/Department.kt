package com.example.myapplication.data.model

data class Department(
    val id: String,
    val imageUrl: String,
    val name: String
)

fun mockDepartment(): Department {
    return Department(
        id="1",
        imageUrl="",
        name="department"
    )
}