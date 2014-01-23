package org.sa.studyassistant.util;

public class Ebbinghaus {
	private static final long MINUTE = 60 * 1000;
	private static final long HOUR = 60 * MINUTE;
	private static final long DAY = 24 * HOUR;
	private static final long[] CURVE = { 5 * MINUTE, 30 * MINUTE, 12 * HOUR,
			1 * DAY, 2 * DAY, 4 * DAY, 7 * DAY, 15 * DAY };

	public static int getPhase(long create_time) {
		long time_pass = System.currentTimeMillis() - create_time;
		for (int i = 0; i < CURVE.length; i++) {
			if (time_pass < CURVE[i])
				return i;
		}
		return CURVE.length;
	}
	
	public static long getMaxDelta() {
		return CURVE[CURVE.length - 1];
	}
	
	/**
	 * 获取到下一个阶段还有多长时间
	 * @param create_time
	 * @param last_time
	 * @return
	 */
	public static long getNextPhaseDelta(long create_time, long last_time) {
		return getNextPhaseTime(create_time) - last_time;
	}
	
	/**
	 * 获取下一个阶段的时间点
	 * @param create_time
	 * @return
	 */
	private static long getNextPhaseTime(long create_time) {
		int phase = getPhase(create_time);
		if (phase == CURVE.length) {
			return System.currentTimeMillis();
		} else {
			return create_time + CURVE[phase];
		}
	}
	
	/**
	 * 获取没有超出记忆曲线的最远的时间
	 * @return
	 */
	public static long getTimePassPoint() {
		return System.currentTimeMillis() - CURVE[CURVE.length - 1];
	}
	

}
