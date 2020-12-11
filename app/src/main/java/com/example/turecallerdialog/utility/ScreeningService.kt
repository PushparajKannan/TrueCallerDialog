package com.example.turecallerdialog.utility

import android.os.Build
import android.telecom.Call
import android.telecom.CallScreeningService
import android.util.Log
import androidx.annotation.RequiresApi

@RequiresApi(api = Build.VERSION_CODES.N)
class ScreeningService : CallScreeningService() {

    override fun onScreenCall(details: Call.Details) {

        Log.e("call-->", "-->${details}")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (details.callDirection === Call.Details.DIRECTION_INCOMING) {
                val response = CallResponse.Builder()
                response.setDisallowCall(false)
                response.setRejectCall(false)
                response.setSilenceCall(false)
                response.setSkipCallLog(false)
                response.setSkipNotification(false)
                details.handle //This is the calling number
                respondToCall(details, response.build())
            }
        }



    }

}