package com.example.turecallerdialog.utility

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.turecallerdialog.database.AppDatabase
import com.example.turecallerdialog.ui.activity.DialogCheckActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


class Callreceiver : PhonecallReceiver()
{
    override fun onIncomingCallStarted(ctx: Context?, number: String?, start: Date?) {
       Toast.makeText(ctx, "number-->$number", Toast.LENGTH_LONG).show()


        number?.let {
            callDialog(ctx,it)

        }

      //  Log.e("TAG", "--->onIncomingCallStarted")
    }

    override fun onOutgoingCallStarted(ctx: Context?, number: String?, start: Date?) {

       Toast.makeText(ctx, "number-->$number", Toast.LENGTH_LONG).show()
       // Log.e("TAG", "--->onOutgoingCallStarted")
        number?.let {
            callDialog(ctx,it)

        }
    }

    override fun onIncomingCallEnded(ctx: Context?, number: String?, start: Date?, end: Date?) {
        //Toast.makeText(ctx, "number-->$number", Toast.LENGTH_LONG).show()
        //Log.e("TAG", "--->onIncomingCallEnded")
        number?.let {
            callDialog(ctx,it)

        }

    }

    override fun onOutgoingCallEnded(ctx: Context?, number: String?, start: Date?, end: Date?) {

        //Toast.makeText(ctx, "number-->$number", Toast.LENGTH_LONG).show()
       // Log.e("TAG", "--->onOutgoingCallEnded")

        number?.let {
            callDialog(ctx,it)

        }
    }

    override fun onMissedCall(ctx: Context?, number: String?, start: Date?) {
        //Toast.makeText(ctx, "number-->$number", Toast.LENGTH_LONG).show()
       // Log.e("TAG", "--->onMissedCallonMissedCall")

        number?.let {
            callDialog(ctx,it)

        }

    }


    fun callDialog(ctx: Context?,number: String){

        ctx?.let { context ->

            val database = AppDatabase.getInstance(context).contactDao()


            GlobalScope.launch (Dispatchers.IO){

               val search = "%${number.removeCountryCode("+91")}%";


                Log.e("number","-->$search")
                database.getNickNamed(search).let {

                    Log.e("dabase-->","-->${it.size}")


                    if(it.size>0) {

                        delay(2000L)

                        val i = Intent(context, DialogCheckActivity::class.java)
                        i.putExtra("model",it.get(0))
                        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        // i.flags = FLAG_ACTIVITY_SINGLE_TOP
                        context.startActivity(i)
                    }






                }
            }






        }


    }


}