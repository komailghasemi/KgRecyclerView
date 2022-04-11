package com.app.libarary

import android.content.res.Resources
import android.view.View

fun View.show(){
    visibility = View.VISIBLE
}

fun View.hide(){
    visibility = View.GONE
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

val Int.sp: Int
    get() = (this.dp / Resources.getSystem().displayMetrics.density + 0.5f).toInt()