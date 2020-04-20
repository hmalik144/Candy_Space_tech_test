package com.example.h_mal.candyspace.utils

import android.content.Context
import android.view.View
import android.widget.Toast

fun View.show(){
    this.visibility = View.VISIBLE
}

fun View.hide(){
    this.visibility = View.GONE
}

fun Context.displayToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}