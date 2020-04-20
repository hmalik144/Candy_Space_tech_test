package com.example.h_mal.candyspace.data.repositories

import androidx.lifecycle.LiveData
import com.example.h_mal.candyspace.data.api.ApiClass
import com.example.h_mal.candyspace.data.api.ApiResponse
import com.example.h_mal.candyspace.data.api.ResponseUnwrap
import com.example.h_mal.candyspace.data.api.User

class Repository(
    private val api: ApiClass
): ResponseUnwrap() {

    suspend fun getUsers(username: String): ApiResponse {
        return responseUnwrap { api.getUsersFromApi(username) }
    }
}