package org.sa.studyassistant.db;

public class DBMetaData {
	public static final String SAVE_DB_NAME = "save";
	public static final int SAVE_DB_VERSION = 1;
	
	public static final String CATEGORY_TABLE_NAME = "category";
	public static final String CATEGORY_NAME = "name";
	public static final String CATEGORY_BELONG_TO = "belong_to";
	
	public static final String KNOWLEDGE_TABLE_NAME = "knowledge";
	public static final String KNOWLEDGE_QUESTION = "question";
	public static final String KNOWLEDGE_ANSWER = "answer";
	public static final String KNOWLEDGE_CREATE_TIME = "create_time";
	public static final String KNOWLEDGE_CATEGORY_ID = "category_id";

	
	public static final String TRACE_DB_NAME = "trace";
	public static final int TRACE_DB_VERSION = 1;
	
	public static final String TRACE_TABLE_NAME = "trace";
	public static final String TRACE_ITEM_ID = "item_id";
	public static final String TRACE_ACTION = "action";
	public static final String TRACE_PHRASE = "phrase";
	public static final String TRACE_TIME = "time";
}
