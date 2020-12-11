package com.example.turecallerdialog.utility

import android.app.Application
import com.example.turecallerdialog.database.AppDatabase

class ApplicationClass : Application()
{
    companion object{
        lateinit var appdataBase : AppDatabase
    }
    override fun onCreate() {
        super.onCreate()

        appdataBase = AppDatabase.getInstance(this)
    }
}