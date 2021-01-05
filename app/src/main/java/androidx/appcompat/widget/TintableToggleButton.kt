package androidx.appcompat.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff.Mode
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.core.view.TintableBackgroundView

class TintableToggleButton
@JvmOverloads // TODO: 1/5/21 重点：JvmOverloads：生成多个构造函数
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    AppCompatToggleButton(context, attrs, defStyleAttr), TintableBackgroundView {

    // TODO: 1/5/21 重点：因为在父类(View)的构造函数里调用了setBackground，此时还没有初始化子类的成员变量
    private var backgroundTintHelper: AppCompatBackgroundHelper? = null

    init {
        backgroundTintHelper = AppCompatBackgroundHelper(this)
        backgroundTintHelper?.loadFromAttributes(attrs, defStyleAttr)
    }

    override fun setSupportBackgroundTintList(tint: ColorStateList?) {
        backgroundTintHelper?.supportBackgroundTintList = tint
    }

    override fun getSupportBackgroundTintMode() = backgroundTintHelper?.supportBackgroundTintMode

    override fun setSupportBackgroundTintMode(tintMode: Mode?) {
        backgroundTintHelper?.supportBackgroundTintMode = tintMode
    }

    override fun getSupportBackgroundTintList() = backgroundTintHelper?.supportBackgroundTintList

    override fun setBackgroundResource(@DrawableRes resId: Int) {
        super.setBackgroundResource(resId)
        backgroundTintHelper?.onSetBackgroundResource(resId)
    }

    override fun setBackground(background: Drawable?) {
        super.setBackground(background)
        backgroundTintHelper?.onSetBackgroundDrawable(background)
    }
}