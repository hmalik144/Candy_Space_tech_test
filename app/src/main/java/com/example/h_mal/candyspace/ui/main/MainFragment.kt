package com.example.h_mal.candyspace.ui.main

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.h_mal.candyspace.R
import com.example.h_mal.candyspace.databinding.MainFragmentBinding
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

/*
* UI for the first screen holding the list, search box
*/
class MainFragment : Fragment(), KodeinAware{

    //retrieve the viewmodel factory from the kodein dependency injection
    override val kodein by lazy { (context as KodeinAware).kodein }
    private val factory : MainViewModelFactory by instance()

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        //retrieve viewmodel from viewmodel factory
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        //Bind layout to Viewmodel
        val binding: MainFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        binding.viewmodel = viewModel

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }



}
