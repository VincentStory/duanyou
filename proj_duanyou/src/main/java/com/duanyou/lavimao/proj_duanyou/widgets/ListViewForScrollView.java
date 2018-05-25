package com.duanyou.lavimao.proj_duanyou.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 解决ScrollView嵌套ListView冲突问题的ListView
 * 
 * @author Administrator
 *
 */
public class ListViewForScrollView extends ListView {
	private int downX;
	private int downY;
	private int moveX;
	private int moveY;

	public ListViewForScrollView(Context context) {
		super(context);
	}

	public ListViewForScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public ListViewForScrollView(Context context, AttributeSet attrs,
                                 int defStyle) {
		super(context, attrs, defStyle);
	}

	// @Override
	/**
	 * 重写该方法，达到使ListView适应ScrollView的效果
	 */
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

	/*@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		getParent().requestDisallowInterceptTouchEvent(true);
		// TODO Auto-generated method stub
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 让listView对应的父控件不要拦截事件
			getParent().requestDisallowInterceptTouchEvent(true);
			downX = (int) ev.getX();
			downY = (int) ev.getY();
			break;
 
		case MotionEvent.ACTION_MOVE:
			moveX = (int) ev.getX();
			moveY = (int) ev.getY();
			if (Math.abs(downX - moveX) < (Math.abs(downY - moveY))) {
				// 在y轴上移动，要做的动作是加载
				// 让listView对应的父控件不要拦截事件
				getParent().requestDisallowInterceptTouchEvent(true);
			} else {
				// 在x轴上移动，
				// getParent().requestDisallowInterceptTouchEvent(false);
				getParent().requestDisallowInterceptTouchEvent(true);
			}
			break;
		case MotionEvent.ACTION_UP:
			getParent().requestDisallowInterceptTouchEvent(true);
			break;
		}
		return super.dispatchTouchEvent(ev);
	}*/

}
