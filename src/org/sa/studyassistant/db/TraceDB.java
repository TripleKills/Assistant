package org.sa.studyassistant.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class TraceDB extends SQLiteOpenHelper {

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

}
