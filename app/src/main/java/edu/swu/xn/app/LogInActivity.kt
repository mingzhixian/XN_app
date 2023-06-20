package edu.swu.xn.app

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import org.json.JSONObject
import java.security.MessageDigest

class LogInActivity : AppCompatActivity() {

  // 页面状态，0位登录页面，1为注册页面
  private var status = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    appData.publicTools.setStatusAndNavBar(this)
    setContentView(R.layout.activity_log_in)
    setButtonLogInListener()
    setButtonRegisterListener()
  }

  // 禁止返回上一级
  @Deprecated("Deprecated in Java")
  override fun onBackPressed() {
    Toast.makeText(appData.main, "请先登录", Toast.LENGTH_SHORT).show()
  }

  // 设置登录按键监听
  private fun setButtonLogInListener() {
    findViewById<Button>(R.id.log_in_button_log_in).setOnClickListener {
      if (!checkAgreement()) return@setOnClickListener
      val name = findViewById<EditText>(R.id.log_in_name).text.toString()
      val password = findViewById<EditText>(R.id.log_in_password).text.toString()
      val postValue = JSONObject().apply {
        put("userName", "user-$name")
        put("password", MD5Util.encode(password))
      }
      appData.netHelper.get("${getString(R.string.admin_url)}/api/security-auth/login", postValue) {
        // 密码账户错误
        if (it == null) return@get
        // 登录成功向数据库保存哈希值
        val data = it.getJSONObject("data")
        val userVO = data.getJSONObject("userVO")
        saveID(data.getString("token"), userVO.getInt("id"), userVO.getString("userName"))
        setResult(1)
        finish()
      }
    }
  }

  // 设置注册按键监听
  private fun setButtonRegisterListener() {
    findViewById<Button>(R.id.log_in_button_register).setOnClickListener {
      if (!checkAgreement()) return@setOnClickListener
      val phoneView = findViewById<EditText>(R.id.log_in_phone)
      // 切换为注册页面
      if (status == 0) {
        status = 1
        findViewById<TextView>(R.id.log_in_title).visibility = View.GONE
        phoneView.visibility = View.VISIBLE
        val cardView = findViewById<View>(R.id.log_in_card)
        val cardViewLayout = cardView.layoutParams as ConstraintLayout.LayoutParams
        cardViewLayout.bottomMargin = appData.publicTools.dp2px(-30f).toInt()
        cardView.layoutParams = cardViewLayout
        findViewById<View>(R.id.log_in_button_log_in).visibility = View.GONE
        findViewById<View>(R.id.log_in_button_space).visibility = View.GONE
        return@setOnClickListener
      }
      val phone = phoneView.text.toString()
      val name = findViewById<EditText>(R.id.log_in_name).text.toString()
      val password = findViewById<EditText>(R.id.log_in_password).text.toString()
      // 检查密码强度
      if (!checkPassword(password)) {
        val checkError = findViewById<TextView>(R.id.log_in_password_check_error)
        checkError.visibility = View.VISIBLE
        checkError.text = "*需大于8位小于16位，并至少包含数字、小写字母、大写字母、特殊符号中的三项"
        return@setOnClickListener
      }
      val postValue = JSONObject().apply {
        put("userName", name)
        put("phonenumber", phone)
        put("password", MD5Util.encode(password))
      }
      appData.netHelper.get(
        "${getString(R.string.admin_url)}/api/service-user/user/addUser",
        postValue
      ) {
        if (it == null) return@get
        val data = it.getJSONObject("data")
        saveID(data.getString("token"), data.getInt("id"), data.getString("userName"))
        setResult(1)
        finish()
      }
    }
  }

  private fun saveID(hashId: String, id: Int, userName: String) {
    appData.hashId = hashId
    appData.userId = id
    appData.userName = userName
    appData.settings.edit().apply {
      putString("hashId", hashId)
      putInt("userId", id)
      putString("userName", userName)
      apply()
    }
  }

  // 检测密码合理性
  private fun checkPassword(password: String): Boolean {
    if (password.length < 8 || password.length > 16) return false
    var i = 0
    if (password.matches(Regex(".*\\d+.*"))) i++
    if (password.matches(Regex(".*[A-Z]+.*"))) i++
    if (password.matches(Regex(".*[a-z]+.*"))) i++
    if (password.matches(Regex(".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*"))) i++
    return i >= 3
  }

  // 检查协议是否同意
  private fun checkAgreement(): Boolean {
    if (!findViewById<CheckBox>(R.id.log_in_pri_agreement).isChecked || !findViewById<CheckBox>(R.id.log_in_user_agreement).isChecked) {
      Toast.makeText(appData.main, "请先同意隐私与用户协议", Toast.LENGTH_SHORT).show()
      return false
    }
    return true
  }


  // MD5加密
  private object MD5Util {
    private const val SALT = "xinandaxue"
    fun encode(p: String): String {
      var password = p
      password += SALT
      var md5: MessageDigest? = null
      md5 = try {
        MessageDigest.getInstance("MD5")
      } catch (e: Exception) {
        throw RuntimeException(e)
      }
      val charArray = password.toCharArray()
      val byteArray = ByteArray(charArray.size)
      for (i in charArray.indices) byteArray[i] = charArray[i].code.toByte()
      val md5Bytes = md5!!.digest(byteArray)
      val hexValue = StringBuffer()
      for (i in md5Bytes.indices) {
        val `val` = md5Bytes[i].toInt() and 0xff
        if (`val` < 16) {
          hexValue.append("0")
        }
        hexValue.append(Integer.toHexString(`val`))
      }
      return hexValue.toString()
    }
  }
}