package cn.why.customviewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class CustomViewPager extends ViewGroup{

	private GestureDetector gestureDetector;
	private Context context;
//	private CustomScoller scoller;
	private Scroller scoller;
	private boolean isFling;
	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
	}

	private void initView() {
//			scoller = new CustomScoller(context);
			scoller = new Scroller(context);
			gestureDetector = new GestureDetector(context, new OnGestureListener() {
				@Override
				public boolean onSingleTapUp(MotionEvent arg0) {
					return false;
				}
				@Override
				public void onShowPress(MotionEvent arg0) {}
				@Override
				public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
						float distanceY) {
					scrollBy((int) distanceX, 0);
					return false;
				}
				public void onLongPress(MotionEvent arg0) {}
				/**
				 * 快速滑动事件
				 */
				public boolean onFling(MotionEvent arg0, MotionEvent arg1, float velocityX,	float velocityY) {
					isFling = true;
					if(velocityX>0 && currentViewId>0){ // 快速向右滑动
						currentViewId--;
					}else if(velocityX<0 && currentViewId<getChildCount()-1){ // 快速向左滑动
						currentViewId++;
					}
					moveToNext(currentViewId);
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
				if (!isFling) {
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
				}
				isFling = false;
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
		if (scoller.computeScrollOffset()) {
			int offset = (int) scoller.getCurrX();
			scrollTo(offset, 0);
			invalidate();
		}
	}
}
