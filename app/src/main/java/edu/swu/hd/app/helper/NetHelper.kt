package edu.swu.hd.app.helper

import android.content.ContentValues
import okhttp3.OkHttpClient

class NetHelper {
    val okhttpClient = OkHttpClient()

    // 网络获取JSON
    fun get(url: String, handle: () -> Unit) {

    }

    fun get(url: String, value: ContentValues, handle: () -> Unit) {

    }
}