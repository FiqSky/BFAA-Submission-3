package com.fiqsky.githubuserapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    val DAILY = "daily"
    val MESSAGE = "message"
//    val TYPE = "type"

    private val ID_DAILY = 101
    private val NOTIF = 0

    private val FORMAT_TIME = "HH:mm"

    lateinit var daily: String
    lateinit var message: String
//    lateinit var type: String
    private var idNotification = 0

    override fun onReceive(context: Context?, intent: Intent?) {
        daily = intent?.getStringExtra(DAILY) as String
        message = intent?.getStringExtra(MESSAGE) as String
        idNotification = 0
//        type = intent?.getStringExtra(TYPE) as String

    }

    fun setRepeatingAlarm(context: Context,time: String, message: String){

    }

    fun isDateInvalid(date: String, format: String): Boolean{
        return try {
            val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
            simpleDateFormat.isLenient = false
            simpleDateFormat.parse(date)
            false
        } catch (e: ParseException){
            true
        }
    }

    fun cancelAlarm(context: Context){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context,ID_DAILY,intent,0)

        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context,"reminder is disable", Toast.LENGTH_SHORT).show()
    }
}
