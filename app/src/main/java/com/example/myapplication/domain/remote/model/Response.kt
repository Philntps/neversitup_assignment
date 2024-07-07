package com.example.myapplication.domain.remote.model

interface Response<T> {
    val status: String
    val data: T?
    val message:String?
}