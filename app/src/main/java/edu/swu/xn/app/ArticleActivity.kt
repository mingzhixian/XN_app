package edu.swu.xn.app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

class ArticleActivity : AppCompatActivity() {
  @SuppressLint("SetJavaScriptEnabled")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_article)
    appData.publicTools.setStatusAndNavBar(this)
    intent.extras!!.getString("url")
      ?.let {
        val webView = findViewById<WebView>(R.id.article_web_view)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(it)
      }
  }

  fun onBackClick(view: View) {
    finish()
  }

  fun onSearchClick(view: View) {
    startActivity(Intent(this, SearchActivity::class.java))
  }
}