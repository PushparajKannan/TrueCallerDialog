package com.example.turecallerdialog.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.turecallerdialog.database.dao.ContactDao
import com.example.turecallerdialog.model.ContactModel
import com.example.turecallerdialog.utility.DataRepository

class ContactViewModel constructor(val context: Context,val contactDao : ContactDao) : ViewModel() {




    fun getContact() = DataRepository(context,contactDao).fetchContacts()

    suspend fun insertNickName(model : ContactModel) {
        DataRepository(context,contactDao).insertData(model)
    }


    class Factory(
        val context: Context,val database : ContactDao
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ContactViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ContactViewModel(context,database) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel")
        }

    }

}