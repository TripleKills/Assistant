package org.sa.studyassistant.util;

public class StringUtil {
	public static String removeTailLine(String q) {
		int len = "\n".length();
		if (null != q && q.endsWith("\n") && q.length() > len) {
			q = q.substring(0, q.length() - len);
		}
		return q;
	}
}
