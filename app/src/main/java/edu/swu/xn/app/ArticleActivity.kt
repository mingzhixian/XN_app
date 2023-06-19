package edu.swu.xn.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView

class ArticleActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_article)
    appData.publicTools.setStatusAndNavBar(this)
    intent.extras!!.getString("url")
      ?.let { findViewById<WebView>(R.id.article_web_view).loadUrl(it) }
  }

  fun onBackClick(view: View) {
    finish()
  }

  fun onSearchClick(view: View) {
    startActivity(Intent(this, SearchActivity::class.java))
  }
}