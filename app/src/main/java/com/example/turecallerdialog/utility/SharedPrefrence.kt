package com.example.turecallerdialog.utility

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class SharedPrefrence {

    private var mCtx: Context? = null
    var APPDATA = "MyUSERDATA"
    var preferences: SharedPreferences? = null
    var prefEditor: SharedPreferences.Editor? = null

     constructor(context: Context){
        mCtx = context
        preferences = context.getSharedPreferences(APPDATA, Context.MODE_PRIVATE)
        prefEditor = preferences!!.edit()
         prefEditor!!.commit()
         prefEditor!!.apply()
    }


    companion object {
        private var mInstance: SharedPrefrence? = null

        fun getInstance(context: Context): SharedPrefrence {
            if (mInstance == null) {
                mInstance = SharedPrefrence(context)
            }
            return mInstance!!
        }
    }

    fun addData(Key: String?, Value: String?) {
        if (prefEditor != null) {
            prefEditor!!.putString(Key, Value)
            prefEditor!!.apply()
            prefEditor!!.commit()
        }else{
            preferences = mCtx!!.getSharedPreferences(APPDATA, Context.MODE_PRIVATE)
            prefEditor = preferences!!.edit()
            prefEditor!!.putString(Key, Value)
            prefEditor!!.apply()
            prefEditor!!.commit()
            Log.e("nullPrefrence","-->")
        }
    }

    fun readData(Key: String?, defValue: String?): String? {
        return preferences!!.getString(Key, defValue)
    }

    fun clear(key: String?): Boolean {
        return if (prefEditor != null) {
            prefEditor!!.remove(key)
            prefEditor!!.apply()
            prefEditor!!.commit()
        } else {
            false
        }
    }

    fun clearUserEntry(): Boolean {
        return if (prefEditor != null) {
            prefEditor!!.clear()
            prefEditor!!.apply()
            prefEditor!!.commit()
        } else {
            false
        }
    }
}