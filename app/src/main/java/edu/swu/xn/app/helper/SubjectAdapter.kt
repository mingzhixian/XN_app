package edu.swu.xn.app.helper

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import edu.swu.xn.app.R
import edu.swu.xn.app.appData
import okhttp3.internal.notify

class SubjectAdapter(val layoutID:Int,val getSize:()->Int,val handle: ((holder: ViewHolder, position: Int) -> Unit)) :
  RecyclerView.Adapter<SubjectAdapter.ViewHolder>() {

  inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view =
      LayoutInflater.from(parent.context).inflate(layoutID, parent, false)
    return ViewHolder(view)
  }

  @SuppressLint("MissingInflatedId", "NotifyDataSetChanged")
  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    handle(holder, position)
  }

  override fun getItemCount(): Int =getSize()


}