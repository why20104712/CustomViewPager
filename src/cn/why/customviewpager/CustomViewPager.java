package cn.why.customviewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class CustomViewPager extends ViewGroup {

	private GestureDetector gestureDetector;
	private Context context;
	// private CustomScoller scoller;
	private Scroller scoller;
	private boolean isFling;
	private int currentViewId = 0;
	private float startX;
	private int nextId = 0;

	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
	}

	private void initView() {
		// scoller = new CustomScoller(context);
		scoller = new Scroller(context);
		gestureDetector = new GestureDetector(context, new OnGestureListener() {
			@Override
			public boolean onSingleTapUp(MotionEvent arg0) {
				return false;
			}

			@Override
			public void onShowPress(MotionEvent arg0) {
			}

			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2,
					float distanceX, float distanceY) {
				scrollBy((int) distanceX, 0);
				return false;
			}

			public void onLongPress(MotionEvent arg0) {
			}

			/**
			 * 快速滑动事件
			 */
			public boolean onFling(MotionEvent arg0, MotionEvent arg1,
					float velocityX, float velocityY) {
				isFling = true;
				if (velocityX > 0 && currentViewId > 0) { // 快速向右滑动
					currentViewId--;
				} else if (velocityX < 0 && currentViewId < getChildCount() - 1) { // 快速向左滑动
					currentViewId++;
				}
				moveToDest(currentViewId);
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
			View view = getChildAt(i);
			view.layout(0 + i * getWidth(), 0, getWidth() + i * getWidth(),
					getHeight());
		}
	}

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
				if (event.getX() - startX > getWidth() / 2) {
					nextId = currentViewId - 1;
				} else if (startX - event.getX() > getWidth() / 2) {
					nextId = currentViewId + 1;
				} else {
					nextId = currentViewId;
				}
				moveToDest(nextId);
			}
			isFling = false;
			break;
		}

		return true;
	}

	/**
	 * 移动到指定页
	 * 
	 * @param nextId
	 */
	public void moveToDest(int nextId) {
		nextId = (nextId >= 0) ? nextId : 0;// nextId不可小于0
		currentViewId = (nextId >= 0) ? nextId : 0;
		currentViewId = (nextId <= getChildCount() - 1) ? nextId
				: (getChildCount() - 1);
		int distance = currentViewId * getWidth() - getScrollX();// 移动的距离 = 最终位置
																	// - 开始位置
		scoller.startScroll(getScrollX(), 0, distance, 0);
		// 触发listener事件
		if (pageChangedListener != null) {
			pageChangedListener.moveToDest(nextId);
		}
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

	private CustomPageChangedListener pageChangedListener;
	private float startY;

	public CustomPageChangedListener getPageChangedListener() {
		return pageChangedListener;
	}

	public void setPageChangedListener(
			CustomPageChangedListener pageChangedListener) {
		this.pageChangedListener = pageChangedListener;
	}

	/**
	 * 自定义页面变化事件,监听接口
	 * 
	 * @author why
	 * 
	 */
	public interface CustomPageChangedListener {
		public void moveToDest(int currId);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		for (int i = 0; i < getChildCount(); i++) {
			View view = getChildAt(i);
			view.measure(widthMeasureSpec, heightMeasureSpec);
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		boolean result = false;
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			gestureDetector.onTouchEvent(ev);
			startX = ev.getX();
			startY = ev.getY();
			break;

		case MotionEvent.ACTION_MOVE:
			float distanceX = Math.abs(ev.getX() - startX);
			float distanceY = Math.abs(ev.getY() - startY);
			
			if (distanceX > distanceY && distanceX > 10) {
				result = true;
			}else if (distanceX < distanceY && distanceY > 10 ) {
				result = false;
			}
			break;
		case MotionEvent.ACTION_UP:

			break;
		}
		return result;
	}

}
