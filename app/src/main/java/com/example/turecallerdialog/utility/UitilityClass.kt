package com.example.turecallerdialog.utility

import com.google.android.material.appbar.CollapsingToolbarLayout

class UitilityClass {




}

//Set CollapsingToolbarLayout title
fun CollapsingToolbarLayout.setCollapsingToolbarLayoutTitle(
    title: String,
    isTitleEnabled: Boolean
) {
    this.title = title
    this.isTitleEnabled = isTitleEnabled

}


fun String.removeCountryCode(countryCode : String) : String{
    return  when {
        this.contains(countryCode) -> this.replace(countryCode,"")
        else -> {
            this
        }
    }

}