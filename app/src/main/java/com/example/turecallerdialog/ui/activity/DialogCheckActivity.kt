package com.example.turecallerdialog.ui.activity

import android.os.Bundle
import com.example.turecallerdialog.R
import com.example.turecallerdialog.databinding.ActivityDialogCheckBinding
import com.example.turecallerdialog.model.ContactModel
import com.example.turecallerdialog.ui.fragment.OutSideDialogFragment


class DialogCheckActivity : BaseActivity<ActivityDialogCheckBinding>(R.layout.activity_dialog_check) {


   lateinit var binding : ActivityDialogCheckBinding




    override fun onActivityCreated(
        dataBinder: ActivityDialogCheckBinding,
        savedInstanceState: Bundle?
    ) {

        binding = dataBinder


        intent.getParcelableExtra<ContactModel>("model")?.let {

            val newFragment = OutSideDialogFragment.newInstance(it) { data ->

                finish()
            }


            newFragment.show(supportFragmentManager,OutSideDialogFragment.TAG)


        }



    }
}