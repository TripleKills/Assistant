package org.sa.studyassistant.component;

import java.util.Map;

import org.sa.studyassistant.adapter.KnowledgeAdapter;
import org.sa.studyassistant.db.DBListener;
import org.sa.studyassistant.db.DBObserver;
import org.sa.studyassistant.model.Knowledge;

import android.widget.ListView;

public class KnowledgeListDBListener extends DBListener {
	private ListView list_view;
	private String name;

	public KnowledgeListDBListener(ListView list_view, String tag) {
		super();
		this.list_view = list_view;
		this.name = tag;
	}

	@Override
	public boolean onAction(Map<String, Object> data) {
		String action = (String) data.get(DBObserver.KEY_ACTION);
		if (action.equals(DBObserver.ACTION_INSERT_KNOWLEDGE)
				|| action.equals(DBObserver.ACTION_DELETE_KNOWLEDGE)
				|| action.equals(DBObserver.ACTION_UPDATE_KNOWLEDGE)) {
			onKnowledge(data, action);
		}
		return false;
	}

	protected void onKnowledge(Map<String, Object> data, String action) {
		Knowledge knowledge = (Knowledge) data.get(DBObserver.KEY_KNOWLEDGE);
		KnowledgeAdapter adapter = (KnowledgeAdapter) list_view.getAdapter();
		if (action.equals(DBObserver.ACTION_INSERT_KNOWLEDGE)) {
			if (!adapter.getKnowledges().contains(knowledge))
				adapter.getKnowledges().add(knowledge);
		} else if (action.equals(DBObserver.ACTION_DELETE_KNOWLEDGE)) {
			adapter.getKnowledges().remove(knowledge); // TODO need to
														// test
		} else if (action.equals(DBObserver.ACTION_UPDATE_KNOWLEDGE)) {
			// do nothing
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public String getName() {
		return name;
	}
}
