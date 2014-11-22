package cn.why.customviewpager;

import android.content.Context;
import android.os.SystemClock;

public class CustomScoller {

	private int startX;
	private int startY;
	private int distanceX;
	private int distanceY;
	private boolean isFinish;
	private Context context;
	private int duration = 500;//单位时毫秒
	private long startTime;
	private long currX;
	private long currY;
	
	public CustomScoller(Context context) {
		this.context = context;
	}

	/**
	 * 计算偏移，即该移动到哪个位置了
	 */
	public boolean computeScrollOffset() {
		// 获得所用的时间
		long passTIme = SystemClock.uptimeMillis() - startTime;
		if (isFinish) {
			return false;
		}
		if (passTIme <= duration) {
			currX = startX + distanceX*passTIme/duration;
			currY = startY + distanceY*passTIme/duration;
		}else {
			currX = startX + distanceX;
			currY = startY + distanceY;
			isFinish = true;
		}
		return true;
	}

	/**
	 * 开始移动
	 * @param scrollX
	 * @param distance
	 * @param i
	 */
	public void startScroll(int startX, int startY, int distanceX, int distanceY) {
		this.startX = startX;
		this.startY = startY;
		this.distanceX = distanceX;
		this.distanceY = distanceY;
		this.startTime = SystemClock.uptimeMillis();
		this.isFinish = false;
	}

	public long getcurrX() {
		return currX;
	}

	public void setcurrX(long currX) {
		this.currX = currX;
	}

	
	
}
