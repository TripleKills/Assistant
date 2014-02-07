package org.sa.studyassistant;

import org.sa.studyassistant.util.AlarmSender;
import org.sa.studyassistant.util.Logger;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class StudyService extends Service {
	
	private static final String tag = StudyService.class.getName();
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		String action = null;
		if (null != intent) {
			action = intent.getAction();
			if (action.equals(AlarmSender.getActiveAlarmAction())) {
				Logger.i(tag, "keep active");
				AlarmSender.sendActiveAlarm();
				return;
			}
			onAction(action, intent);
		}
		super.onStart(intent, startId);
	}

	@Override
	public void onDestroy() {
		startService(new Intent(this, getClass()));
		super.onDestroy();
	}
	
	private void onAction(String action, Intent intent) {
		
	}

}
