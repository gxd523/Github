package com.github

import android.app.Activity
import android.os.Bundle
import android.util.Log
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class OkHttpActivity : Activity() {
    val urlArray = arrayOf(
        "http://zmapi.dangbei.net/time.php",
        "http://down.znds.com/getdownurl/?s=L3dlYi9kYW5nYmVpbWFya2V0XzQuMi44XzI1OV95dW5qaS5hcGs=",
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val requestBody = FormBody.Builder()
            .add("timestamp", "${System.currentTimeMillis()}")
            .build()

        val request = Request.Builder()
            .url(urlArray[0])
            .post(requestBody)
            .build()

        val client = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .cache(Cache(File(externalCacheDir, "okHttpCache"), 50 * 1024 * 1024))// CacheInterceptor
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