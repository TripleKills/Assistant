package org.sa.studyassistant;

import org.sa.studyassistant.db.AssistantDAO;

import android.app.Application;

public class AssistantApplication extends Application {
	public static AssistantApplication APPLICATION;
	@Override
	public void onCreate() {
		AssistantDAO.getInstance().init(this);
		super.onCreate();
		APPLICATION = this;
	}
}
