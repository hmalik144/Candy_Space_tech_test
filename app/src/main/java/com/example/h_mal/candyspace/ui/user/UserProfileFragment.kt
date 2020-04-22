package com.example.h_mal.candyspace.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.h_mal.candyspace.R
import com.example.h_mal.candyspace.databinding.FragmentUserProfileBinding
import com.example.h_mal.candyspace.ui.main.MainActivity.Companion.viewModel
import com.squareup.picasso.Picasso

/**
 * A simple [Fragment] subclass.
 * Use the [UserProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserProfileFragment : Fragment() {

    companion object {
        fun newInstance() =
            UserProfileFragment()
    }

    lateinit var binding: FragmentUserProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment into data binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_profile, container, false)

        return binding.root
    }

    //Update the data for viewbinding onResume as data would have changed when selecting a new user
    override fun onResume() {
        super.onResume()
        viewModel.currentUserLiveData?.let {
            binding.user = it
            binding.lifecycleOwner = this

            Picasso.get().load(it.profileImage).placeholder(R.mipmap.ic_launcher).into(binding.imageView)
        }
    }

}
