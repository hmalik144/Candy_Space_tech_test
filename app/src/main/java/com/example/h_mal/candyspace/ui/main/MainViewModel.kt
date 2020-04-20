package com.example.h_mal.candyspace.ui.main

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.h_mal.candyspace.R
import com.example.h_mal.candyspace.data.api.User
import com.example.h_mal.candyspace.data.repositories.Repository
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel(
    private val repository: Repository
) : ViewModel() {

    var searchString: String? = null

    var completionListener: CompletionListener? = null

    val usersLiveData = MutableLiveData<List<User>>()
    var currentUserLiveData: User? = null

    fun submit(view: View){
        view.let { v ->
            val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }

        completionListener?.onStarted()
        if (searchString.isNullOrEmpty()){
            completionListener?.onFailure("Search box is empty")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val apiResponse = repository.getUsers(searchString!!)

                apiResponse.items?.let {
                    if (it.isNotEmpty()){
                        //update live data
                        usersLiveData.value = it
                        //successfully finished
                        completionListener?.onSuccess()
                        return@launch
                    }
                }
                completionListener?.onFailure("No Users found")
            }catch (e: IOException){
                completionListener?.onFailure(e.message!!)
            }
        }
    }

    fun setCurrentUser(user: User) {
        currentUserLiveData = user
    }
}
