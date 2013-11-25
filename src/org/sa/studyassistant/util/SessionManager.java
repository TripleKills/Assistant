package org.sa.studyassistant.util;

import java.util.HashMap;
import java.util.Map;

/**
 * SessionManager保存有Session对象，对于数据的存储操作会直接转发给 Session,
 * 而SessionManager则要负责一些初始化的工作 未安装应用：uninstalled_apps 下载队列管理器：load_sequence
 * 应用列表:app_list 全局下载监听器:global_down 已安装应用：installed_apps
 */

public class SessionManager {

	private static SessionManager _inst;
	private Session session;
	private static final String TAG = SessionManager.class.toString();

	private SessionManager() {
		session = new Session();
		Logger.i(TAG, "new session manager");
	}

	public void clearSession() {
		Logger.i(TAG, "clear session manager");
		_inst = null;
	}

	public static SessionManager getInstance() {
		return _inst == null ? _inst = new SessionManager() : _inst;
	}

	public void put(String key, Object value) {
		session.put(key, value);
	}

	public Object get(String key) {
		return session.get(key);
	}

	public Object remove(String key) {
		return session.remove(key);
	}

	/**
	 * session类仅仅用来保存数据，是属于SessionManager的私有类 不对外公开
	 * 
	 */
	private static class Session {
		private Map<String, Object> _data;

		public Session() {
			_data = new HashMap<String, Object>();
		}

		public Object remove(String key) {
			Object obj = get(key);
			_data.remove(key);
			return obj;
		}

		public void put(String key, Object value) {
			if (value == null || key == null) {
				return;
			}
			_data.put(key, value);
		}

		public Object get(String key) {
			if (key == null) {
				return null;
			}
			return _data.get(key);
		}
	}

}
