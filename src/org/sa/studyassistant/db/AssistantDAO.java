package org.sa.studyassistant.db;

import java.util.List;

import org.sa.studyassistant.model.Category;
import org.sa.studyassistant.model.Knowledge;
import org.sa.studyassistant.model.Trace;
import org.sa.studyassistant.util.Ebbinghaus;
import org.sa.studyassistant.util.Logger;

import android.content.Context;
import android.database.Cursor;

public class AssistantDAO extends DBObserver {
	private static AssistantDAO _inst;
	private TraceDAO trace;
	private SaveDAO save;
	private static final String tag = AssistantDAO.class.getName();

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
	
	public boolean checkKnowledgeMain(Knowledge knowledge) {
		return checkKnowledge(knowledge, TraceDB.ACTION_MAIN);
	}
	
	public boolean checkKnowledge(Knowledge knowledge, String action) {
		Trace mTrace = new Trace();
		mTrace.action = action;
		mTrace.item_id = knowledge._id;
		mTrace.phase = Ebbinghaus.getPhase(knowledge.create_time);
		mTrace.time = System.currentTimeMillis();
		return trace.insertTrace(mTrace) != -1;
	}

	/**
	 * 1.如果当前phase还没有check立即返回 2.最后一次check的时间距离下个phase的标准时间距离最长
	 */
	public Knowledge getTheMostKnowledge() {
		Cursor c = save.getAllKnowledgeAfter(Ebbinghaus.getTimePassPoint());
		if (null == c)
			return null;
		Knowledge target = null;
		long last_delta = -1;
		try {
			while (c.moveToNext()) {
				Knowledge knowledge = save.recreateKnowledge(c);
				Trace mTrace = trace.findByKnowledge(knowledge);
				int current_phase = Ebbinghaus.getPhase(knowledge.create_time);
				if (null == mTrace || mTrace.phase < current_phase) {
					target = knowledge;
					break;
				} else if (null == target) {
					target = knowledge;
					last_delta = Ebbinghaus.getNextPhaseDelta(
							knowledge.create_time, mTrace.time);
					continue;
				}

				long delta = Ebbinghaus.getNextPhaseDelta(
						knowledge.create_time, mTrace.time);
				if (delta > last_delta) {
					target = knowledge;
					last_delta = delta;
				}
			}
		} finally {
			c.close();
		}
		return target;
	}
	
	
	public Knowledge getCurrentUncheckKnowledge() {
		Cursor c = save.getAllKnowledgeAfter(Ebbinghaus.getTimePassPoint());
		return getCurrentUncheckKnowledgeByCursor(c);
	}
	
	public boolean isCurrentPhaseCheck(Knowledge knowledge) {
		Trace mTrace = trace.findByKnowledge(knowledge);
		int current_phase = Ebbinghaus.getPhase(knowledge.create_time);
		return null != mTrace && mTrace.phase >= current_phase;
	}
	
	/**
	 * 是否有当前phase还没有check的
	 * @return
	 */
	public boolean hasThisCurrentUncheckKnowledge() {
		return getCurrentUncheckKnowledge() != null;
	}
	
	public boolean hasThisCurrentUncheckKnowledge(Category category) {
		List<Category> children = save.findChildCategorys(category);
		if (null == children || children.isEmpty()) {
			return getCurrentUncheckKnowledgeNum(category) > 0;
		} else {
			for (Category child : children) {
				if (hasThisCurrentUncheckKnowledge(child)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public int getCurrentUncheckKnowledgeNum(Category category) {
		List<Category> children = save.findChildCategorys(category);
		int uncheck_num = 0;
		if (null == children || children.isEmpty()) {
			Cursor c = save.getAllKnowledgeAfterByCategory(Ebbinghaus.getTimePassPoint(), category.category_id);
			uncheck_num = getCurrentUncheckKnowledgeNumByCursor(c);
		} else {
			for (Category child : children) {
				if (hasThisCurrentUncheckKnowledge(child)) {
					uncheck_num += getCurrentUncheckKnowledgeNum(child);
				}
			}
		}
		return uncheck_num;
	}
	
	protected Knowledge getCurrentUncheckKnowledgeByCursor(Cursor c) {
		if (null == c)
			return null;
		try {
			while (c.moveToNext()) {
				Knowledge knowledge = save.recreateKnowledge(c);
				Trace mTrace = trace.findByKnowledge(knowledge);
				int current_phase = Ebbinghaus.getPhase(knowledge.create_time);
				if (null == mTrace || mTrace.phase < current_phase) {
					return knowledge;
				}
			}
		} finally {
			c.close();
		}
		return null;
	}

	protected int getCurrentUncheckKnowledgeNumByCursor(Cursor c) {
		if (null == c)
			return 0;
		int count = 0;
		try {
			while (c.moveToNext()) {
				Knowledge knowledge = save.recreateKnowledge(c);
				Trace mTrace = trace.findByKnowledge(knowledge);
				int current_phase = Ebbinghaus.getPhase(knowledge.create_time);
				if (null == mTrace || mTrace.phase < current_phase) {
					count++;
				}
			}
		} finally {
			c.close();
		}
		return count;
	}

	public boolean insertKnowledge(Knowledge knowledge) {
		int result = (int) save.insertKnowledge(knowledge);
		return result != -1;
	}

	public boolean deleteCategory(Category category) {
		List<Category> children = save.findChildCategorys(category);
		for (Category child : children) {
			deleteCategory(child);
		}
		int result = save.deleteCategory(category);
		save.deleteKnowledgesByCategory(category);
		Logger.i(tag, "delete category:" + category.category_id);
		return result != -1;
	}

	public boolean insertCategory(String name) {
		Category category = new Category();
		category.name = name;
		return insertCategory(category);
	}

	public boolean insertCategory(Category category) {
		int result = save.insertCategory(category);
		return result != -1;
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
