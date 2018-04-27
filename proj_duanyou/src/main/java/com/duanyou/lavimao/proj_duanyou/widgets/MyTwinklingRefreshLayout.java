package com.duanyou.lavimao.proj_duanyou.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

/**
 * Created by luojialun on 2018/4/26.
 */

public class MyTwinklingRefreshLayout extends TwinklingRefreshLayout {

    private boolean enable;//是否生效

    public MyTwinklingRefreshLayout(Context context) {
        this(context, null, 0);
    }

    public MyTwinklingRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTwinklingRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLoadEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                return enable;

        }

        return super.dispatchTouchEvent(ev);


    }
}
