package org.sa.studyassistant.model;

public class Knowledge {
	public String question;
	public String answer;
	public Category category;
	public long create_time = System.currentTimeMillis();
}
