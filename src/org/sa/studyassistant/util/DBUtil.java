package org.sa.studyassistant.util;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

public class DBUtil {
	public static Cursor findAll(SQLiteOpenHelper helper, String table_name) {
		String sql = "select * from " + table_name;
		Cursor c = helper.getReadableDatabase().rawQuery(sql, null);
		return c;
	}

	public static Cursor findBy(SQLiteOpenHelper helper, String table_name,
			String key, String value) {
		if (TextUtils.isEmpty(table_name) || TextUtils.isEmpty(key))
			return null;
		String sql = "select * from " + table_name + " where " + key + "=?";
		Cursor c = helper.getReadableDatabase().rawQuery(sql,
				new String[] { value });
		return c;
	}

	public static int deleteBy(SQLiteOpenHelper helper, String table_name,
			String key, String value) {
		return helper.getWritableDatabase().delete(table_name, key + "=?",
				new String[] { value });
	}

	public static int updateBy(SQLiteOpenHelper helper, String table_name,
			ContentValues values, String key, String value) {
		return helper.getWritableDatabase().update(table_name, values,
				key + "=?", new String[] { value });
	}
}
