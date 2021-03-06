package org.sa.studyassistant.db;

import org.sa.studyassistant.util.DBUtil;

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
	public int insertKnowledge(String answer, int category_id, String question,
			long create_time) {
		if ((null == question && null == answer) || category_id < 0)
			return -1;
		ContentValues values = new ContentValues();
		values.put(DBMetaData.KNOWLEDGE_ANSWER, answer);
		values.put(DBMetaData.KNOWLEDGE_CATEGORY_ID, category_id);
		values.put(DBMetaData.KNOWLEDGE_QUESTION, question);
		values.put(DBMetaData.KNOWLEDGE_CREATE_TIME, create_time);
		return (int) getWritableDatabase().insert(DBMetaData.KNOWLEDGE_TABLE_NAME,
				null, values);
	}
	
	public int deleteKnowledge(int _id) {
		return DBUtil.deleteBy(this, DBMetaData.KNOWLEDGE_TABLE_NAME, "_id", String.valueOf(_id));
	}
	
	public int deleteCategorysByCategory(int category_id) {
		return deleteBy(DBMetaData.CATEGORY_TABLE_NAME, DBMetaData.CATEGORY_BELONG_TO, String.valueOf(category_id));
	}
	
	public int deleteKnowledgesByCategory(int category_id) {
		return deleteBy(DBMetaData.KNOWLEDGE_TABLE_NAME, DBMetaData.KNOWLEDGE_CATEGORY_ID, String.valueOf(category_id));
	}

	public Cursor findKnowledgesByCategory(int category_id) {
		return findBy(DBMetaData.KNOWLEDGE_TABLE_NAME,
				DBMetaData.KNOWLEDGE_CATEGORY_ID, String.valueOf(category_id));
	}
	
	public int deleteCategory(int category_id) {
		return deleteBy(DBMetaData.CATEGORY_TABLE_NAME, "_id", String.valueOf(category_id));
	}
	
	public Cursor getAllKnowledgeAfter(long time) {
		String sql = "select * from " + DBMetaData.KNOWLEDGE_TABLE_NAME + " where " + DBMetaData.KNOWLEDGE_CREATE_TIME + ">?";
		Cursor c = getReadableDatabase().rawQuery(sql, new String[] { String.valueOf(time) });
		return c;
	}
	
	public Cursor getAllKnowledgeAfterByCategory(long time, int category_id) {
		String sql = "select * from " + DBMetaData.KNOWLEDGE_TABLE_NAME + " where " + DBMetaData.KNOWLEDGE_CREATE_TIME + ">?"
				+ " and " + DBMetaData.KNOWLEDGE_CATEGORY_ID + "=?";
		Cursor c = getReadableDatabase().rawQuery(sql, new String[] { String.valueOf(time), String.valueOf(category_id) });
		return c;
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
	
	public Cursor findFirstLevelCategorys() {
		return findBy(DBMetaData.CATEGORY_TABLE_NAME, DBMetaData.CATEGORY_BELONG_TO, "-1");
	}
	
	public Cursor findCategorysByCategory(int category_id) {
		return findBy(DBMetaData.CATEGORY_TABLE_NAME, DBMetaData.CATEGORY_BELONG_TO, String.valueOf(category_id));
	}
	
	public int updateCategoryName(String name, int category_id) {
		ContentValues values = new ContentValues();
		values.put(DBMetaData.CATEGORY_NAME, name);
		return updateBy(DBMetaData.CATEGORY_TABLE_NAME, values, "_id", String.valueOf(category_id));
	}

	// apis for all tables
	private Cursor findAll(String table_name) {
		return DBUtil.findAll(this, table_name);
	}
	
	private Cursor findBy(String table_name, String key, String value) {
		return DBUtil.findBy(this, table_name, key, value);
	}
	
	private int deleteBy(String table_name, String key, String value) {
		return DBUtil.deleteBy(this, table_name, key, value);
	}
	
	private int updateBy(String table_name, ContentValues values, String key, String value) {
		return DBUtil.updateBy(this, table_name, values, key, value);
	}

}
