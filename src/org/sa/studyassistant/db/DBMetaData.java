package org.sa.studyassistant.db;

public class DBMetaData {
	public static final String SAVE_DB_NAME = "save";
	public static final int SAVE_DB_VERSION = 1;
	
	public static final String CATEGORY_TABLE_NAME = "category";
	public static final String CATEGORY_TEXT = "text";
	
	public static final String QUESTION_TABLE_NAME = "question";
	public static final String QUESTION_TEXT = "text";
	public static final String QUESTION_CREATE_TIME = "create_time";
	public static final String QUESTION_CATEGORY_ID = "category_id";
	public static final String QUESTION_ANSWER_ID = "answer_id";
	
	public static final String ANSWER_TABLE_NAME = "answer";
	public static final String ANSWER_TEXT = "text";
	
	public static final String RESOURCE_TABLE_NAME = "resource";
	public static final String RESOURCE_PATH = "path";
	public static final String RESOURCE_TYPE = "type";
	
	
	
	public static final String TRACE_DB_NAME = "trace";
	public static final int TRACE_DB_VERSION = 1;
	
	public static final String TRACE_TABLE_NAME = "trace";
	public static final String TRACE_ITEM_TYPE = "item_type";
	public static final String TRACE_ITEM_ID = "item_id";
	public static final String TRACE_TIME_DAY = "time_day";
	public static final String TRACE_ACTION = "action";
	public static final String TRACE_TIME_HOURINFO = "time_hourinfo";
}
