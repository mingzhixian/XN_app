package edu.swu.xn.app.entity

/**
 *  就诊人
 */
data class Visitor(
  val name : String,
  val id : Int,
  val sex : String,
  val phoneNumber : String,
  val medicalHistory : String,
  val cardID : String,
  val age : Int
)
