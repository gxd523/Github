package com.github.startactivitycallback

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import java.lang.reflect.Field

class StartActivityForResultFragment : Fragment() {
    companion object {
        const val TAG = "com.github.startactivitycallback.StartActivityForResultFragment"
        const val CALLBACK_HASHCODE = "callback_hashcode"
    }

    var startActivityCallback: (() -> Unit)? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (startActivityCallback == null && savedInstanceState != null) {
            startActivityCallback = savedInstanceState.getInt(CALLBACK_HASHCODE).let(::findActivityCallback)

            startActivityCallback?.let(::replaceOuterReference)
        }
    }

    /**
     * 寻找持有旧Activity的匿名内部类，找到Activity类型的Field，替换成员变量为新的Activity对象
     * 注意，嵌套匿名内部类时，callback持有的外部类的引用并不是Activity
     * 所以要从callback开始不断向外找
     */
    private fun replaceOuterReference(callback: () -> Unit) {
        var enclosingObject: Any = callback
        var enclosingClass: Class<*> = callback::class.java
        var activityField: Field? = null
        while (enclosingClass.enclosingClass != null) {
            var hasEnclosingClass = false
            for (field: Field in enclosingClass.declaredFields) {
                if (Activity::class.java.isAssignableFrom(field.type)) {// 寻找类型是Activity的子类的成员变量
                    activityField = field
                    break// 如果找到Activity类型成员变量，就跳出while循环，结束寻找
                }

                if (field.type == enclosingClass.enclosingClass) {// 如果成员变量的类型与外部类相同，说明有嵌套匿名内部类的情况
                    hasEnclosingClass = true
                    field.isAccessible = true
                    enclosingObject = field.get(enclosingObject)!!// 记录该成员变量，下一轮遍历该类型的成员变量，如果找到了Activity的field，需要该变量
                }
            }
            if (hasEnclosingClass) {
                enclosingClass = enclosingClass.enclosingClass!!
            } else {
                break
            }
        }

        activityField?.apply {
            if (activityField.type == enclosingClass.enclosingClass) {
                activityField.isAccessible = true
                activityField.set(enclosingObject, this@StartActivityForResultFragment.activity)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(CALLBACK_HASHCODE, startActivityCallback.hashCode())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        startActivityCallback?.invoke()
    }
}