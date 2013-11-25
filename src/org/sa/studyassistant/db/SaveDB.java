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
				+ DBMetaData.QUESTION_ANSWER_ID + " TEXT, "
				+ DBMetaData.QUESTION_CATEGORY_ID + " TEXT, "
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

	// apis for category
	public long addCategory(String name) {
		if (TextUtils.isEmpty(name)) return -1;
		if (isCategoryExist(name)) return -2;
		ContentValues values = new ContentValues();
		values.put(DBMetaData.CATEGORY_TEXT, name);
		return getWritableDatabase().insert(DBMetaData.CATEGORY_TABLE_NAME, null, values);
	}

	public boolean isCategoryExist(String name) {
		if (TextUtils.isEmpty(name))
			return false;
		String sql = "select * from " + DBMetaData.CATEGORY_TABLE_NAME
				+ " where name=?";
		Cursor c = getReadableDatabase().rawQuery(sql, new String[] { name });
		if (null == c) return false;
		boolean result = c.moveToFirst();
		c.close();
		return result;
	}

}
