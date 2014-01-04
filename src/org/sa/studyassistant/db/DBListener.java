package org.sa.studyassistant.db;

import java.util.Map;

public abstract class DBListener {
	private int prioirty = 0;
	private static int seq = 0;
	public abstract boolean onAction(Map<String, Object> data);

	public int getPriority() {
		return prioirty;
	}

	public int setPriority(int prioirty) {
		return this.prioirty = prioirty;
	}

	public String getName() {
		return "default" + (seq++);
	}
}