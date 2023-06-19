package edu.swu.xn.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView

class ArticleActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_article)
    intent.extras!!.getString("url")
      ?.let { findViewById<WebView>(R.id.article_web_view).loadUrl(it) }
  }
}