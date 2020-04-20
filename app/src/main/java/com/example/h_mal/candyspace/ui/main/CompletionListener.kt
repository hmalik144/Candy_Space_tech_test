package com.example.h_mal.candyspace.ui.main

/**
 * completion listener for [MainViewModel] when handling async calls
 */
interface CompletionListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
}