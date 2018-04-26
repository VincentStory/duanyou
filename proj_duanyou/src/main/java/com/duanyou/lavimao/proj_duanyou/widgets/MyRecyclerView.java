package com.duanyou.lavimao.proj_duanyou.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by luojialun on 2018/4/26.
 */

public class MyRecyclerView extends RecyclerView {
    public MyRecyclerView(Context context) {
        this(context, null, 0);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

   /* @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_UP:

                break;
        }
        return super.onTouchEvent(e);
    }*/


}
