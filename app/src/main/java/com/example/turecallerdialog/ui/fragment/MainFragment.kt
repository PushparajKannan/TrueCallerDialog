package com.example.turecallerdialog.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.turecallerdialog.R
import com.example.turecallerdialog.adapter.NickNameApadter
import com.example.turecallerdialog.database.AppDatabase
import com.example.turecallerdialog.databinding.FragmentMainBinding
import com.example.turecallerdialog.ui.viewmodel.MainViewModel
import com.example.turecallerdialog.utility.setCollapsingToolbarLayoutTitle
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainFragment : Fragment()
{

    lateinit var binding : FragmentMainBinding


    lateinit var adapter : NickNameApadter

    val viewModel : MainViewModel by lazy {

        val database = AppDatabase.getInstance(requireContext())

        val factory = MainViewModel.Factory(requireContext(), database.contactDao())
        ViewModelProvider(this,factory).get(MainViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        activity?.findViewById<CollapsingToolbarLayout>(R.id.collapsingToolbarLayout_home_screen)
            ?.setCollapsingToolbarLayoutTitle(
                title = "Home",
                isTitleEnabled = true

            )
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_main, container, false)

        binding.viewModel = viewModel
        binding.fragment = this
        binding.lifecycleOwner =this
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = NickNameApadter {
            data ->

        }


        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.getNickNameContact().collectLatest { data->
                adapter.submitData(data)
            }
        }

        binding.reSavedContact.adapter = adapter



    }
}