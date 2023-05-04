package com.space.chatapp.common.extensions

import android.content.res.ColorStateList
import android.widget.TextView

fun TextView.setBkgTintColor(colorRes: Int) {
    this.backgroundTintList = ColorStateList.valueOf(this.context.getColor(colorRes))
}