package com.github

import android.app.Activity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.github.util.copyTo
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class OkHttpActivity : Activity() {
    private val urlArray = arrayOf(
        "http://www.baidu.com",
        "http://zmapi.dangbei.net/time.php",
        "http://115.223.11.173/app.znds.com/web/dangbeimarket_4.2.8_259_yunji.apk",
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val requestBody = FormBody.Builder()
            .add("timestamp", "${System.currentTimeMillis()}")
            .build()

        val request = Request.Builder()
            .url(urlArray[2])
//            .post(requestBody)
            .get()
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
                response.body()?.let { responseBody ->
                    downloadApk(responseBody)
//                    printDate(responseBody)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.d("gxd", "TempActivity.onFailure-->", e)
            }
        })
    }

    private fun printDate(responseBody: ResponseBody) {
        responseBody.string().toLong()
            .let { it * 1000 }
            .let(::Date)
            .let(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)::format)
            .let {
                Log.d("gxd", "TempActivity.onResponse-->$it")
            }
    }

    private fun downloadApk(responseBody: ResponseBody) {
        File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "a.apk")
            .outputStream().use { output ->
                responseBody.byteStream().use { input ->
                    input.copyTo(output) { bytesCopied ->
                        val progress = bytesCopied * 100 / responseBody.contentLength()
                        Log.d("gxd", "下载进度...$progress")
                    }
                }
            }
    }
}