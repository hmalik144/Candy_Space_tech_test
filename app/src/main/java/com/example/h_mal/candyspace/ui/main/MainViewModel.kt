package com.example.h_mal.candyspace.ui.main

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.h_mal.candyspace.data.repositories.Repository

class MainViewModel(
    private val repository: Repository
) : ViewModel() {

    var searchString: String? = null

    fun submit(view: View){

    }
}
