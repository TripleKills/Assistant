package org.sa.studyassistant;

import org.sa.studyassistant.util.AlarmSender;
import org.sa.studyassistant.util.Logger;
import org.sa.studyassistant.util.TimeUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StudyReceiver extends BroadcastReceiver {
	private static final String tag = StudyReceiver.class.getName();

	/**
     * #packageName + ".alarm"
     * <intent-filter>
     *    <action android:name="org.sa.studyassistant.alarm"/>
     * </intent-filter>
     * <intent-filter>
     *    <action android:name="org.sa.studyassistant.active_alarm" />
     * </intent-filter>
     * <intent-filter>
     *    <action android:name="android.intent.action.BOOT_COMPLETED"></action>
     * </intent-filter>
     * <intent-filter>   
     *    <action android:name="android.net.conn.CONNECTIVITY_CHANGE"/>   
     * </intent-filter>
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		Intent service_intent = new Intent(context, StudyService.class);
		String action = null != intent ? intent.getAction() : null;
		if (null != action) {
			Logger.i(tag, "is action null? " + (null == action));
			Logger.i(tag, "on receive action " + action
					+ "\n  at time:" + TimeUtil.transTime(System.currentTimeMillis()));
			service_intent.setAction(action);
		}
		context.startService(service_intent);
	}

}
