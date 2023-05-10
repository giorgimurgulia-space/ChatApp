package com.space.chatapp.common.extensions

import android.content.res.ColorStateList
import android.view.View
import com.space.chatapp.domain.model.message.MessageModel
import com.space.chatapp.ui.chat.model.MessageUIModel


fun View.setBkgTintColor(colorRes: Int) {
    this.backgroundTintList = ColorStateList.valueOf(this.context.getColor(colorRes))
}

