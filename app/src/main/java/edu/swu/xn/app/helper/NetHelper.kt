package edu.swu.xn.app.helper

import android.content.ContentValues
import android.util.Log
import edu.swu.xn.app.appData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject


class NetHelper {
  val okhttpClient = OkHttpClient()

  // 网络获取JSON
  fun get(url: String, handle: (json: JSONObject) -> Unit) {
    appData.mainScope.launch {
      withContext(Dispatchers.IO) {
        val request: Request = Request.Builder()
          .url(url)
          .build()
        okhttpClient.newCall(request).execute().use { response ->
          handle(JSONObject(response.body!!.string()))
        }
      }
    }
  }

  fun get(url: String, value: JSONObject, handle: (json: JSONObject) -> Unit) {
    appData.mainScope.launch {
      withContext(Dispatchers.IO) {
        val requestBody = value.toString()
          .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        val request = Request.Builder()
          .url(url)
          .post(requestBody)
          .build()
        okhttpClient.newCall(request).execute().use { response ->
          handle(JSONObject(response.body!!.string()))
        }
      }
    }
  }
}