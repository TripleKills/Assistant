package org.sa.studyassistant.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class DBObserver implements DBListener {

	private List<DBListener> observers = new ArrayList<DBListener>();

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


	public abstract class PriorityListener implements DBListener {
		private int prioirty = 0;

		public abstract boolean onAction(Map<String, Object> data);

		@Override
		public int getPriority() {
			return prioirty;
		}

		@Override
		public int setPriority(int prioirty) {
			return this.prioirty = prioirty;
		}

		@Override
		public String getName() {
			return "default";
		}
	}


	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public int setPriority(int prioirty) {
		return 0;
	}

	@Override
	public String getName() {
		return "dbobserver";
	}

}
