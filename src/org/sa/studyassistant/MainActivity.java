package org.sa.studyassistant;

import java.util.ArrayList;
import java.util.List;

import org.sa.studyassistant.adapter.QuestionsAdapter;
import org.sa.studyassistant.db.AssistantDAO;
import org.sa.studyassistant.model.Answer;
import org.sa.studyassistant.model.Knowledge;
import org.sa.studyassistant.model.Question;
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
		
		ListView list = (ListView) findViewById(R.id.questions_aty_list);
		question_adapter = new QuestionsAdapter(AssistantDAO.getInstance().findDefaultQuestion(), this);
		//testAddKnowledges();
		list.setAdapter(question_adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Question question = (Question) arg0.getAdapter().getItem(arg2);
				Answer answer = AssistantDAO.getInstance().findAnswer(question);
				SessionManager.getInstance().put("answer", answer);
				startActivity(new Intent(MainActivity.this, AnswerActivity.class));
			}
		});
		
		findViewById(R.id.questions_aty_add).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(v.getContext(), KnowledgeActivity.class));
			}
		});
	}
	
	private List<Question> testQuestions() {
		List<Question> questions = new ArrayList<Question>();
		for (int i = 0; i < 50; i++) {
			Question question = new Question();
			question.text = "question " + i;
			questions.add(question);
		}
		return questions;
	}
	
	private void testAddKnowledges() {
		for (int i = 0; i < 50; i++) {
			Question question = new Question();
			question.text = "question " + i;
			Answer ans = new Answer();
			ans.text = "answer " + i;
			Knowledge kn = new Knowledge(ans, question, null);
			AssistantDAO.getInstance().insertKnowledge(kn);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
