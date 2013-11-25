package org.sa.studyassistant.model;

public class Knowledge {
	private Answer answer;
	private Question question;
	private Category category;

	private long answer_id;
	private long question_id;
	private long category_id;

	public Knowledge(Answer answer, Question question, Category category) {
		super();
		this.answer = answer;
		this.question = question;
		this.category = category;
	}

	public Answer getAnswer() {
		return answer;
	}

	public Question getQuestion() {
		return question;
	}

	public Category getCategory() {
		return category;
	}

	public long getAnswer_id() {
		return answer_id;
	}

	public void setAnswer_id(long answer_id) {
		this.answer_id = answer_id;
	}

	public long getQuestion_id() {
		return question_id;
	}

	public void setQuestion_id(long question_id) {
		this.question_id = question_id;
	}

	public long getCategory_id() {
		return category_id;
	}

	public void setCategory_id(long category_id) {
		this.category_id = category_id;
	}

}
