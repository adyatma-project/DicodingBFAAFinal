package id.adyatma.dicodingbfaafinal;
/*This App Created By ADYATMA LAKATJINDA To Dicoding Indonesia*/

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.util.Calendar;

import id.adyatma.dicodingbfaafinal.view.MainActivity;

public class ReminderReceiver extends BroadcastReceiver {

    private static final int REMINDER_ID = 10;
    private static final String CHANNEL_ID = "Channel_10";
    private static final String CHANNEL_NAME = "DailyAlarmManagerGithubUser";

    @Override
    public void onReceive(Context context, Intent intent) {
        String mTitleReminder = context.getString(R.string.app_name);
        String mMessageReminder = context.getString(R.string.pesan_reminder);
        showReminderNotification(context, mTitleReminder, mMessageReminder);
    }

    private void showReminderNotification(Context context, String title, String message) {

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService
                (Context.NOTIFICATION_SERVICE);

        Intent mHomeIntent = new Intent(context, MainActivity.class);
        PendingIntent mPendingIntent = PendingIntent.getActivity(context, REMINDER_ID, mHomeIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri mAlarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(message);
        mBuilder.setContentIntent(mPendingIntent);
        mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        mBuilder.setAutoCancel(true);
        mBuilder.setColor(ContextCompat.getColor(context, android.R.color.transparent));
        mBuilder.setVibrate(new long[]{2000, 2000, 2000, 2000, 2000, 2000});
        mBuilder.setSound(mAlarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{2000, 2000, 2000, 2000, 2000});

            mBuilder.setChannelId(CHANNEL_ID);
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(mChannel);
        }

        Notification notification = mBuilder.build();
        mNotificationManager.notify(REMINDER_ID, notification);

    }

    public void setReminder(Context context) {

        AlarmManager mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent mIntent = new Intent(context, ReminderReceiver.class);
        PendingIntent mPendingIntent = PendingIntent.getBroadcast(context, REMINDER_ID,
                mIntent, 0);

        Calendar mTime = Calendar.getInstance();
        mTime.set(Calendar.HOUR_OF_DAY, 9);
        mTime.set(Calendar.MINUTE, 0);
        mTime.set(Calendar.SECOND, 0);

        if (mAlarmManager != null) {
            mAlarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, mTime.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, mPendingIntent);
        }

        Toast.makeText(context, context.getResources().getString(R.string.msg_reminder_activated),
                Toast.LENGTH_SHORT).show();
    }

    public void cancelReminder(Context context) {
        AlarmManager mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent mIntent = new Intent(context, ReminderReceiver.class);
        PendingIntent mPendingIntent = PendingIntent.getBroadcast(context, REMINDER_ID,
                mIntent, 0);
        if (mAlarmManager != null) {
            mAlarmManager.cancel(mPendingIntent);
        }
        Toast.makeText(context, context.getResources().getString(R.string.msg_reminder_disabled), Toast.LENGTH_SHORT).show();
    }

}
