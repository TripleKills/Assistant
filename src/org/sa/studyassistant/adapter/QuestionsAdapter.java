package org.sa.studyassistant.adapter;

import java.util.List;

import org.sa.studyassistant.R;
import org.sa.studyassistant.model.Knowledge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class QuestionsAdapter extends BaseAdapter {
	private List<Knowledge> questions;
	private Context mContext;

	public QuestionsAdapter(List<Knowledge> questions, Context mContext) {
		super();
		this.questions = questions;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return questions.size();
	}

	@Override
	public Object getItem(int position) {
		return questions.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.questions_adapter, null);
			holder.text = (TextView) convertView.findViewById(R.id.questions_adapter_text);
			holder.tag = (ImageView) convertView.findViewById(R.id.questions_adapter_tag);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		Knowledge question = (Knowledge) getItem(position);
		holder.text.setText(question.question);
		holder.tag.setVisibility(View.VISIBLE);
		
		return convertView;
	}
	
	private class ViewHolder {
		public TextView text;
		public ImageView tag;
	}

}
