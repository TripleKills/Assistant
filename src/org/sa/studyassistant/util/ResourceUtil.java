package org.sa.studyassistant.util;

import org.sa.studyassistant.AssistantApplication;

public class ResourceUtil {
	public static String getString(int id) {
		return AssistantApplication.APPLICATION.getString(id);
	}
}
