package com.example.h_mal.candyspace.ui.main

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.h_mal.candyspace.data.repositories.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel(
    private val repository: Repository
) : ViewModel() {

    var searchString: String? = null

    val completionListener: CompletionListener? = null

    fun submit(view: View){
        completionListener?.onStarted()
        if (searchString.isNullOrEmpty()){
            completionListener?.onFailure("Search box is empty")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val user = repository.getUsers(searchString!!)
                //todo: update live data

                //successfully finished
                completionListener?.onSuccess()
            }catch (e: IOException){
                completionListener?.onFailure(e.message!!)
            }
            completionListener?.onFailure("Failed to retrieve Users")
        }
    }
}
