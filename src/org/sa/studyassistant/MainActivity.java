package org.sa.studyassistant;

import org.sa.studyassistant.db.AssistantDAO;
import org.sa.studyassistant.model.Knowledge;
import org.sa.studyassistant.util.FileUtil;
import org.sa.studyassistant.util.Logger;
import org.sa.studyassistant.util.Parser;
import org.sa.studyassistant.util.ToastUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final String tag = MainActivity.class.getName();
	private RelativeLayout show;
	private TextView question, answer, no_knowledge;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_aty);
		// startActivity(new Intent(this, CategoryActivity.class));

		// testparse();
		prepareViews();
		initViews();
	}

	private void prepareViews() {
		show = (RelativeLayout) findViewById(R.id.main_aty_show);
		question = (TextView) findViewById(R.id.main_aty_q);
		answer = (TextView) findViewById(R.id.main_aty_a);
		no_knowledge = (TextView) findViewById(R.id.main_aty_no_knowledge);
		setToHome(no_knowledge);
		setToHome(findViewById(R.id.main_aty_index));
		findViewById(R.id.main_aty_check).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Knowledge current = (Knowledge) show.getTag();
						AssistantDAO dao = AssistantDAO.getInstance();
						dao.checkKnowledgeMain(current);
						Knowledge next = dao.getTheMostKnowledge();
						if (null == next || next._id == current._id) {
							ToastUtil.toast(R.string.no_more_knowledge);
						} else {
							onKnowledgeChange(dao.getTheMostKnowledge());
						}
					}
				});
	}

	private void initViews() {
		Knowledge knowledge = AssistantDAO.getInstance().getTheMostKnowledge();
		if (knowledge == null) {
			show.setVisibility(View.GONE);
			return;
		}
		no_knowledge.setVisibility(View.GONE);
		onKnowledgeChange(knowledge);
	}

	protected void onKnowledgeChange(Knowledge knowledge) {
		answer.setText(R.string.click_check_answer);
		question.setText(knowledge.question);
		show.setTag(knowledge);
		answer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Logger.i(tag, "click to view answer");
				answer.setText(((Knowledge) show.getTag()).answer);
				answer.setOnClickListener(null);
			}
		});
	}

	private void setToHome(View v) {
		v.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				toHome();
			}
		});
	}

	private void toHome() {
		startActivity(new Intent(this, CategoryActivity.class));
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void testparse() {
		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/json";
		Logger.i(tag, "path is " + path);
		Parser.parseAll(FileUtil.readFile(path));
	}

}
