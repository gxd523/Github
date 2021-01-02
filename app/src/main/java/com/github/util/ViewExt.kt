package com.github.util

import android.content.Context
import android.view.ViewManager
import android.widget.TextView
import cn.carbs.android.avatarimageview.library.AvatarImageView
import com.zzhoujay.richtext.RichText
import org.jetbrains.anko.AnkoViewDslMarker
import org.jetbrains.anko.custom.ankoView

// TODO: 1/2/21 重点：扩展属性
var TextView.markdownText: String
    set(value) {
        RichText.fromMarkdown(value).into(this)
    }
    get() = text.toString()


fun ViewManager.avatarImageView(): AvatarImageView = avatarImageView {}
inline fun ViewManager.avatarImageView(init: (@AnkoViewDslMarker AvatarImageView).() -> Unit): AvatarImageView {
    return ankoView({ ctx: Context -> AvatarImageView(ctx) }, theme = 0) { init() }
}