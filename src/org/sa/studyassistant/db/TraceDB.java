package org.sa.studyassistant.db;

import org.sa.studyassistant.util.DBUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class TraceDB extends SQLiteOpenHelper {

	public static final String ACTION_MAIN = "main";
	
	public TraceDB(Context context) {
		this(context, DBMetaData.TRACE_DB_NAME, null, DBMetaData.TRACE_DB_VERSION);
	}
	
	public TraceDB(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DBMetaData.TRACE_DB_NAME, factory,
				DBMetaData.TRACE_DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// trace table
		db.execSQL("create table " + DBMetaData.TRACE_TABLE_NAME
				+ " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ DBMetaData.TRACE_ACTION + " TEXT, "
				+ DBMetaData.TRACE_ITEM_ID + " INTEGER, "
				+ DBMetaData.TRACE_TIME + " LONG, "
				+ DBMetaData.TRACE_PHRASE + " INTEGER);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
	public Cursor findByItemId(int item_id) {
		return DBUtil.findBy(this, DBMetaData.TRACE_DB_NAME, 
				DBMetaData.TRACE_ITEM_ID, String.valueOf(item_id));
	}
	
	public int insertTrace(String action, int item_id, int phase, long time) {
		ContentValues cv = new ContentValues();
		cv.put(DBMetaData.TRACE_ACTION, action);
		cv.put(DBMetaData.TRACE_ITEM_ID, item_id);
		cv.put(DBMetaData.TRACE_TIME, time);
		cv.put(DBMetaData.TRACE_PHRASE, phase);
		return (int) getWritableDatabase().insert(
				DBMetaData.TRACE_TABLE_NAME, null, cv);
	}
	
	public int deleeteTrace(int item_id) {
		return DBUtil.deleteBy(this, DBMetaData.TRACE_TABLE_NAME, DBMetaData.TRACE_ITEM_ID, String.valueOf(item_id));
	}

}
