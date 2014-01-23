package org.sa.studyassistant;

import org.sa.studyassistant.adapter.KnowledgeAdapter;
import org.sa.studyassistant.db.AssistantDAO;
import org.sa.studyassistant.model.Knowledge;
import org.sa.studyassistant.util.FileUtil;
import org.sa.studyassistant.util.Logger;
import org.sa.studyassistant.util.Parser;
import org.sa.studyassistant.util.SessionManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	private ListAdapter question_adapter;
	private static final String tag = MainActivity.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.questions_aty);
		startActivity(new Intent(this, CategoryActivity.class));
		ListView list = (ListView) findViewById(R.id.questions_aty_list);
		question_adapter = new KnowledgeAdapter(AssistantDAO.getInstance()
				.findDefaultKnowledegs(), this);
		// testAddKnowledges();
		list.setAdapter(question_adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Knowledge question = (Knowledge) arg0.getAdapter()
						.getItem(arg2);
				SessionManager.getInstance().put("answer", question);
				startActivity(new Intent(MainActivity.this,
						AnswerActivity.class));
				finish();
			}
		});

		findViewById(R.id.questions_aty_add).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						startActivity(new Intent(v.getContext(),
								KnowledgeEditActivity.class));
					}
				});
		//testparse();
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
