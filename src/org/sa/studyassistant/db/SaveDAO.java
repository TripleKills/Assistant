package org.sa.studyassistant.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sa.studyassistant.model.Category;
import org.sa.studyassistant.model.Knowledge;
import org.sa.studyassistant.util.Logger;

import android.content.Context;
import android.database.Cursor;

public class SaveDAO extends DBObserver {
	private SaveDB db;
	private static final String tag = SaveDAO.class.getName();
	private static final int DEFAULT_CATEGORY_ID = -2;

	public SaveDAO(Context context) {
		db = new SaveDB(context);
	}
	
	public Cursor getAllKnowledgeAfter(long time) {
		return db.getAllKnowledgeAfter(time);
	}
	
	public Cursor getAllKnowledgeAfterByCategory(long time, int category_id) {
		return db.getAllKnowledgeAfterByCategory(time, category_id);
	}

	public int insertCategory(Category category) {
		int belong_to = -1;
		if (category.belong_to > 0 && category.belong_to != DEFAULT_CATEGORY_ID)
			belong_to = category.belong_to;
		int result = db.insertCategory(category.name, belong_to);
		if (result != -1) {
			category.category_id = result;
			Map<String, Object> data = new HashMap<String, Object>();
			data.put(KEY_ACTION, ACTION_INSERT_CATEGORY);
			data.put(KEY_CATEGORY, category);
			onAction(data);
		}
		return result;
	}

	public int deleteCategory(Category category) {
		int result = db.deleteCategory(category.category_id);
		if (result != -1) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put(KEY_ACTION, ACTION_DELETE_CATEGORY);
			data.put(KEY_CATEGORY, category);
			onAction(data);
		}
		return result;
	}
	
	public int deleteKnowledgesByCategory(Category category) {
		Logger.i(tag, "deleteKnowledgesByCategory:" + category.category_id);
		return db.deleteKnowledgesByCategory(category.category_id);
	}

	public List<Category> findChildCategorys(Category category) {
		Cursor cursor = db.findCategorysByCategory(category.category_id);
		return createCategoryList(cursor);
	}

	public long insertKnowledge(Knowledge knowledge) {
		int category_id = DEFAULT_CATEGORY_ID;
		if (null != knowledge.category)
			category_id = knowledge.category.category_id;
		int result = db.insertKnowledge(knowledge.answer, category_id,
				knowledge.question, knowledge.create_time);
		if (result != -1) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put(KEY_ACTION, ACTION_INSERT_KNOWLEDGE);
			data.put(KEY_KNOWLEDGE, knowledge);
			onAction(data);
		}
		return result;
	}

	public int getDefaultKnowledgeId() {
		return DEFAULT_CATEGORY_ID;
	}
	
	public int updateCategoryName(Category category, String name) {
		int result = db.updateCategoryName(name, category.category_id);
		if (result != -1) {
			category.name = name;
			Map<String, Object> data = new HashMap<String, Object>();
			data.put(KEY_ACTION, ACTION_UPDATE_CATEGORY);
			data.put(KEY_CATEGORY, category);
			onAction(data);
		}
		return result;
	}

	public List<Category> findAllCategory() {
		Cursor c = db.findAllCategory();
		return createCategoryList(c);
	}
	
	public List<Category> findFirstLevlCategorys() {
		Cursor c = db.findFirstLevelCategorys();
		return createCategoryList(c);
	}

	private List<Category> createCategoryList(Cursor c) {
		List<Category> categories = new ArrayList<Category>();
		if (null == c)
			return categories;
		try {
			while (c.moveToNext()) {
				Category category = recreateCategory(c);
				categories.add(category);
			}
		} finally {
			c.close();
		}
		return categories;
	}

	private Category recreateCategory(Cursor c) {
		Category category = new Category();
		category.name = c.getString(c.getColumnIndex(DBMetaData.CATEGORY_NAME));
		category.category_id = c.getInt(c.getColumnIndex("_id"));
		category.belong_to = c.getInt(c.getColumnIndex(DBMetaData.CATEGORY_BELONG_TO));
		return category;
	}

	public List<Knowledge> findKnowledgeByCategory(int category_id) {
		List<Knowledge> knowledges = new ArrayList<Knowledge>();
		Cursor c = db.findKnowledgesByCategory(category_id);
		if (null == c)
			return knowledges;
		try {
			while (c.moveToNext()) {
				Knowledge knowledge = recreateKnowledge(c);
				knowledges.add(knowledge);
			}
		} finally {
			c.close();
		}
		return knowledges;
	}

	public Knowledge recreateKnowledge(Cursor c) {
		Knowledge knowledge = new Knowledge();
		knowledge.question = c.getString(c
				.getColumnIndex(DBMetaData.KNOWLEDGE_QUESTION));
		knowledge.answer = c.getString(c
				.getColumnIndex(DBMetaData.KNOWLEDGE_ANSWER));
		knowledge.create_time = c.getLong(c
				.getColumnIndex(DBMetaData.KNOWLEDGE_CREATE_TIME));
		knowledge._id = c.getInt(c.getColumnIndex("_id"));
		return knowledge;
	}
}
