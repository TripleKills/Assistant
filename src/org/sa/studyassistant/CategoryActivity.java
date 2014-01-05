package org.sa.studyassistant;

import java.util.List;

import org.sa.studyassistant.adapter.CategoryAdapter;
import org.sa.studyassistant.component.CategoryListDBListener;
import org.sa.studyassistant.component.CategoryLongClickListener;
import org.sa.studyassistant.db.AssistantDAO;
import org.sa.studyassistant.model.Category;
import org.sa.studyassistant.util.ToastUtil;
import org.sa.studyassistant.view.EditDialog;
import org.sa.studyassistant.view.EditDialog.OnPositiveListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class CategoryActivity extends Activity implements OnClickListener {
	protected Button add_button;
	protected ListView list_view;

	private static final String tag = CategoryActivity.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category_act);

		prepareViews();
		setViews();
		setListener();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregistListener();
	}

	protected void unregistListener() {
		AssistantDAO.getInstance().unregist(tag);
	}

	protected List<Category> getCategorys() {
		return AssistantDAO.getInstance().findFirstLevelCategorys();
	}

	private void prepareViews() {
		add_button = (Button) findViewById(R.id.category_act_add);
		list_view = (ListView) findViewById(R.id.category_act_list);
	}

	private void setViews() {
		add_button.setOnClickListener(this);
		list_view.setAdapter(new CategoryAdapter(getCategorys(), this));
		list_view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent it = new Intent(arg0.getContext(),
						KnowledgeActivity.class);
				it.putExtra("category",
						(Category) arg0.getAdapter().getItem(arg2));
				startActivity(it);
			}
		});
		list_view
				.setOnItemLongClickListener(new CategoryLongClickListener(this));
	}

	protected void setListener() {
		AssistantDAO.getInstance().regist(
				new CategoryListDBListener(list_view, tag));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.category_act_add:
			onAddCategoryClick();
		}
	}

	protected void onAddCategoryClick() {
		EditDialog dialog = new EditDialog(this, R.string.category_act_add);
		dialog.setPositiveListener(new OnPositiveListener() {

			@Override
			public void onPositive(String inputtext) {
				addCategoryAction(inputtext);
			}
		});
		dialog.show();
	}

	private void addCategoryAction(String category) {
		if (!TextUtils.isEmpty(category)) {
			boolean result = addCategory(category);
			ToastUtil.toast(result ? R.string.notify_add_success
					: R.string.notify_add_fail);
		}
	}

	protected boolean addCategory(String category) {
		return AssistantDAO.getInstance().insertCategory(category);
	}
}
