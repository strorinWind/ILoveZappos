package com.strorin.zappos.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.strorin.zappos.R
import com.strorin.zappos.ui.main.MainActivity


class NotificationResolver(
    private val context: Context
) {
    private val CHANNEL_ID = "price_notification"
    private val PRICE_ID = 1

    fun createPriceNotification(price: Float) {
        createNotificationChannel()

        val builder = getNotificationBuilder(price)

        with(NotificationManagerCompat.from(context)) {
            cancel(PRICE_ID)
            // notificationId is a unique int for each notification that you must define
            notify(1, builder.build())
        }
    }

    private fun getNotificationBuilder(price: Float): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_settings_24dp)
            .setContentTitle("Price decreased!!")
            .setContentText("Current price is $price")
            .setPriority(if (Build.VERSION.SDK_INT >= 26) NotificationManager.IMPORTANCE_HIGH else NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_SOUND or NotificationCompat.DEFAULT_VIBRATE)
            .setContentIntent(createPendingIntent())
    }

    private fun createPendingIntent() : PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        return PendingIntent.getActivity(context, 0, intent, 0)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.str_price_channel_name)
            val descriptionText = context.getString(R.string.str_price_channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}