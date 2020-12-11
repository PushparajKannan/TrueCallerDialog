package com.example.turecallerdialog.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.turecallerdialog.database.dao.ContactDao

class MainViewModel constructor(val context: Context,val contactDao : ContactDao): ViewModel()
{


    val pagingSource =  { contactDao.getNickNamedContact()}


    fun getNickNameContact() = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = pagingSource
    ).flow.cachedIn(viewModelScope)




    class Factory(
        val context: Context,val database : ContactDao
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(context,database) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel")
        }

    }

}