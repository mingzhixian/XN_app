package edu.swu.xn.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class ServiceActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_service)
    appData.publicTools.setStatusAndNavBar(this)
  }

  fun onBackClick(view: View) {
    finish()
  }

  fun onSearchClick(view: View) {
    startActivity(Intent(this, SearchActivity::class.java))
  }

  fun onTeleItemClick(view: View) {
    val tele = (view as TextView).tag
    val intentPhone = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$tele"))
    startActivity(intentPhone)
  }

  fun onOnLineItemClick(view: View) {
    val intent = Intent(this, ArticleActivity::class.java)
    var url = ""
    when ((view as TextView).tag) {
      "1" -> {
        url = "https://m.chunyuyisheng.com/m/doctor/clinic_web_cc088a40cbcebec1/"
      }

      "2" -> {
        url = "https://m.chunyuyisheng.com/m/doctor/clinic_web_cc088a40cbcebec1/"
      }
    }
    intent.putExtra("url", url)
    startActivity(intent)
  }
}