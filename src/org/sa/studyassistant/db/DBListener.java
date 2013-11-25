package org.sa.studyassistant.db;

import java.util.Map;

public interface DBListener {
	public boolean onAction(Map<String, Object> data);

	public int getPriority();

	public int setPriority(int prioirty);

	public String getName();
}