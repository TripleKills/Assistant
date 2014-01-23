package org.sa.studyassistant.adapter;

import java.util.List;

import org.sa.studyassistant.R;
import org.sa.studyassistant.db.AssistantDAO;
import org.sa.studyassistant.model.Category;
import org.sa.studyassistant.util.StringUtil;

import com.readystatesoftware.viewbadger.BadgeView;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CategoryAdapter extends BaseAdapter {

	private List<Category> categorys;
	private Context mContext;

	public CategoryAdapter(List<Category> categorys, Context mContext) {
		super();
		this.categorys = categorys;
		this.mContext = mContext;
	}

	public List<Category> getCategorys() {
		return categorys;
	}

	@Override
	public int getCount() {
		return categorys.size();
	}

	@Override
	public Object getItem(int arg0) {
		return categorys.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder = null != arg1 ? (ViewHolder) arg1.getTag() : null;
		Category category = (Category) getItem(arg0);
		if (null == holder) {
			holder = new ViewHolder();
			arg1 = LayoutInflater.from(mContext).inflate(
					R.layout.category_adapter, null);
			holder.text_view = (TextView) arg1
					.findViewById(R.id.category_adapter_text);
			
			//TextView textview = new TextView(mContext);
			//textview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			//textview.setPadding(15, 15, 0, 15);
			//holder.text_view = textview;
			//arg1 = textview;
			arg1.setTag(holder);
		}
		holder.text_view.setText(StringUtil.removeTailLine(category.name));
		int uncheck_count = AssistantDAO.getInstance().getCurrentUncheckKnowledgeNum(category);
		if (uncheck_count > 0) {
			BadgeView badge1 = new BadgeView(holder.text_view.getContext(), holder.text_view);
			badge1.setText(String.valueOf(uncheck_count));
		    badge1.setBadgePosition(BadgeView.POSITION_BOTTOM_RIGHT);
		    badge1.show();
		}
		return arg1;
	}

	class ViewHolder {
		TextView text_view;
	}

}
