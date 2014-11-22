package cn.why.customviewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class CustomViewPager extends ViewGroup{

	private GestureDetector gestureDetector;
	private Context context;
	private CustomScoller scoller;
	
	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
	}

	private void initView() {
			scoller = new CustomScoller(context);
			gestureDetector = new GestureDetector(context, new OnGestureListener() {
				@Override
				public boolean onSingleTapUp(MotionEvent arg0) {
					return false;
				}
				
				@Override
				public void onShowPress(MotionEvent arg0) {
				}
				
				@Override
				public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
						float distanceY) {
					scrollBy((int) distanceX, 0);
					return false;
				}
				
				@Override
				public void onLongPress(MotionEvent arg0) {
				}
				
				@Override
				public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
						float arg3) {
					return false;
				}
				
				@Override
				public boolean onDown(MotionEvent arg0) {
					return false;
				}
			});
	}

	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		for (int i = 0; i < getChildCount(); i++) {
			View  view  = getChildAt(i);
			view.layout(0+i*getWidth(), 0, getWidth()+i*getWidth(), getHeight());
		}
	}

	private int currentViewId = 0;
	private float startX;
	private int nextId = 0;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		gestureDetector.onTouchEvent(event);
		
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				startX = event.getX();
				break;
			case MotionEvent.ACTION_MOVE:
				
				break;
			case MotionEvent.ACTION_UP:
				if (event.getX() - startX > getWidth()/2) {
					nextId = currentViewId - 1;
				}else if (startX - event.getX() > getWidth()/2) {
					nextId = currentViewId + 1;
				}else {
					nextId = currentViewId;
				} 
				nextId = (nextId >=0)?nextId:0;//nextId不可小于0
				currentViewId = (nextId >= 0)?nextId:0;
				currentViewId = (nextId <= getChildCount() - 1)?nextId:(getChildCount() - 1);
				moveToNext(nextId);
				break;
			}
		
		return true;
	}
	private void moveToNext(int nextId) {
		int distance = currentViewId*getWidth() - getScrollX();//移动的距离 = 最终位置 - 开始位置
		scoller.startScroll(getScrollX(), 0, distance, 0);
		invalidate();
	}
	/**
	 * 每次执行invalidate方法，都会调用computeScroll方法
	 */
	@Override
	public void computeScroll() {
		if (scoller.computeScollOffset()) {
			int offset = (int) scoller.getCurrentX();
			scrollTo(offset, 0);
			invalidate();
		}
	}
}
