package org.sa.studyassistant.db;

import org.sa.studyassistant.util.Logger;

import android.content.Context;

public class SaveDAO extends DBObserver {
	private SaveDB db;
	private static final String tag = SaveDAO.class.getName();

	public SaveDAO(Context context) {
		db = new SaveDB(context);
		addDefaultCategory();
	}

	/**
	 * add default category, all notes without category belong to this category
	 * 
	 * @return
	 */
	private long addDefaultCategory() {
		long result = db.addCategory("default");
		String msg = "add default category, result is " + result;
		Logger.i(tag, msg);
		return result;
	}
}
