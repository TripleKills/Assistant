package org.sa.studyassistant;

import org.sa.studyassistant.model.Knowledge;
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
		Knowledge answer = (Knowledge) SessionManager.getInstance().remove(
				"answer");
		if (null != answer)
			text.setText(answer.answer);
	}
}
