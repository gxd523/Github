package com.github.layout

import android.app.Activity
import android.os.Bundle
import android.view.Gravity

class DslActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        frameLayout {
            setBackgroundColor(0xFF666666.toInt())

            verticalLayout {
                button {
                    text = "Button 1"
                    setBackgroundColor(0xFFFF00FF.toInt())
                }.lparams {
                    weight = 1f
                }
                button {
                    text = "Button 2"
                    setBackgroundColor(0xFF00FFFF.toInt())
                }.lparams {
                    weight = 3f
                }
            }.lparams(WRAP_CONTENT, MATCH_PARENT) {
                gravity = Gravity.END
            }
        }
    }
}