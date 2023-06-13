package edu.swu.hd.app.activity

import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import edu.swu.hd.app.R
import edu.swu.hd.app.helper.AppData

class MainActivity : Activity(), ViewModelStoreOwner {
    companion object {
        var ViewModelStore: ViewModelStore? = null
    }

    // 数据
    val appData = ViewModelProvider(this).get(AppData::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun getViewModelStore(): ViewModelStore {
        if (ViewModelStore == null) {
            ViewModelStore = ViewModelStore()
        }
        return ViewModelStore!!
    }
}