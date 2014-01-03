package org.sa.studyassistant.db;

import java.util.ArrayList;
import java.util.List;

import org.sa.studyassistant.model.Category;
import org.sa.studyassistant.model.Knowledge;

import android.content.Context;
import android.database.Cursor;

public class SaveDAO extends DBObserver {
	private SaveDB db;
	private static final String tag = SaveDAO.class.getName();
	private static final int DEFAULT_CATEGORY_ID = -2;

	public SaveDAO(Context context) {
		db = new SaveDB(context);
	}

	public long insertCategory(Category category) {
		int belong_to = -1;
		if (null != category.belong_to)
			belong_to = category.belong_to.category_id;
		return db.insertCategory(category.name, belong_to);
	}

	public long insertKnowledge(Knowledge knowledge) {
		int category_id = DEFAULT_CATEGORY_ID;
		if (null != knowledge.category)
			category_id = knowledge.category.category_id;
		return db.insertKnowledge(knowledge.answer, category_id, knowledge.question, knowledge.create_time);
	}
	
	public int getDefaultKnowledgeId() {
		return DEFAULT_CATEGORY_ID;
	}

	public List<Category> findAllCategory() {
		List<Category> categories = new ArrayList<Category>();
		Cursor c = db.findAllCategory();
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

	private Knowledge recreateKnowledge(Cursor c) {
		Knowledge knowledge = new Knowledge();
		knowledge.question = c.getString(c.getColumnIndex(DBMetaData.KNOWLEDGE_QUESTION));
		knowledge.answer = c.getString(c.getColumnIndex(DBMetaData.KNOWLEDGE_ANSWER));
		knowledge.create_time = c.getLong(c
				.getColumnIndex(DBMetaData.KNOWLEDGE_CREATE_TIME));
		return knowledge;
	}
}
