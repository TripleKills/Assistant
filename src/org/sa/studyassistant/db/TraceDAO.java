package org.sa.studyassistant.db;

import android.content.Context;

public class TraceDAO extends DBObserver {
	private TraceDB db;
	
	public TraceDAO(Context context) {
		db = new TraceDB(context);
	}
}
