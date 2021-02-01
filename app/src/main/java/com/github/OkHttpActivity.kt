package com.github

import android.app.Activity
import android.os.Bundle
import android.util.Log
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class OkHttpActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val requestBody = FormBody.Builder()
            .add("timestamp", "${System.currentTimeMillis()}")
            .build()

        val request = Request.Builder()
            .url("http://zmapi.dangbei.net/time.php")
            .post(requestBody)
            .build()

        val client = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()

        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                response.body()?.string()?.toLong()
                    ?.let { it * 1000 }
                    ?.let(::Date)
                    .let(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)::format)
                    .let {
                        Log.d("gxd", "TempActivity.onResponse-->$it")
                    }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.d("gxd", "TempActivity.onFailure-->", e)
            }
        })
    }
}