package org.sa.studyassistant.db;

import java.util.List;

import org.sa.studyassistant.model.Answer;
import org.sa.studyassistant.model.Category;
import org.sa.studyassistant.model.Knowledge;
import org.sa.studyassistant.model.Question;

import android.content.Context;

public class AssistantDAO extends DBObserver {
	private static AssistantDAO _inst;
	private TraceDAO trace;
	private SaveDAO save;

	private AssistantDAO() {
	}

	public static AssistantDAO getInstance() {
		if (null == _inst)
			_inst = new AssistantDAO();
		return _inst;
	}

	public void init(Context context) {
		if (null != save)
			return;
		save = new SaveDAO(context);
		save.regist(this);
		trace = new TraceDAO(context);
		trace.regist(this);
	}

	public boolean insertKnowledge(Knowledge knowledge) {
		long answer_id = save.insertAnswer(knowledge.getAnswer().text);
		if (answer_id == -1)
			return false;
		String category = null != knowledge.getCategory() ? knowledge.getCategory().name : "default";
		long category_id = save.insertCategory(category);
		if (category_id == -1)
			return false;
		long question_id = save.insertQuestion(knowledge.getQuestion().text,
				answer_id, category_id);
		if (question_id == -1)
			return false;
		knowledge.setAnswer_id(answer_id);
		knowledge.setCategory_id(category_id);
		knowledge.setQuestion_id(question_id);
		return true;
	}

	public List<Category> findAllCategory() {
		return save.findAllCategory();
	}
	
	public List<Question> findDefaultQuestion() {
		return findQuestionByCategory("default");
	}

	public List<Question> findQuestionByCategory(String name) {
		Category category = new Category();
		category.name = name;
		return save.findQuestionByCategory(category);
	}

	public List<Question> findQuestionByCategory(Category category) {
		return save.findQuestionByCategory(category);
	}

	public Answer findAnswerById(long answer_id) {
		return save.findAnswerById(answer_id);
	}

	public Answer findAnswer(Question question) {
		return save.findAnswerById(question.answer_id);
	}

}
