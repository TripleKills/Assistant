package org.sa.studyassistant;

import org.sa.studyassistant.model.Answer;
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
		Answer answer = (Answer) SessionManager.getInstance().remove("answer");
		if (null != answer) text.setText(answer.text);
	}
}
