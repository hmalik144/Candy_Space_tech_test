package com.example.h_mal.candyspace.ui.main

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.h_mal.candyspace.data.api.model.User
import com.example.h_mal.candyspace.data.repositories.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel(
    private val repository: Repository
) : ViewModel() {

    // string binded to the edittext input in @R.layout.main_fragment
    var searchString: String? = null

    var completionListener: CompletionListener? = null

    //data objects - live data and var
    val usersLiveData = MutableLiveData<List<User>>()
    var currentUserLiveData: User? = null

    /**
     * view binding of the submit button in @R.layout.main_fragment
     */
    fun submit(view: View?){
        //close keyboard when clicked
        view.let { v ->
            val imm = view?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v?.windowToken, 0)
        }

        completionListener?.onStarted()
        //return if search string is empty
        if (searchString.isNullOrEmpty()){
            completionListener?.onFailure("Search box is empty")
            return
        }

        //open a coroutine on the Main thread and update views upon load
        CoroutineScope(Dispatchers.Main).launch {
            try {
                //retrieve response from API call
                val apiResponse = repository.getUsers(searchString!!)

                //unwrap list of user out of ApiResponse
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
