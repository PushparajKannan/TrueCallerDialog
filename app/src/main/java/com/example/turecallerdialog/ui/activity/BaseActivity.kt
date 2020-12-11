package com.example.turecallerdialog.ui.activity

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.Settings
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import com.example.turecallerdialog.BuildConfig
import com.example.turecallerdialog.R
import com.example.turecallerdialog.utility.SharedPrefrence
import com.google.android.material.snackbar.Snackbar


abstract class BaseActivity<in T>(@LayoutRes private val layoutResId: Int? = null): AppCompatActivity() where T: ViewDataBinding {




    lateinit var prefrence : SharedPrefrence

    final val TAG =  "BaseActivity"
     final val PERMISSIONS_REQUEST_CODE = 34


    var contactPermission  =  MutableLiveData<Boolean>(false)

    abstract fun onActivityCreated(dataBinder: T,savedInstanceState : Bundle?)


    final override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        this@BaseActivity.initial(savedInstanceState)

    }

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this@BaseActivity.initial(savedInstanceState)

    }

    private fun initial(bundel : Bundle?) {





        prefrence = SharedPrefrence.getInstance(this)





        if (permissionApproved()) {
            contactPermission.value = true

        } else {
            requestPermissions()
        }


        this@BaseActivity.layoutResId?.let { layoutId ->
            val dataBinder = DataBindingUtil.setContentView<T>(this@BaseActivity, layoutId)
            this@BaseActivity.onActivityCreated(dataBinder,bundel)
        }









    }

    fun AppCompatActivity.checkSelfPermissionCompat(permission: String) =
        ActivityCompat.checkSelfPermission(this, permission)

    fun AppCompatActivity.shouldShowRequestPermissionRationaleCompat(permission: String) =
        ActivityCompat.shouldShowRequestPermissionRationale(this, permission)

    fun AppCompatActivity.requestPermissionsCompat(
        permissionsArray: Array<String>,
        requestCode: Int
    ) {
        ActivityCompat.requestPermissions(this, permissionsArray, requestCode)

    }



    override fun onStart() {
        super.onStart()

    }

    override fun onResume() {
        super.onResume()


    }

    override fun onPause() {


        super.onPause()
    }

    override fun onStop() {


        super.onStop()
    }

    private fun permissionApproved(): Boolean {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_CONTACTS
        ) &&
                PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_PHONE_STATE
        ) &&
                PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_CALL_LOG)

    }

    private fun requestPermissions() {
        val provideRationale = permissionApproved()

        if (provideRationale) {



        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_CALL_LOG),
                PERMISSIONS_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        Log.d(TAG, "onRequestPermissionResult")

        when (requestCode) {
            PERMISSIONS_REQUEST_CODE -> when {
                grantResults.isEmpty() ->{

                }

                (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[2] == PackageManager.PERMISSION_GRANTED)
                ->{
                    contactPermission.value = true

                }


                else -> {


                    Snackbar.make(
                            findViewById(android.R.id.content),
                            R.string.permission_denied_explanation,
                            Snackbar.LENGTH_INDEFINITE
                    )
                            .setAction(R.string.settings) {
                                val intent = Intent()
                                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                val uri = Uri.fromParts(
                                        "package",
                                        BuildConfig.APPLICATION_ID,
                                        null
                                )
                                intent.data = uri
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                            }
                            .show()

                }
            }
        }
    }


}