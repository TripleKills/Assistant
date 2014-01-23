package org.sa.studyassistant;

import java.util.ArrayList;
import java.util.List;

import org.sa.studyassistant.adapter.CategoryAdapter;
import org.sa.studyassistant.adapter.KnowledgeAdapter;
import org.sa.studyassistant.component.KnowledgeCategoryListDBListener;
import org.sa.studyassistant.component.KnowledgeCategoryListDBListener.OnAction;
import org.sa.studyassistant.db.AssistantDAO;
import org.sa.studyassistant.model.Category;
import org.sa.studyassistant.model.Knowledge;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class KnowledgeActivity extends CategoryActivity {
	private Category category;
	private String status;// "initial", "category", "knowledge" # can't use
	private static final String tag = KnowledgeActivity.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		category = (Category) getIntent().getSerializableExtra("category");
		System.out.println("category:" + category);
		super.onCreate(savedInstanceState);
		setViews();
	}

	@Override
	protected void setListener() {
		KnowledgeCategoryListDBListener listener = new KnowledgeCategoryListDBListener(
				list_view, tag);
		listener.setFather(category);
		listener.setOnAction(new OnAction() {

			@Override
			public void onAction() {
				String current_status = getCurrentStatus();
				if (!current_status.equals(status)) {
					status = current_status;
					refreshViews();
				}
			}
		});
		AssistantDAO.getInstance().regist(listener);
	}

	@Override
	protected void unregistListener() {
		AssistantDAO.getInstance().unregist(tag);
	}

	private String getCurrentStatus() {
		boolean category_empty = getCategorys().isEmpty();
		boolean knowledge_empty = getKnowledges().isEmpty();
		if (category_empty && knowledge_empty) {
			return "initial";
		} else if (category_empty) {
			return "knowledge";
		} else {
			return "category";
		}
	}

	private String getStatus() {
		if (null != status)
			return status;
		status = getCurrentStatus();
		return status;
	}

	private void setViews() {
		setButton();
		if (getStatus().equals("initial")) {
			// setCategoryView();
		} else if (getStatus().equals("knowledge")) {
			setKnowledgeView();
		}
	}

	private void setButton() {
		int res = R.string.category_act_add;
		if (getStatus().equals("initial")) {
			res = R.string.add;
		} else if (getStatus().equals("knowledge")) {
			res = R.string.add_knowledge;
		}
		add_button.setText(res);
	}

	private void setKnowledgeView() {
		KnowledgeAdapter adapter = new KnowledgeAdapter(getKnowledges(), this);
		list_view.setAdapter(adapter);
		setKnowledgeItemClickEvent();
	}

	private void refreshViews() {
		setButton();
		if (getStatus().equals("category")) {
			setCategoryViewEmpty();
		} else if (getStatus().equals("knowledge")) {
			setKnowledgeViewEmpty();
			setKnowledgeItemClickEvent();
		}
	}

	private void setCategoryViewEmpty() {
		CategoryAdapter adapter = new CategoryAdapter(
				new ArrayList<Category>(), this);
		list_view.setAdapter(adapter);
	}

	private void setKnowledgeViewEmpty() {
		KnowledgeAdapter adapter = new KnowledgeAdapter(
				new ArrayList<Knowledge>(), this);
		list_view.setAdapter(adapter);
	}
	
	private void setKnowledgeItemClickEvent() {
		list_view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent it = new Intent(arg0.getContext(),
						KnowledgeEditActivity.class);
				it.putExtra("knowledge",
						(Knowledge) arg0.getAdapter().getItem(arg2));
				startActivity(it);
			}
		});
		list_view.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				return false;
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.category_act_add:
			onClickAdd();
		}
	}

	private void onClickAdd() {
		if (getStatus().equals("initial")) {
			Builder builder = new Builder(this);
			builder.setItems(R.array.dialog_add_option,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							switch (arg1) {
							case 0:
								onAddCategoryClick();
								break;
							case 1:
								onAddKnowledgeClick();
							}
						}
					});
			builder.create().show();
		} else if (getStatus().equals("knowledge")) {
			onAddKnowledgeClick();
		} else {
			onAddCategoryClick();
		}
	}

	private void onAddKnowledgeClick() {
		Intent it = new Intent(this, KnowledgeEditActivity.class);
		it.putExtra("category", category);
		startActivity(it);
	}

	@Override
	protected List<Category> getCategorys() {
		return AssistantDAO.getInstance().findChildCategorys(category);
	}

	@Override
	protected boolean addCategory(String category_name) {
		Category m_category = new Category();
		m_category.belong_to = category.category_id;
		m_category.name = category_name;
		return AssistantDAO.getInstance().insertCategory(m_category);
	}

	private List<Knowledge> getKnowledges() {
		return AssistantDAO.getInstance().findKnowledgeByCategory(category);
	}
}
