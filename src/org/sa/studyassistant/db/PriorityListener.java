package org.sa.studyassistant.db;

import java.util.Map;

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