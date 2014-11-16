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
	
	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
	}

	private void initView() {
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

	/**
	 * ���ò���λ��
	 */
	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		for (int i = 0; i < getChildCount(); i++) {
			View  view  = getChildAt(i);//����Ӳ���
			view.layout(0+i*getWidth(), 0, getWidth()+i*getWidth(), getHeight());//�����Ӳ��ֵ�λ��
		}
	}

	private int currentViewId = 0;//��ǰ��ͼview��id
	private float startX;
	
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
				int nextId = 0;
				if (event.getX() - startX > getWidth()/2) {
					nextId = currentViewId - 1;
				}else if (startX - event.getX() > getWidth()/2) {
					nextId = currentViewId + 1;
				}else {
					nextId = currentViewId;
				} 
				moveToNext(nextId);
				break;
			}
		
		return true;
	}
	/**
	 * �ƶ�ͼƬ����һ��view
	 * @param nextId
	 */
	private void moveToNext(int nextId) {
		//�ж�λ�õĺ�����
		currentViewId = (nextId >= 0)?nextId:0;
		currentViewId = (nextId <= getChildCount() - 1)?nextId:(getChildCount() - 1);
		
		scrollTo(currentViewId*getWidth(), 0);
	}
}
