package com.example.h_mal.candyspace.data.api.model

import com.example.h_mal.candyspace.data.api.model.User

data class ApiResponse(
    val items : List<User>?,
    val has_more : Boolean?,
    val quota_max : Int?,
    val quota_Remaining: Int?
)