package com.example.turecallerdialog.utility

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import java.util.*


abstract class PhonecallReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent) {




        val telephony = context!!.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        telephony.listen(object : PhoneStateListener() {
            override fun onCallStateChanged(state: Int, incomingNumber: String) {
                super.onCallStateChanged(state, incomingNumber)

                onCallStateChanged(context, state, incomingNumber)

                println("incomingNumber : $incomingNumber")
            }
        }, PhoneStateListener.LISTEN_CALL_STATE)


    }

    abstract fun onIncomingCallStarted(ctx: Context?, number: String?, start: Date?)
    abstract fun onOutgoingCallStarted(ctx: Context?, number: String?, start: Date?)
    abstract fun onIncomingCallEnded(ctx: Context?, number: String?, start: Date?, end: Date?)
    abstract fun onOutgoingCallEnded(ctx: Context?, number: String?, start: Date?, end: Date?)
    abstract fun onMissedCall(ctx: Context?, number: String?, start: Date?)


    fun onCallStateChanged(context: Context?, state: Int, number: String?) {
        if (lastState == state) {
            return
        }

        when (state) {
            TelephonyManager.CALL_STATE_RINGING -> {
                isIncoming = true
                callStartTime = Date()
                savedNumber = number
                onIncomingCallStarted(context, number, callStartTime)
            }
            TelephonyManager.CALL_STATE_OFFHOOK ->
                if (lastState != TelephonyManager.CALL_STATE_RINGING) {
                    isIncoming = false
                    callStartTime = Date()
                    onOutgoingCallStarted(context, savedNumber, callStartTime)
                }
            TelephonyManager.CALL_STATE_IDLE ->
                if (lastState == TelephonyManager.CALL_STATE_RINGING) {
                    onMissedCall(context, savedNumber, callStartTime)
                } else if (isIncoming) {
                    onIncomingCallEnded(context, savedNumber, callStartTime, Date())
                } else {
                    onOutgoingCallEnded(context, savedNumber, callStartTime, Date())
                }
        }
        lastState = state
    }

    companion object {
        private var lastState = TelephonyManager.CALL_STATE_IDLE
        private var callStartTime: Date? = null
        private var isIncoming = false
        private var savedNumber
                : String? = null
    }
}