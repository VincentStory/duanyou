package com.duanyou.lavimao.proj_duanyou.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;

/**
 * Created by luojialun on 2018/4/27.
 */

public class MyViewPager extends VerticalViewPager {

    private float beforeY;

    public MyViewPager(Context context) {
        this(context, null);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN://按下如果‘仅’作为‘上次坐标’，不妥，因为可能存在左滑，motionValue大于0的情况（来回滑，只要停止坐标在按下坐标的右边，左滑仍然能滑过去）
                beforeY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float motionValue = ev.getY() - beforeY;
                Log.i("TAG", "motionValue-->" + motionValue);
                if (motionValue > 1 && getCurrentItem() == 0) {
                    if (null != upSlideListener) {
                        upSlideListener.upSlide();
                    }
                    return true;
                }
                beforeY = ev.getY();
                break;

        }
        return super.dispatchTouchEvent(ev);
    }

    public interface UpSlideListener {
        void upSlide();
    }

    public UpSlideListener upSlideListener;

    public UpSlideListener getUpSlideListener() {
        return upSlideListener;
    }

    public void setUpSlideListener(UpSlideListener upSlideListener) {
        this.upSlideListener = upSlideListener;
    }
}
