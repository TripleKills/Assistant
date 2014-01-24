package org.sa.studyassistant;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class StudyService extends Service {

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
