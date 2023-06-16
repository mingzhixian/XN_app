package edu.swu.xn.app.helper

import android.content.ContentValues
import android.util.Log
import android.widget.Toast
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
  private val okhttpClient = OkHttpClient()

  // 网络获取JSON
  fun get(url: String, handle: (json: JSONObject) -> Unit) {
    appData.mainScope.launch {
      withContext(Dispatchers.IO) {
        val request = Request.Builder().url(url)
        if (appData.hashID != "") {
          request.addHeader("token", appData.hashID)
        }
        try {
          okhttpClient.newCall(request.build()).execute().use { response ->
            handle(JSONObject(response.body!!.string()))
          }
        } catch (_: Exception) {
          withContext(Dispatchers.Main){
            Toast.makeText(appData.main, "网络错误", Toast.LENGTH_SHORT).show()
          }
        }
      }
    }
  }

  fun get(url: String, value: JSONObject, handle: (json: JSONObject) -> Unit) {
    appData.mainScope.launch {
      withContext(Dispatchers.IO) {
        val requestBody =
          value.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        val request = Request.Builder().url(url).post(requestBody)
        if (appData.hashID != "") {
          request.addHeader("token", appData.hashID)
        }
        try {
          okhttpClient.newCall(request.build()).execute().use { response ->
            handle(JSONObject(response.body!!.string()))
          }
        } catch (_: Exception) {
          Toast.makeText(appData.main, "网络错误", Toast.LENGTH_SHORT).show()
        }
      }
    }
  }
}