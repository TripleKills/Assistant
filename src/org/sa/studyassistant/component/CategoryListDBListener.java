package org.sa.studyassistant.component;

import java.util.Map;

import org.sa.studyassistant.adapter.CategoryAdapter;
import org.sa.studyassistant.db.DBListener;
import org.sa.studyassistant.db.DBObserver;
import org.sa.studyassistant.model.Category;

import android.widget.ListView;

public class CategoryListDBListener extends DBListener {
	private ListView list_view;
	private String name;
	private Category father;

	public CategoryListDBListener(ListView list_view, String tag) {
		this(list_view, tag, null);
	}

	public CategoryListDBListener(ListView list_view, String tag,
			Category father) {
		super();
		this.list_view = list_view;
		this.name = tag;
		this.father = father;
	}

	@Override
	public boolean onAction(Map<String, Object> data) {
		String action = (String) data.get(DBObserver.KEY_ACTION);
		if (action.equals(DBObserver.ACTION_INSERT_CATEGORY)
				|| action.equals(DBObserver.ACTION_DELETE_CATEGORY)
				|| action.equals(DBObserver.ACTION_UPDATE_CATEGORY)) {
			onCategory(data, action);
		}
		return false;
	}

	protected void onCategory(Map<String, Object> data, String action) {
		Category category = (Category) data.get(DBObserver.KEY_CATEGORY);
		if (null == father && category.belong_to != -1) {
			return;
		} else if (null != father && category.belong_to != father.category_id) {
			return;
		}
		CategoryAdapter adapter = (CategoryAdapter) list_view.getAdapter();
		if (action.equals(DBObserver.ACTION_INSERT_CATEGORY)) {
			if (!adapter.getCategorys().contains(category))
				adapter.getCategorys().add(category);
		} else if (action.equals(DBObserver.ACTION_DELETE_CATEGORY)) {
			adapter.getCategorys().remove(category); // TODO need to
														// test
		} else if (action.equals(DBObserver.ACTION_UPDATE_CATEGORY)) {
			// do nothing
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public String getName() {
		return name;
	}
}
