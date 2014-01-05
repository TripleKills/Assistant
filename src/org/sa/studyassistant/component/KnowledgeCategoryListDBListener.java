package org.sa.studyassistant.component;

import java.util.Map;

import org.sa.studyassistant.adapter.CategoryAdapter;
import org.sa.studyassistant.adapter.KnowledgeAdapter;
import org.sa.studyassistant.db.DBListener;
import org.sa.studyassistant.model.Category;

import android.widget.ListView;

public class KnowledgeCategoryListDBListener extends DBListener {
	private ListView list_view;
	private String name;
	private CategoryListDBListener category_listener;
	private KnowledgeListDBListener knowledge_listener;
	private OnAction onAction;
	
	public KnowledgeCategoryListDBListener(ListView list_view, String name) {
		super();
		this.list_view = list_view;
		this.name = name;
		category_listener = new CategoryListDBListener(list_view, name);
		knowledge_listener = new KnowledgeListDBListener(list_view, name);
	}
	
	public void setFather(Category father) {
		category_listener = new CategoryListDBListener(list_view, name, father);
	}

	public void setOnAction(OnAction onAction) {
		this.onAction = onAction;
	}

	@Override
	public boolean onAction(Map<String, Object> data) {
		if (null != this.onAction) {
			this.onAction.onAction();
		}
		Object adapter = list_view.getAdapter();
		if (adapter instanceof CategoryAdapter) {
			category_listener.onAction(data);
		} else if (adapter instanceof KnowledgeAdapter) {
			knowledge_listener.onAction(data);
		}
		return false;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	public interface OnAction {
		public void onAction();
	}

}
