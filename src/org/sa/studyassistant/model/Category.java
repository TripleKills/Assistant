package org.sa.studyassistant.model;

import java.io.Serializable;

public class Category implements Serializable{
	private static final long serialVersionUID = 1L;
	public int category_id;
	public String name = null;
	public int belong_to = -1;
}
