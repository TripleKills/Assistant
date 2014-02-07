package org.sa.studyassistant.util;

import java.util.Calendar;

import org.sa.studyassistant.AssistantApplication;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmSender {
	private static final String tag = AlarmSender.class.getName();

	public static void sendActiveAlarm() {
		Context context = AssistantApplication.APPLICATION;
		String action = getActiveAlarmAction();
		deployAlarm(context, action, (int) Constants.ACTIVE_ALARM_INTERVAL);
	}
	
	public static String getActiveAlarmAction() {
		return AssistantApplication.APPLICATION.getPackageName() + ".active_alarm";
	}
	
	public static void deployAlarm(Context context, String action,
			int millisecond) {
		Logger.i("AlarmSender", "deploy alarm " + action + " after "
				+ millisecond + "ms");
		Intent intent = new Intent(action);
		PendingIntent pending = PendingIntent.getBroadcast(context, 0, intent,
				0);
		AlarmManager alarmMgr = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MILLISECOND, millisecond);
		int type = AlarmManager.RTC_WAKEUP;
		alarmMgr.set(type, calendar.getTimeInMillis(), pending);
	}

	public static void pushNotification(Context context, int icon_resource,
			Class<?> cls, String content) {
		Logger.i(tag, "pushNotification");

		NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification n = new Notification(icon_resource, content,
				System.currentTimeMillis());
		n.flags = Notification.FLAG_AUTO_CANCEL;
		n.defaults |= Notification.DEFAULT_VIBRATE;

		Intent i = new Intent(context, cls);

		if (!(context instanceof Activity)) {
			Logger.i(tag, "set flag new task");
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		} else {
			Logger.i(tag, "set flag FLAG_ACTIVITY_SINGLE_TOP");
			i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		}

		PendingIntent contentIntent = PendingIntent.getActivity(context,
				icon_resource, i, PendingIntent.FLAG_UPDATE_CURRENT);
		n.setLatestEventInfo(context, content, content, contentIntent);
		nm.notify(icon_resource, n);
	}

}