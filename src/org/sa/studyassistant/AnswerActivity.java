package org.sa.studyassistant;

import org.sa.studyassistant.model.Question;
import org.sa.studyassistant.util.SessionManager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AnswerActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.answer_aty);
		
		TextView text = (TextView) findViewById(R.id.answer_aty_text);
		Question question = (Question) SessionManager.getInstance().remove("question");
		if (null != question) text.setText(question.text);
	}
}
