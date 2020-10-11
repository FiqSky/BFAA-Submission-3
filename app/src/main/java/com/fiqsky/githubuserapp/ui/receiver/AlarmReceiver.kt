package com.fiqsky.githubuserapp.ui.receiver

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.fiqsky.githubuserapp.R
import com.fiqsky.githubuserapp.ui.activity.MainActivity
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID ="channel_1"
        const val CHANNEL_NAME = "Alarm_manager_channel"
    }

    override fun onReceive(context: Context, intent: Intent) {
        showAlarmNotification(context)
    }

    private fun showAlarmNotification(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val soundAlarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources,
                R.drawable.ic_baseline_notifications_24
            ))
            .setContentTitle(context.resources.getString(R.string.unknown))
            .setContentText(context.getString(R.string.daily_notif_message))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(soundAlarm)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

            builder.setChannelId(CHANNEL_ID)
            mNotificationManager.createNotificationChannel(channel)
        }

        val notification = builder.build()
        mNotificationManager.notify(NOTIFICATION_ID, notification)
    }

    fun setRepeatAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        val pendingIntent = PendingIntent.getBroadcast(context, 101, intent, 0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 101, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
    }

    /*val DAILY = "daily"
    val MESSAGE = "message"
//    val TYPE = "type"

    private val ID_DAILY = 101
    private val NOTIF = 10

    private val FORMAT_TIME = "HH:mm"

    lateinit var daily: String
    lateinit var message: String
//    lateinit var type: String
    private var idNotification = 0

    override fun onReceive(context: Context?, intent: Intent?) {
        daily = intent?.getStringExtra(DAILY) as String
        message = intent.getStringExtra(MESSAGE) as String
        idNotification = 0
//        type = intent?.getStringExtra(TYPE) as String
        showReminderNotification(context as Context, daily, idNotification)
        Toast.makeText(context,"$daily : $message", Toast.LENGTH_LONG).show()
    }

    fun showReminderNotification(context: Context, daily: String, notifyId: Int){
        val CHANNEL_ID = "channel_01"
        val CHANNEL_NAME = "reminder_notification"
        val CONTENT = "open_github_user"

        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        intent.putExtra("type", type)

        val pendingIntent = PendingIntent.getActivity(
            context,0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificationManagerCompact = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//        val builder = NotificationCompat.Builder
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle(daily)
            .setContentText(CONTENT)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(CHANNEL_ID)
            notificationManagerCompact.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManagerCompact.notify(notifyId, notification)
    }

    fun setRepeatingAlarm(context: Context, time: String, string: String){
        if (isDateInvalid(time,FORMAT_TIME)) return
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(MESSAGE, string)

        val timeArray = time.split(":".toRegex()).dropLastWhile {
            it.isEmpty()
        }.toTypedArray()

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY,intent,0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
        Log.d("track time array d1", timeArray[0])
        Log.d("track time array d2", timeArray[1])
        Toast.makeText(context,"reminder is enable",Toast.LENGTH_SHORT).show()
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

    fun cancelAlarm(context: Context, daily: String){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val requestCode = if (daily.equals(DAILY, ignoreCase = true)) ID_DAILY else ID_DAILY
        val pendingIntent = PendingIntent.getBroadcast(context,requestCode,intent,0)

        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context,"reminder is disable", Toast.LENGTH_SHORT).show()
    }*/
}
