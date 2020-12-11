package com.example.turecallerdialog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.turecallerdialog.R
import com.example.turecallerdialog.databinding.ItemSavedContactBinding
import com.example.turecallerdialog.model.ContactModel

class NickNameApadter(val listener : (ContactModel) -> Unit) : PagingDataAdapter<ContactModel, NickNameApadter.ViewHolder>(DIFF_CALLBACK) {

    override
    fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val concert: ContactModel? = getItem(position)

        concert?.let {
            holder.bind(it,listener)

        }
        // Note that "concert" is a placeholder if it's null.
    }


    class ViewHolder(itemView: ItemSavedContactBinding) : RecyclerView.ViewHolder(itemView.root) {

        val bindingItem = itemView

        fun bind(task: ContactModel, clickListener: (ContactModel) -> Unit) {
            // itemView.taskTitle.text = task.title
            bindingItem.model = task
            itemView.setOnClickListener { clickListener(task) }
        }
    }


    companion object {
        private val DIFF_CALLBACK = object :
                DiffUtil.ItemCallback<ContactModel>() {
            // Concert details may have changed if reloaded from the database,
            // but ID is fixed.
            override fun areItemsTheSame(oldConcert: ContactModel,
                                         newConcert: ContactModel) = oldConcert.phoneNumber == newConcert.phoneNumber

            override fun areContentsTheSame(oldConcert: ContactModel,
                                            newConcert: ContactModel) = oldConcert == newConcert
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding: ItemSavedContactBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.item_saved_contact,
                parent,
                false
        )


        return ViewHolder(binding)
    }
}