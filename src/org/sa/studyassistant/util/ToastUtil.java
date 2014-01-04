package org.sa.studyassistant.util;

import org.sa.studyassistant.AssistantApplication;

import android.widget.Toast;

public class ToastUtil {
	public static void toast(int resId) {
		Toast.makeText(AssistantApplication.APPLICATION, resId,
				Toast.LENGTH_LONG).show();
	}
}
