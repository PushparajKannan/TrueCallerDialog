package com.example.turecallerdialog.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.turecallerdialog.R
import com.example.turecallerdialog.database.AppDatabase
import com.example.turecallerdialog.databinding.FragmentDialogBinding
import com.example.turecallerdialog.model.ContactModel
import com.example.turecallerdialog.ui.viewmodel.ContactViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CustomDialogFragment : DialogFragment()
{
    companion object {

        const val TAG = "DialogFragment"

         const val KEY_TITLE = "model"

        fun newInstance(title: ContactModel): CustomDialogFragment {
            val args = Bundle()
            args.putParcelable(KEY_TITLE, title)
            val fragment = CustomDialogFragment()
            fragment.arguments = args
            return fragment
        }

    }


    val viewModel : ContactViewModel by lazy {
        val database = AppDatabase.getInstance(requireContext())
        val factory = ContactViewModel.Factory(requireContext(), database.contactDao())
        ViewModelProvider(this,factory).get(ContactViewModel::class.java)
    }

    lateinit var binding : FragmentDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog, container, false)

       binding.viewModel = viewModel
       binding.lifecycleOwner = this

        binding.saveBtn.setOnClickListener { v->

            if(!TextUtils.isEmpty(binding.name.text.toString().trim())) {
                lifecycleScope.launch(Dispatchers.IO) {
                    arguments?.getParcelable<ContactModel>(KEY_TITLE)?.let {
                        it.dummyName = binding.name.text.toString().trim()
                        viewModel.insertNickName(it)
                    }
                }
                dismiss()
            }else{
                binding.name.error = "Please Enter Nick Name"
            }
        }

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        retainInstance = true
        return dialog
    }

    override fun onResume() {
        super.onResume()
        val window = dialog!!.window ?: return
        val params = window.attributes
        val metrics = resources.displayMetrics
        val width = metrics.widthPixels
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = params
    }



    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )


    }



}