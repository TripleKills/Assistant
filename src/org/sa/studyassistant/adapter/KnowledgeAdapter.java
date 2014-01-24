package org.sa.studyassistant.adapter;

import java.util.List;

import org.sa.studyassistant.R;
import org.sa.studyassistant.db.AssistantDAO;
import org.sa.studyassistant.model.Knowledge;
import org.sa.studyassistant.util.Logger;
import org.sa.studyassistant.util.StringUtil;

import com.readystatesoftware.viewbadger.BadgeView;

import android.content.Context;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class KnowledgeAdapter extends BaseAdapter {
	private List<Knowledge> knowledges;
	private Context mContext;
	private static final String tag = KnowledgeAdapter.class.getName();

	public KnowledgeAdapter(List<Knowledge> questions, Context mContext) {
		super();
		this.knowledges = questions;
		this.mContext = mContext;
	}

	public List<Knowledge> getKnowledges() {
		return knowledges;
	}

	@Override
	public int getCount() {
		return knowledges.size();
	}

	@Override
	public Object getItem(int position) {
		return knowledges.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = (ViewHolder) (null == convertView ? null
				: convertView.getTag());
		if (null == holder) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.questions_adapter, null);
			holder.text = (TextView) convertView
					.findViewById(R.id.questions_adapter_text);
		//	holder.tag = (ImageView) convertView
		//			.findViewById(R.id.questions_adapter_tag);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Knowledge question = (Knowledge) getItem(position);
		holder.text.setText(StringUtil.removeTailLine(question.question));
		if (!AssistantDAO.getInstance().isCurrentPhaseCheck(question)) {
			if (null == holder.badge) {
				holder.badge = new BadgeView(holder.text.getContext(), holder.text);
				holder.badge.setBadgePosition(BadgeView.POSITION_BOTTOM_RIGHT);
			}
			holder.badge.setText(String.valueOf("1"));
			holder.badge.show();
		} else if (null != holder.badge) {
			holder.badge.hide();
		}
	//	holder.tag.setVisibility(View.VISIBLE);

		return convertView;
	}

	private class ViewHolder {
		public TextView text;
		BadgeView badge;
	//	public ImageView tag;
	}

}
