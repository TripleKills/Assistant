package org.sa.studyassistant;

import java.util.List;

import org.sa.studyassistant.db.AssistantDAO;
import org.sa.studyassistant.model.Category;

import android.os.Bundle;

public class KnowledgeActivity extends CategoryActivity {
	private Category category;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		category = (Category) getIntent().getSerializableExtra("category");
		System.out.println("category:" + category);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected List<Category> getCategorys() {
		return AssistantDAO.getInstance().findChildCategorys(category);
	}
}
