package com.space.chatapp.common.extensions

import android.content.res.ColorStateList
import android.view.View


fun View.setBkgTintColor(colorRes: Int) {
    this.backgroundTintList = ColorStateList.valueOf(this.context.getColor(colorRes))
}