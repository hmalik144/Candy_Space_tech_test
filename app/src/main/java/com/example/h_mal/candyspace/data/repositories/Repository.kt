package com.example.h_mal.candyspace.data.repositories

import com.example.h_mal.candyspace.data.api.ApiClass
import com.example.h_mal.candyspace.data.api.model.ApiResponse
import com.example.h_mal.candyspace.data.api.ResponseUnwrap

class Repository(
    private val api: ApiClass
): ResponseUnwrap() {

    //get api response from retrofit class
    //then unwrap data object from retrofit response class
    suspend fun getUsers(username: String): ApiResponse {
        return responseUnwrap { api.getUsersFromApi(username) }
    }
}