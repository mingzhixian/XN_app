package edu.swu.xn.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class OrderActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    appData.publicTools.setFullScreen(this)
    setContentView(R.layout.activity_order)
  }
}