package com.example.turecallerdialog.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import com.example.turecallerdialog.R
import com.example.turecallerdialog.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {


    lateinit var binding : ActivityMainBinding
    lateinit var anim : Animation


    override fun onActivityCreated(dataBinder: ActivityMainBinding, savedInstanceState: Bundle?) {


        binding = dataBinder

        anim = AnimationUtils.loadAnimation(
            applicationContext,
            android.R.anim.fade_in
        ); // Create the animation.


        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {




            }

            override fun onAnimationRepeat(animation: Animation) {}


        })
        binding.image.startAnimation(anim)

        contactPermission.observe(this, Observer {
            data ->

            if(data) {
                updateDisplay()

            }

        })


    }

    override fun onResume() {
        super.onResume()



    }




    fun updateDisplay(){

        val i  = Intent(this, HomeActivity::class.java)
        startActivity(i)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }
}