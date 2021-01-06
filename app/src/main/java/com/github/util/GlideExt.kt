package com.github.util

import cn.carbs.android.avatarimageview.library.AppCompatAvatarImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.*

// TODO: 1/2/21 重点：扩展函数+高阶函数
fun AppCompatAvatarImageView.loadWithGlide(
    url: String,
    textPlaceHolder: Char,
    requestOptions: RequestOptions = RequestOptions(),
) {
    textPlaceHolder.toString().let {
        setTextAndColorSeed(it.toUpperCase(Locale.getDefault()), it.hashCode().toString())
    }

    Glide.with(this.context)
        .load(url)
        .apply(requestOptions)
        .into(this)
}