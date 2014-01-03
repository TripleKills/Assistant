package org.sa.studyassistant;

import org.sa.studyassistant.db.AssistantDAO;
import org.sa.studyassistant.model.Category;
import org.sa.studyassistant.model.Knowledge;
import org.sa.studyassistant.util.Logger;
import org.sa.studyassistant.util.SessionManager;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class KnowledgeActivity extends Activity {
	private static final String tag = KnowledgeActivity.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_add_knowledge);
		final Category category = (Category) SessionManager.getInstance()
				.remove("category");
		findViewById(R.id.aty_add_knowledge_cancel).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});

		findViewById(R.id.aty_add_knowledge_save).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						String title = ((EditText) findViewById(R.id.aty_add_knowledge_title))
								.getText().toString();
						String text = ((EditText) findViewById(R.id.aty_add_knowledge_text))
								.getText().toString();
						if (TextUtils.isEmpty(title) && TextUtils.isEmpty(text)) {
							finish();
							return;
						}
						title = TextUtils.isEmpty(title) ? ((EditText) findViewById(R.id.aty_add_knowledge_title))
								.getHint().toString() : title;
						Knowledge knowledge = new Knowledge();
						knowledge.answer = text;
						knowledge.question = title;
						Logger.i(tag, String.format("question: %s, answer: %s",
								new Object[] { text, title }));
						AssistantDAO.getInstance().insertKnowledge(knowledge);
						finish();
					}
				});
	}
}
