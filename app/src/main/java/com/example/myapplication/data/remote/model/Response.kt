package com.example.myapplication.data.remote.model

import com.example.myapplication.domain.remote.model.Response

data class Response<T>(
    override val status: String, override val data: T?, override val message: String?
):Response<T> {

}
