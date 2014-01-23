package org.sa.studyassistant.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sa.studyassistant.db.AssistantDAO;
import org.sa.studyassistant.model.Category;
import org.sa.studyassistant.model.Knowledge;

import android.text.TextUtils;

public class Parser {
	private static final String tag = Parser.class.getName();

	public static void parseAll(String str) {
		if (TextUtils.isEmpty(str))
			return;
		try {
			JSONObject obj = new JSONObject(str);
			String book_name = obj.getString("name");
			Category book = createCategory(book_name);
			JSONArray arr = obj.getJSONArray("chapter");
			for (int i = 0; i < arr.length(); i++) {
				JSONObject chapter = arr.getJSONObject(i);
				String ch_name = chapter.getString("name");
				Logger.i(tag, "ch_name: \n" + ch_name);
				Category category = createCategory(ch_name, book);
				JSONArray qas = chapter.getJSONArray("qas");
				for (int j = 0; j < qas.length(); j++) {
					JSONObject qa = qas.getJSONObject(j);
					String q = qa.getString("question");
					String a = qa.getString("answer");
					Logger.i(tag, "question: \n" + q);
					Logger.i(tag, "answer: \n" + a);
					createKnowledge(category, q, a);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private static void createKnowledge(Category category, String q, String a) {
		Knowledge kld = new Knowledge();
		kld.answer = a;
		kld.question = q;
		kld.category = category;
		AssistantDAO.getInstance().insertKnowledge(kld);
	}
	
	private static Category createCategory(String name) {
		return createCategory(name, null);
	}
	
	private static Category createCategory(String name, Category belong_to) {
		Category category = new Category();
		category.name = name;
		if (null != belong_to)
			category.belong_to = belong_to.category_id;
		AssistantDAO.getInstance().insertCategory(category);
		return category;
	}
	
	
}
