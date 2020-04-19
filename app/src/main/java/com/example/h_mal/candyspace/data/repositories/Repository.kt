package com.example.h_mal.candyspace.data.repositories

import com.example.h_mal.candyspace.data.api.ApiClass
import com.example.h_mal.candyspace.data.api.ResponseUnwrap
import com.example.h_mal.candyspace.data.api.User

class Repository(
    private val api: ApiClass
): ResponseUnwrap() {

    suspend fun sdasda(username: String): User {
        return apiRequest { api.getUsersFromApi(username) }
    }
}