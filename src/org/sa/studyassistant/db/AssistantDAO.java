package org.sa.studyassistant.db;

import java.util.List;

import org.sa.studyassistant.model.Category;
import org.sa.studyassistant.model.Knowledge;

import android.content.Context;

public class AssistantDAO extends DBObserver {
	private static AssistantDAO _inst;
	private TraceDAO trace;
	private SaveDAO save;

	private AssistantDAO() {
	}

	public static AssistantDAO getInstance() {
		if (null == _inst)
			_inst = new AssistantDAO();
		return _inst;
	}

	public void init(Context context) {
		if (null != save)
			return;
		save = new SaveDAO(context);
		save.regist(this);
		trace = new TraceDAO(context);
		trace.regist(this);
	}

	public boolean insertKnowledge(Knowledge knowledge) {
		int result = (int) save.insertKnowledge(knowledge);
		return result != -1;
	}
	
	public boolean deleteCategory(Category category) {
		List<Category> children = save.findChildCategorys(category);
		for(Category child:children) {
			deleteCategory(child);
		}
		int result = save.deleteCategory(category);
		save.deleteKnowledgesByCategory(category);
		return result != -1;
	}
	
	public boolean insertCategory(String name) {
		Category category = new Category();
		category.name = name;
		return save.insertCategory(category) != -1;
	}
	
	public boolean updateCategoryName(Category category, String name) {
		int result = save.updateCategoryName(category, name);
		return result != -1;
	}
	
	public List<Category> findFirstLevelCategorys() {
		return save.findFirstLevlCategorys();
	}
	
	public List<Category> findChildCategorys(Category category) {
		return save.findChildCategorys(category);
	}

	public List<Category> findAllCategory() {
		return save.findAllCategory();
	}
	
	public List<Knowledge> findDefaultKnowledegs() {
		return findKnowledgeByCategoryId(save.getDefaultKnowledgeId());
	}

	public List<Knowledge> findKnowledgeByCategory(Category category) {
		return findKnowledgeByCategoryId(category.category_id);
	}
	
	public List<Knowledge> findKnowledgeByCategoryId(int category_id) {
		return save.findKnowledgeByCategory(category_id);
	}
}
