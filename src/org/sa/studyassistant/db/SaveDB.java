package org.sa.studyassistant.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

public class SaveDB extends SQLiteOpenHelper {

	public SaveDB(Context context) {
		this(context, DBMetaData.SAVE_DB_NAME, null, DBMetaData.SAVE_DB_VERSION);
	}

	public SaveDB(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DBMetaData.SAVE_DB_NAME, factory,
				DBMetaData.SAVE_DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// category table
		db.execSQL("create table " + DBMetaData.CATEGORY_TABLE_NAME
				+ " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ DBMetaData.CATEGORY_BELONG_TO + " INTEGER, "
				+ DBMetaData.CATEGORY_NAME + " TEXT);");

		// knowledge table
		db.execSQL("create table " + DBMetaData.KNOWLEDGE_TABLE_NAME
				+ " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ DBMetaData.KNOWLEDGE_CATEGORY_ID + " INTEGER, "
				+ DBMetaData.KNOWLEDGE_CREATE_TIME + " LONG, "
				+ DBMetaData.KNOWLEDGE_ANSWER + " TEXT, "
				+ DBMetaData.KNOWLEDGE_QUESTION + " TEXT);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	// apis for knowledge
	public long insertKnowledge(String answer, int category_id, String question,
			long create_time) {
		if ((null == question && null == answer) || category_id < 0)
			return -1;
		ContentValues values = new ContentValues();
		values.put(DBMetaData.KNOWLEDGE_ANSWER, answer);
		values.put(DBMetaData.KNOWLEDGE_CATEGORY_ID, category_id);
		values.put(DBMetaData.KNOWLEDGE_QUESTION, question);
		values.put(DBMetaData.KNOWLEDGE_CREATE_TIME, create_time);
		return getWritableDatabase().insert(DBMetaData.KNOWLEDGE_TABLE_NAME,
				null, values);
	}

	public Cursor findKnowledgesByCategory(int category_id) {
		return findBy(DBMetaData.KNOWLEDGE_TABLE_NAME,
				DBMetaData.KNOWLEDGE_CATEGORY_ID, String.valueOf(category_id));
	}

	// apis for category
	public int insertCategory(String name, int belong_to) {
		if (TextUtils.isEmpty(name))
			return -1;
		ContentValues values = new ContentValues();
		values.put(DBMetaData.CATEGORY_NAME, name);
		values.put(DBMetaData.CATEGORY_BELONG_TO, belong_to);
		return (int) getWritableDatabase().insert(
				DBMetaData.CATEGORY_TABLE_NAME, null, values);
	}

	public Cursor findAllCategory() {
		return findAll(DBMetaData.CATEGORY_TABLE_NAME);
	}

	// apis for all tables
	private Cursor findAll(String table_name) {
		String sql = "select * from " + table_name;
		Cursor c = getReadableDatabase().rawQuery(sql, null);
		return c;
	}

	private Cursor findBy(String table_name, String key, String value) {
		if (TextUtils.isEmpty(table_name) || TextUtils.isEmpty(key))
			return null;
		String sql = "select * from " + table_name + " where " + key + "=?";
		Cursor c = getReadableDatabase().rawQuery(sql, new String[] { value });
		return c;
	}

}
