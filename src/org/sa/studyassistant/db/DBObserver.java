package org.sa.studyassistant.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.sa.studyassistant.util.Logger;

import android.text.TextUtils;

public class DBObserver extends DBListener {

	private static final String tag = DBObserver.class.getName();
	private List<DBListener> observers = new ArrayList<DBListener>();
	public static final String KEY_ACTION = "action";
	public static final String KEY_CATEGORY = "category";
	public static final String ACTION_INSERT_CATEGORY = "action_insert_category";
	public static final String ACTION_DELETE_CATEGORY = "action_delete_category";
	public static final String ACTION_UPDATE_CATEGORY = "action_update_category";

	public boolean registMax(DBListener lsnr) {
		if (null == lsnr)
			return false;
		lsnr.setPriority(getPriority(1) + 1);
		return regist(lsnr);
	}

	public boolean regist(DBListener lsnr) {
		if (null == lsnr)
			return false;
		if (observers.contains(lsnr))
			return false;
		if (!observers.add(lsnr))
			return false;
		Collections.sort(observers, com);
		return true;
	}

	public boolean unregist(DBListener lsnr) {
		return observers.remove(lsnr);
	}

	public boolean unregist(String name) {
		if (TextUtils.isEmpty(name))
			return false;
		DBListener target = null;
		for (DBListener listener : observers) {
			if (name.equals(listener.getName())) {
				target = listener;
			}
		}
		Logger.i(tag, "unregist " + name);
		return observers.remove(target);
	}

	public boolean onAction(Map<String, Object> data) {
		for (DBListener l : observers) {
			if (l.onAction(data))
				return true;
		}
		return true;
	}

	private int getPriority(int type) {
		if (observers.isEmpty())
			return 0;
		int min = observers.get(0).getPriority();
		int max = observers.get(0).getPriority();
		for (DBListener l : observers) {
			int p = l.getPriority();
			if (p < min)
				min = p;
			if (p > max)
				max = p;
		}
		return type > 0 ? max : min;
	}

	private Comparator<DBListener> com = new Comparator<DBListener>() {

		@Override
		public int compare(DBListener lhs, DBListener rhs) {
			return lhs.getPriority() - rhs.getPriority();
		}
	};

}
