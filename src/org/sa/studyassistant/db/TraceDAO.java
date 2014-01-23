package org.sa.studyassistant.db;

import org.sa.studyassistant.model.Knowledge;
import org.sa.studyassistant.model.Trace;

import android.content.Context;
import android.database.Cursor;

public class TraceDAO extends DBObserver {
	private TraceDB db;

	public TraceDAO(Context context) {
		db = new TraceDB(context);
	}
	
	public int insertTrace(Trace trace) {
		return db.insertTrace(trace.action, trace.item_id, trace.phase, trace.time);
	}
	
	public Trace findByKnowledge(Knowledge knowledge) {
		return findByItemId(knowledge._id);
	}

	public Trace findByItemId(int item_id) {
		Cursor c = db.findByItemId(item_id);
		if (null == c)
			return null;
		Trace trace = null;
		try {
			if (c.moveToFirst())
				trace = parseTrace(c);
		} finally {
			c.close();
		}
		return trace;
	}

	private Trace parseTrace(Cursor c) {
		if (null == c)
			return null;
		Trace trace = new Trace();
		trace.action = c.getString(c.getColumnIndex(DBMetaData.TRACE_ACTION));
		trace.item_id = c.getInt(c.getColumnIndex(DBMetaData.TRACE_ITEM_ID));
		trace.phase = c.getInt(c.getColumnIndex(DBMetaData.TRACE_PHRASE));
		trace.time = c.getLong(c.getColumnIndex(DBMetaData.TRACE_TIME));
		return trace;
	}
}
