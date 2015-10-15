package library;

public class TimeCounter {
	private long allElapsedTime;
	private long elapsedTime;

	/**************************************************
	 * コンストラクタ
	 **************************************************/
	public TimeCounter(){
		allElapsedTime = System.currentTimeMillis();
		elapsedTime = System.currentTimeMillis();
	}

	/**************************************************
	 * インスタンスを生成してからの経過時間を返す。
	 * @return TimeCounterのインスタンスを生成してからの累計時間。
	 **************************************************/
	public long getAllElapsedTime(){
		return System.currentTimeMillis() - allElapsedTime;
	}

	/**************************************************
	 * 以前にこのメソッドを呼び出していれば、そこからの経過時間。
	 * 呼び出していなければTimeCounterのインスタンスを生成してからの経過時間を返す。
	 * @return 経過時間
	 **************************************************/
	public long getElapsedTime(){
		long time = System.currentTimeMillis() - elapsedTime;
		elapsedTime = System.currentTimeMillis();
		return time;
	}

	/**************************************************
	 * インスタンスを生成してからの経過時間を、文字列にして返す。
	 * @return TimeCounterのインスタンスを生成してからの累計時間の文字列。
	 **************************************************/
	public String getAllElapsedTimeString(){
		return TimeToString(getAllElapsedTime());
	}

	/**************************************************
	 * 以前にこのメソッドを呼び出していれば、そこからの経過時間、
	 * 呼び出していなければTimeCounterのインスタンスを生成してからの経過時間を
	 * 文字列にして返す。
	 * @return 経過時間の文字列。
	 **************************************************/
	public String getElapsedTimeString(){
		return TimeToString(getElapsedTime());
	}

	private String TimeToString(long time){
		String timeStr;

		//ミリ秒
		timeStr = String.valueOf(time % 1000) + "ms";
		//秒
		if(time >= 1000){
			time /= 1000;
			timeStr = String.valueOf(time % 60) + "s "+ timeStr;
		}else{
			return timeStr;
		}
		//分
		if(time >= 60){
			time /= 60;
			timeStr = String.valueOf(time % 60) + "m " + timeStr;
		}else{
			return timeStr;
		}
		//時間
		if(time >= 60){
			time /= 60;
			timeStr = String.valueOf(time) + "h " + timeStr;
		}

		return timeStr;
	}
}
