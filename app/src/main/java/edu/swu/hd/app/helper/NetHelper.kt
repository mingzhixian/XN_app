package edu.swu.hd.app.helper

import android.content.ContentValues
import edu.swu.hd.app.activity.appData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
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

    fun get(url: String, value: ContentValues, handle: (json: JSONObject) -> Unit) {
        appData.mainScope.launch {
            withContext(Dispatchers.IO) {
                val formBody = FormBody.Builder()
                for (item in value.valueSet()) {
                    formBody.add(item.key, item.value.toString())
                }
                val request = Request.Builder()
                    .url(url)
                    .post(formBody.build())
                    .build()
                okhttpClient.newCall(request).execute().use { response ->
                    handle(JSONObject(response.body!!.string()))
                }
            }
        }
    }
}