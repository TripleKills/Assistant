package org.sa.studyassistant.util;

import java.io.FileInputStream;
import java.io.IOException;

import android.text.TextUtils;

public class FileUtil {
	public static String readFile(String path) {
		if(TextUtils.isEmpty(path)) return null;
		FileInputStream fis = null;
		StringBuffer sb = new StringBuffer();
		try {
			fis = new FileInputStream(path);
			byte[] buffer = new byte[1024];
			int read = -1;
			while((read = fis.read(buffer)) != -1) {
				sb.append(new String(buffer,0,read));	
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != fis)
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return sb.toString();
	}
}
