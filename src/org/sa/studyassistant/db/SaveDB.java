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
				+ DBMetaData.CATEGORY_TEXT + " TEXT);");

		// question table
		db.execSQL("create table " + DBMetaData.QUESTION_TABLE_NAME
				+ " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ DBMetaData.QUESTION_ANSWER_ID + " LONG, "
				+ DBMetaData.QUESTION_CATEGORY_ID + " LONG, "
				+ DBMetaData.QUESTION_CREATE_TIME + " LONG, "
				+ DBMetaData.QUESTION_TEXT + " TEXT);");

		// answer table
		db.execSQL("create table " + DBMetaData.ANSWER_TABLE_NAME
				+ " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ DBMetaData.ANSWER_TEXT + " TEXT);");

		// resource table
		db.execSQL("create table " + DBMetaData.RESOURCE_TABLE_NAME
				+ " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ DBMetaData.RESOURCE_TYPE + " TEXT, "
				+ DBMetaData.RESOURCE_PATH + " TEXT);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	// apis for answer
	public long insertAnswer(String text) {
		ContentValues values = new ContentValues();
		values.put(DBMetaData.ANSWER_TEXT, text);
		return getWritableDatabase().insert(DBMetaData.ANSWER_TABLE_NAME, null,
				values);
	}

	// apis for question
	public long insertQuestion(long answer_id, long category_id, String text, long create_time) {
		if (answer_id < 0 || category_id < 0)
			return -1;
		ContentValues values = new ContentValues();
		values.put(DBMetaData.QUESTION_ANSWER_ID, answer_id);
		values.put(DBMetaData.QUESTION_CATEGORY_ID, category_id);
		values.put(DBMetaData.QUESTION_TEXT, text);
		values.put(DBMetaData.QUESTION_CREATE_TIME, create_time);
		return getWritableDatabase().insert(DBMetaData.QUESTION_TABLE_NAME,
				null, values);
	}

	public Cursor findQuestionsByCategory(long category_id) {
		String sql = "select * from " + DBMetaData.QUESTION_TABLE_NAME
				+ " where " + DBMetaData.QUESTION_CATEGORY_ID + "=?";
		Cursor c = getReadableDatabase().rawQuery(sql,
				new String[] { String.valueOf(category_id) });
		return c;
	}

	// apis for category
	public long findCategoryIdWithInsert(String name) {
		if (TextUtils.isEmpty(name))
			return -1;
		long id = findCategoryId(name);
		if (id != -1)
			return id;
		ContentValues values = new ContentValues();
		values.put(DBMetaData.CATEGORY_TEXT, name);
		return getWritableDatabase().insert(DBMetaData.CATEGORY_TABLE_NAME,
				null, values);
	}

	public long findCategoryId(String name) {
		long result = -1;
		if (TextUtils.isEmpty(name))
			return result;
		String sql = "select * from " + DBMetaData.CATEGORY_TABLE_NAME
				+ " where name=?";
		Cursor c = getReadableDatabase().rawQuery(sql, new String[] { name });
		if (null == c)
			return -1;
		try {
			if (null != c && c.moveToFirst()) {
				result = c.getInt(c.getColumnIndex("_id"));
			}
		} finally {
			c.close();
		}
		return result;
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

}
