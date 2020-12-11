package com.example.turecallerdialog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.turecallerdialog.R
import com.example.turecallerdialog.databinding.ItemSavedContactBinding
import com.example.turecallerdialog.model.ContactModel

class ContactListAdapter(private val clickListener: (ContactModel) -> Unit) : ListAdapter<ContactModel, ContactListAdapter.ViewHolder>(TaskDiffCallbacks()) {


    private val mDiffer: AsyncListDiffer<ContactModel> = AsyncListDiffer(this, TaskDiffCallbacks())

    fun setData(list: List<ContactModel>) {
        mDiffer.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding: ItemSavedContactBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.item_saved_contact,
            parent,
            false
        )


        return ViewHolder( binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        mDiffer.currentList.get(position).let {
            holder.bind(it, clickListener)
        }
    }

    class ViewHolder(itemView: ItemSavedContactBinding) : RecyclerView.ViewHolder(itemView.root) {

        val bindingItem = itemView

        fun bind(task : ContactModel, clickListener : (ContactModel) -> Unit) {
            // itemView.taskTitle.text = task.title
            bindingItem.model = task
            itemView.setOnClickListener { clickListener(task) }
        }
    }

    override fun getItemCount(): Int {
        return  mDiffer.currentList.size
    }

}

class TaskDiffCallbacks : DiffUtil.ItemCallback<ContactModel>() {
    override fun areItemsTheSame(oldItem: ContactModel, newItem: ContactModel): Boolean {
        return oldItem.phoneNumber == newItem.phoneNumber
    }

    override fun areContentsTheSame(oldItem: ContactModel, newItem: ContactModel): Boolean {
        return oldItem == newItem
    }
}