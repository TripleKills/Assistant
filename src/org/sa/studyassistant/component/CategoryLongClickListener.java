package org.sa.studyassistant.component;

import org.sa.studyassistant.R;
import org.sa.studyassistant.adapter.CategoryAdapter;
import org.sa.studyassistant.db.AssistantDAO;
import org.sa.studyassistant.model.Category;
import org.sa.studyassistant.util.ToastUtil;
import org.sa.studyassistant.view.EditDialog;
import org.sa.studyassistant.view.EditDialog.OnPositiveListener;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;

public class CategoryLongClickListener implements OnItemLongClickListener {
	private Context mContext;

	public CategoryLongClickListener(Context mContext) {
		super();
		this.mContext = mContext;
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		CategoryAdapter adapter = (CategoryAdapter) arg0.getAdapter();
		final Category category = (Category) adapter.getItem(arg2);
		Builder builder = new Builder(arg1.getContext());
		builder.setItems(R.array.dialog_cateroy_option,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						onOption(category, arg1);
					}
				});
		builder.create().show();
		return false;
	}

	private void onOption(final Category category, int arg1) {
		switch (arg1) {
		case 0:
			onModify(category);
			break;
		case 1:
			onDelete(category);
		}
	}

	private void onDelete(final Category category) {
		boolean result = AssistantDAO.getInstance().deleteCategory(category);
		ToastUtil.toast(result ? R.string.notify_delete_success
				: R.string.notify_delete_fail);
	}

	private void onModify(final Category category) {
		EditDialog dialog = new EditDialog(mContext,
				R.string.category_act_modity);
		dialog.setPositiveListener(new OnPositiveListener() {

			@Override
			public void onPositive(String inputtext) {
				if (TextUtils.isEmpty(inputtext))
					return;
				boolean result = AssistantDAO.getInstance().updateCategoryName(
						category, inputtext);
				ToastUtil.toast(result ? R.string.notify_modity_success
						: R.string.notify_modity_fail);
			}
		});
		dialog.show();
	}
}
