package org.sa.studyassistant.db;

import java.util.ArrayList;
import java.util.List;

import org.sa.studyassistant.model.Answer;
import org.sa.studyassistant.model.Category;
import org.sa.studyassistant.model.Question;
import org.sa.studyassistant.util.Logger;

import android.content.Context;
import android.database.Cursor;

public class SaveDAO extends DBObserver {
	private SaveDB db;
	private static final String tag = SaveDAO.class.getName();

	public SaveDAO(Context context) {
		db = new SaveDB(context);
		addDefaultCategory();
	}

	/**
	 * add default category, all notes without category belong to this category
	 * 
	 * @return
	 */
	private long addDefaultCategory() {
		long result = db.findCategoryIdWithInsert("default");
		String msg = "add default category, result is " + result;
		Logger.i(tag, msg);
		return result;
	}

	public long insertAnswer(String answer) {
		return db.insertAnswer(answer);
	}

	public long insertCategory(String category) {
		return db.findCategoryIdWithInsert(category);
	}

	public long insertQuestion(String text, long answer_id, long category_id) {
		return db.insertQuestion(answer_id, category_id, text,
				System.currentTimeMillis());
	}

	public List<Category> findAllCategory() {
		List<Category> categories = new ArrayList<Category>();
		Cursor c = db.findAllCategory();
		if (null == c)
			return categories;
		try {
			while (c.moveToNext()) {
				Category category = recreateCategory(c);
				categories.add(category);
			}
		} finally {
			c.close();
		}
		return categories;
	}

	private Category recreateCategory(Cursor c) {
		Category category = new Category();
		category.name = c.getString(c.getColumnIndex(DBMetaData.CATEGORY_TEXT));
		return category;
	}

	public List<Question> findQuestionByCategory(Category category) {
		List<Question> questions = new ArrayList<Question>();
		Cursor c = db.findQuestionsByCategory(db.findCategoryId(category.name));
		if (null == c)
			return questions;
		try {
			while (c.moveToNext()) {
				Question question = recreateQuestion(c);
				questions.add(question);
			}
		} finally {
			c.close();
		}
		return questions;
	}

	private Question recreateQuestion(Cursor c) {
		Question question = new Question();
		question.text = c.getString(c.getColumnIndex(DBMetaData.QUESTION_TEXT));
		question.answer_id = c.getInt(c
				.getColumnIndex(DBMetaData.QUESTION_ANSWER_ID));
		question.create_time = c.getLong(c
				.getColumnIndex(DBMetaData.QUESTION_CREATE_TIME));
		return question;
	}

	public Answer findAnswerById(long answer_id) {
		Cursor c = db.findAnswerById(answer_id);
		if (null == c)
			return null;
		try {
			if (c.moveToFirst()) {
				Answer answer = recreateAnswer(c);
				return answer;
			}
		} finally {
			c.close();
		}
		return null;
	}

	private Answer recreateAnswer(Cursor c) {
		Answer answer = new Answer();
		answer.text = c.getString(c.getColumnIndex(DBMetaData.ANSWER_TEXT));
		return answer;
	}
}
