package com.example.h_mal.candyspace.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.h_mal.candyspace.R
import com.example.h_mal.candyspace.data.api.model.User
import com.example.h_mal.candyspace.databinding.MainFragmentBinding
import com.example.h_mal.candyspace.ui.main.MainActivity.Companion.viewModel
import com.example.h_mal.candyspace.ui.user.ListItemViewModel
import com.example.h_mal.candyspace.ui.user.UserProfileFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder


/**
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
        //observer the live data of what is retrieved from the API call
        viewModel.usersLiveData.observe(viewLifecycleOwner, Observer {
            //create adapter for viewbinding into the recycler view
            val mAdapter = GroupAdapter<ViewHolder>().apply {
                addAll(it.toUserViewModels())
            }

            //setup the recyclerview
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = mAdapter
            }

            /*
             * Item click listener for recyclerView
             *
             */
            mAdapter.setOnItemClickListener { item, _ ->
                //get the position of the item clicked
                val i = mAdapter.getAdapterPosition(item)
                //set user in @MainViewModel
                viewModel.setCurrentUser(it[i])

                /**
                 * display [UserProfileFragment]
                 */
                activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.container,
                        UserProfileFragment()
                    )
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
