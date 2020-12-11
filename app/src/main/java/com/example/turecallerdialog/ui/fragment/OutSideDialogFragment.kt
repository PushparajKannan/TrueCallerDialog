package com.example.turecallerdialog.ui.fragment

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.turecallerdialog.R
import com.example.turecallerdialog.databinding.FragmentDialogOutsideBinding
import com.example.turecallerdialog.model.ContactModel

class OutSideDialogFragment(val dismissed : (Boolean) -> Unit)  : DialogFragment()
    {
        companion object {

        const val TAG = "OutSideDialogFragment"

        const val KEY_TITLE = "model"

        fun newInstance(title: ContactModel, dismissed : (Boolean) -> Unit): OutSideDialogFragment {
            val args = Bundle()
            args.putParcelable(KEY_TITLE, title)
            val fragment = OutSideDialogFragment(dismissed)
            fragment.arguments = args
            return fragment
        }

    }




        lateinit var binding : FragmentDialogOutsideBinding

        override fun onCreateView(
                inflater: LayoutInflater,
                container: ViewGroup?,
                savedInstanceState: Bundle?
        ): View? {

            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog_outside, container, false)


            arguments?.let {

                binding.model = it.getParcelable(KEY_TITLE)

            }

            binding.lifecycleOwner = this

            binding.close.setOnClickListener { v->
                dismiss()

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


        override fun onCancel(dialog: DialogInterface) {
            dismissed(true)
            super.onCancel(dialog)
        }

        override fun onDismiss(dialog: DialogInterface) {
            dismissed(true)
            super.onDismiss(dialog)


        }
    }
