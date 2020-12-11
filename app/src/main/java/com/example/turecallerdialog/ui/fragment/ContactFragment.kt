package com.example.turecallerdialog.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.turecallerdialog.R
import com.example.turecallerdialog.adapter.ContactListAdapter
import com.example.turecallerdialog.database.AppDatabase
import com.example.turecallerdialog.databinding.FragmentContactBinding
import com.example.turecallerdialog.ui.viewmodel.ContactViewModel
import com.example.turecallerdialog.utility.setCollapsingToolbarLayoutTitle
import com.google.android.material.appbar.CollapsingToolbarLayout

class ContactFragment  :  Fragment() {

    lateinit var binding : FragmentContactBinding

    val viewModel :  ContactViewModel by lazy {

        val database = AppDatabase.getInstance(requireContext())

        val factory = ContactViewModel.Factory(requireContext(), database.contactDao())
        ViewModelProvider(this, factory).get(ContactViewModel::class.java)
    }

    lateinit var adpater : ContactListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        activity?.findViewById<CollapsingToolbarLayout>(R.id.collapsingToolbarLayout_home_screen)
            ?.setCollapsingToolbarLayoutTitle(
                title = "Local Contact",
                isTitleEnabled = true
            )

        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_contact,
            container,
            false
        )

        binding.viewModel = viewModel
        binding.fragment = this
        binding.lifecycleOwner =this
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adpater= ContactListAdapter { data ->


            activity?.let {
                val newFragment = CustomDialogFragment.newInstance(data)
                newFragment.show(parentFragmentManager,CustomDialogFragment.TAG)

            }


        }

        viewModel.getContact().observe(viewLifecycleOwner, Observer { data ->

            adpater.setData(data)

        })

        binding.rePhoneContact.adapter = adpater



    }



}


