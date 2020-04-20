package com.example.h_mal.candyspace.ui.main

import com.example.h_mal.candyspace.R
import com.example.h_mal.candyspace.data.api.User
import com.example.h_mal.candyspace.databinding.ListItemLayoutBinding
import com.xwray.groupie.databinding.BindableItem

class ListItemViewModel (
    val user: User
): BindableItem<ListItemLayoutBinding>(){
    override fun getLayout(): Int = R.layout.list_item_layout

    override fun bind(viewBinding: ListItemLayoutBinding, position: Int) {
        viewBinding.user = user
    }
}