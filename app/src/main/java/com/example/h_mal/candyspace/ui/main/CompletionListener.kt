package com.example.h_mal.candyspace.ui.main

interface CompletionListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
}