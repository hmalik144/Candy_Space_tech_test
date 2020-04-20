package com.example.h_mal.candyspace.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.h_mal.candyspace.R
import com.example.h_mal.candyspace.data.api.User
import com.example.h_mal.candyspace.databinding.MainFragmentBinding
import com.example.h_mal.candyspace.ui.MainActivity.Companion.viewModel
import com.example.h_mal.candyspace.utils.displayToast
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder


/*
* UI for the first screen holding the list, search box
*/
class MainFragment : Fragment(){

    companion object {
        fun newInstance() = MainFragment()
    }

    lateinit var binding: MainFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        //Bind layout to Viewmodel in main activity
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.usersLiveData.observe(viewLifecycleOwner, Observer {
            val mAdapter = GroupAdapter<ViewHolder>().apply {
                addAll(it.toUserViewModels())
            }

            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = mAdapter
            }

            mAdapter.setOnItemClickListener { item, _ ->
                val i = mAdapter.getAdapterPosition(item)


                viewModel.setCurrentUser(it[i])

                activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.container, UserProfileFragment())
                    .addToBackStack("user")
                    .commit()
            }

        })

    }

    private fun List<User>.toUserViewModels() : List<ListItemViewModel>{
        return this.map {
            ListItemViewModel(it)
        }
    }

}
