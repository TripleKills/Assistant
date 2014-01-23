package org.sa.studyassistant.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

public class Parser {
	private static final String tag = Parser.class.getName();

	public static void parseAll(String str) {
		if (TextUtils.isEmpty(str))
			return;
		try {
			JSONArray arr = new JSONArray(str);
			for (int i = 0; i < arr.length(); i++) {
				JSONObject chapter = arr.getJSONObject(i);
				String ch_name = chapter.getString("name");
				Logger.i(tag, "ch_name: \n" + ch_name);
				JSONArray qas = chapter.getJSONArray("qas");
				for (int j = 0; j < qas.length(); j++) {
					JSONObject qa = qas.getJSONObject(j);
					String q = qa.getString("question");
					String a = qa.getString("answer");
					Logger.i(tag, "question: \n" + q);
					Logger.i(tag, "answer: \n" + a);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}