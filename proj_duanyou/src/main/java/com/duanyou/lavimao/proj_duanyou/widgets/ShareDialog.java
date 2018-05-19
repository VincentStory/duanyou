package com.duanyou.lavimao.proj_duanyou.widgets;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.duanyou.lavimao.proj_duanyou.R;

/**
 * Created by vincent on 2018/4/29.
 */

public class ShareDialog implements View.OnClickListener {
    private final Display display;
    private Context context;

    private Dialog dialog;
    private Window dialogWindow;
    private TextView sinaTv;
    private TextView qqTv;
    private TextView circleTv;
    private TextView weichatTv;
    private TextView qqspaceTv;
    private TextView copyTv;
    private TextView collectionTv;
    private TextView reportTv;
    private TextView saveTv;
    private TextView mTvCancel;
    private onClickListener listener;
    private boolean save = true;

    public ShareDialog(Context context, onClickListener listener) {
        this.context = context;
        this.listener = listener;
        final WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        dialog = new Dialog(context, R.style.Custom_Dialog_Style);
        dialogWindow = dialog.getWindow();
    }

    public ShareDialog(Context context, boolean save, onClickListener listener) {
        this.context = context;
        this.listener = listener;
        final WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        dialog = new Dialog(context, R.style.Custom_Dialog_Style);
        dialogWindow = dialog.getWindow();
        this.save = save;

    }

    /**
     * 构建
     *
     * @return
     */
    public ShareDialog builder() {
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_share, null, false);
//        LinearLayout mLinearDialog = ((LinearLayout) view.findViewById(R.id.linear_dialog));


//        mIvIcon = ((ImageView) view.findViewById(R.id.iv_icon));
        initView(view);
        dialog.setContentView(view);
//        mLinearDialog.setLayoutParams(new FrameLayout.LayoutParams(((int) (display.getWidth() * 0.80)), LinearLayout.LayoutParams.WRAP_CONTENT));
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        return this;
    }

    private void initView(View view) {
        view.findViewById(R.id.sina_tv).setOnClickListener(this);
        view.findViewById(R.id.tentcent_tv).setOnClickListener(this);
        view.findViewById(R.id.friend_circle_tv).setOnClickListener(this);
        view.findViewById(R.id.weichat_tv).setOnClickListener(this);
        view.findViewById(R.id.qqsapce_tv).setOnClickListener(this);
        view.findViewById(R.id.copy_tv).setOnClickListener(this);
        view.findViewById(R.id.collection_tv).setOnClickListener(this);
        view.findViewById(R.id.report_tv).setOnClickListener(this);
        view.findViewById(R.id.save_tv).setOnClickListener(this);
        if (save) {
            view.findViewById(R.id.save_tv).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.save_tv).setVisibility(View.GONE);
        }
        view.findViewById(R.id.cancel_tv).setOnClickListener(this);
//        qqTv = ((TextView) view.findViewById(R.id.tentcent_tv));
//        circleTv = ((TextView) view.findViewById(R.id.friend_circle_tv));
//        weichatTv = ((TextView) view.findViewById(R.id.weichat_tv));
//        qqspaceTv = ((TextView) view.findViewById(R.id.qqsapce_tv));
//        copyTv = ((TextView) view.findViewById(R.id.copy_tv));
//        collectionTv = ((TextView) view.findViewById(R.id.collection_tv));
//        reportTv = ((TextView) view.findViewById(R.id.report_tv));
//        saveTv = ((TextView) view.findViewById(R.id.save_tv));
//
//        mTvCancel = ((TextView) view.findViewById(R.id.cancel_tv));
    }

    /**
     * setting dialog position
     *
     * @param gravity
     * @return
     */
    public ShareDialog setGravity(int gravity) {
        dialogWindow.setGravity(gravity);
        return this;
    }


    /**
     * setting dialog cancelable
     *
     * @param cancelable
     * @return
     */
    public ShareDialog setCancelable(boolean cancelable) {
        dialog.setCancelable(cancelable);
        return this;
    }

    /**
     * cancel dialog
     *
     * @return
     */
    public ShareDialog dismiss() {
        dialog.dismiss();
        return this;
    }

    /**
     * show dialog
     */
    public void show() {
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sina_tv:
                listener.sinaClick();
                break;
            case R.id.tentcent_tv:
                listener.qqClick();
                break;
            case R.id.friend_circle_tv:
                listener.circleClick();
                break;
            case R.id.weichat_tv:
                listener.wechatClick();
                break;
            case R.id.qqsapce_tv:
                listener.qqspaceClick();
                break;
            case R.id.copy_tv:
                listener.copyClick();
                break;
            case R.id.collection_tv:
                listener.collectionClick();
                break;
            case R.id.report_tv:
                listener.reportClick();
                break;
            case R.id.save_tv:
                listener.saveClick();
                break;
            case R.id.cancel_tv:
                listener.cancleClick();
                break;
            default:

                break;

        }
    }

    public interface onClickListener {
        void sinaClick();

        void qqClick();

        void circleClick();

        void wechatClick();

        void qqspaceClick();

        void copyClick();

        void collectionClick();

        void reportClick();

        void saveClick();

        void cancleClick();

    }

}
