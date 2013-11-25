package org.sa.studyassistant.db;

import android.content.Context;

public class AssistantDAO extends DBObserver {
	private static AssistantDAO _inst;
	private TraceDAO trace;
	private SaveDAO save;

	private AssistantDAO() {
	}

	public static AssistantDAO getInstance() {
		if (null == _inst)
			_inst = new AssistantDAO();
		return _inst;
	}

	public void init(Context context) {
		if (null != save)
			return;
		save = new SaveDAO(context);
		save.regist(this);
		trace = new TraceDAO(context);
		trace.regist(this);
	}

}
