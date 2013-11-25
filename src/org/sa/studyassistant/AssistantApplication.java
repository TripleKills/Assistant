package org.sa.studyassistant;

import org.sa.studyassistant.db.AssistantDAO;

import android.app.Application;

public class AssistantApplication extends Application {
	@Override
	public void onCreate() {
		AssistantDAO.getInstance().init(this);
		super.onCreate();
	}
}
