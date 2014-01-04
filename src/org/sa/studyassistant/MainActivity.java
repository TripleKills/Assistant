package org.sa.studyassistant;

import org.sa.studyassistant.adapter.QuestionsAdapter;
import org.sa.studyassistant.db.AssistantDAO;
import org.sa.studyassistant.model.Knowledge;
import org.sa.studyassistant.util.SessionManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	private ListAdapter question_adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.questions_aty);
		startActivity(new Intent(this, CategoryActivity.class));
		ListView list = (ListView) findViewById(R.id.questions_aty_list);
		question_adapter = new QuestionsAdapter(AssistantDAO.getInstance().findDefaultKnowledegs(), this);
		//testAddKnowledges();
		list.setAdapter(question_adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Knowledge question = (Knowledge) arg0.getAdapter().getItem(arg2);
				SessionManager.getInstance().put("answer", question);
				startActivity(new Intent(MainActivity.this, AnswerActivity.class));
			}
		});
		
		findViewById(R.id.questions_aty_add).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(v.getContext(), KnowledgeActivity1.class));
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
