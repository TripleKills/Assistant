package org.sa.studyassistant;

import org.sa.studyassistant.db.AssistantDAO;
import org.sa.studyassistant.model.Category;
import org.sa.studyassistant.model.Knowledge;
import org.sa.studyassistant.util.Logger;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class KnowledgeEditActivity extends Activity {
	private static final String tag = KnowledgeEditActivity.class.getName();
	private Category category;
	private Knowledge knowledge;
	private EditText title, text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_add_knowledge);
		title = ((EditText) findViewById(R.id.aty_add_knowledge_title));
		text = ((EditText) findViewById(R.id.aty_add_knowledge_text));
		knowledge = (Knowledge) getIntent().getSerializableExtra("knowledge");
		if (null == knowledge) {
			category = (Category) getIntent().getSerializableExtra("category");
			findViewById(R.id.aty_add_knowledge_edit).setVisibility(View.GONE);
		} else {
			category = knowledge.category;
			title.setText(knowledge.question);
			text.setText(knowledge.answer);
			title.setEnabled(false);
			text.setEnabled(false);
			findViewById(R.id.aty_add_knowledge_save).setEnabled(false);
			OnClickListener lsnr = new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					title.setEnabled(true);
					text.setEnabled(true);
					findViewById(R.id.aty_add_knowledge_save).setEnabled(true);
					findViewById(R.id.aty_add_knowledge_edit).setVisibility(View.GONE);
				}
			};
			findViewById(R.id.aty_add_knowledge_edit).setOnClickListener(lsnr);
		}
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
						String title_str = title.getText().toString();
						String text_str = text.getText().toString();
						if (TextUtils.isEmpty(title_str) && TextUtils.isEmpty(text_str)) {
							finish();
							return;
						}
						text_str = TextUtils.isEmpty(title_str) ? title.getHint().toString() : title_str;
						Knowledge m_knowledge = null == knowledge ? new Knowledge() : knowledge;
						m_knowledge.answer = text_str;
						m_knowledge.question = title_str;
						m_knowledge.category = category;
						Logger.i(tag, String.format("question: %s, answer: %s",
								new Object[] { text, title }));
						AssistantDAO.getInstance().insertKnowledge(m_knowledge);
						finish();
					}
				});
	}
}
