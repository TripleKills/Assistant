package org.sa.studyassistant.model;

import java.io.Serializable;

public class Knowledge implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2926131965682055923L;
	public String question;
	public String answer;
	public Category category;
	public long create_time = System.currentTimeMillis();
}
