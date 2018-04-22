package com.duanyou.lavimao.proj_duanyou.base;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.blankj.utilcode.util.SizeUtils;
import com.duanyou.lavimao.proj_duanyou.AppManager;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.util.DeviceUtils;
import com.duanyou.lavimao.proj_duanyou.util.PermissionRequest;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;
import com.yanzhenjie.permission.AndPermission;

import java.util.List;

import cn.jzvd.JZVideoPlayer;


/**
 * @author Lavimao
 * @date 2017/10/23
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected AppCompatActivity activity = this;
    protected final String TAG = this.getClass().getSimpleName();


    protected boolean bEnableHideInputManger = true;
    protected boolean bEnableImmersive = false;
    private boolean mEnableSlide = true;


    /**
     * 设置activity的布局
     *
     * @see Activity#setContentView(int)
     */
    public abstract void setView();

    /**
     * 初始化数据和配置
     */
    public abstract void initData();

    /**
     * 执行动作
     */
    public abstract void startInvoke();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppManager.getAppManager().addActivity(this);

        setView();
        initData();
        startInvoke();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setBarColor();

        }
        if (bEnableImmersive) {
            if (hasKitKat() && !hasLollipop()) {
                //透明状态栏
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            } else if (hasLollipop()) {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            }


            final TextView toolbar = findViewById(R.id.tv_fill_status_bar);
            if (toolbar != null) {
                //1.先设置toolbar的高度
                ViewGroup.LayoutParams params = toolbar.getLayoutParams();
                int statusBarHeight = getStatusBarHeight(this);
                params.height += statusBarHeight;
                toolbar.setLayoutParams(params);

            }

        }

        if (mEnableSlide) {
            SlidrConfig mConfig = new SlidrConfig.Builder()
                    .position(SlidrPosition.LEFT)
                    .velocityThreshold(2400)
                    .edge(true)
                    .edgeSize(0.18f) // The % of the screen that counts as the edge, default_pic 18%
                    .build();


            // Attach the Slidr Mechanism to this activity
            Slidr.attach(this, mConfig);
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setBarColor() {
        Window window = activity.getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(getResources().getColor(R.color.black));
    }

    public void initEnableSlide(boolean enableSlide) {
        mEnableSlide = enableSlide;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }

    private boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    private boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }


    // -------------------------------------隐藏输入法-----------------------------------------------------
    // 获取点击事件
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (bEnableHideInputManger) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                View view = getCurrentFocus();
                if (isHideInput(view, ev)) {
                    HideSoftInput(view.getWindowToken());
                }
            }
        }

        return super.dispatchTouchEvent(ev);
    }

    // 判定是否需要隐藏
    private boolean isHideInput(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top
                    && ev.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    // 隐藏软键盘
    private void HideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    // -------------------------------------隐藏输入法-----------------------------------------------------


    /**
     * 获取导航栏高度
     *
     * @param context
     * @return
     */
    private int getNavigationBarHeight(Context context) {
        return getSystemComponentDimen(this, "navigation_bar_height");
    }

    /**
     * 获取状态栏的高度
     *
     * @param context
     * @return
     */
    private int getStatusBarHeight(Context context) {
        // 反射手机运行的类：android.R.dimen.status_bar_height.
        return getSystemComponentDimen(this, "status_bar_height");
    }

    /**
     * 反射获取高度
     *
     * @param context
     * @param dimenName
     * @return
     */
    private static int getSystemComponentDimen(Context context, String dimenName) {
        // 反射手机运行的类：android.R.dimen.status_bar_height.
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            String heightStr = clazz.getField(dimenName).get(object).toString();
            int height = Integer.parseInt(heightStr);
            //dp--->px
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 判断是否显示导航栏
     *
     * @param wm
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static boolean hasNavigationBarShow(WindowManager wm) {
        Display display = wm.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        //获取整个屏幕的高度
        display.getRealMetrics(outMetrics);
        int heightPixels = outMetrics.heightPixels;
        int widthPixels = outMetrics.widthPixels;
        //获取内容展示部分的高度
        outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        int heightPixels2 = outMetrics.heightPixels;
        int widthPixels2 = outMetrics.widthPixels;
        int w = widthPixels - widthPixels2;
        int h = heightPixels - heightPixels2;
        return w > 0 || h > 0;//竖屏和横屏两种情况。
    }

    public void onPermissionSuccessful() {

    }

    public void getTakePhotoPermission() {
        PermissionRequest permissionRequest;
        permissionRequest = new PermissionRequest(this, new PermissionRequest.PermissionCallback() {
            @Override
            public void onSuccessful(List<String> grantedPermissions) {
                // 权限申请成功回调。
                onPermissionSuccessful();
            }

            @Override
            public void onFailure(List<String> deniedPermissions) {

                if (AndPermission.hasAlwaysDeniedPermission(activity, deniedPermissions)) {
                    // 第一种：用默认的提示语。
                    AndPermission.defaultSettingDialog(activity, 100).show();

                }
            }
        });

        String[] p = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        // 先判断是否有权限。
        if (permissionRequest.hasPermission(p)) {
            // 有权限，直接do anything.
            onPermissionSuccessful();
        } else {
            permissionRequest.request(p);
        }

    }

    public void getPermission(String[] p) {
        PermissionRequest permissionRequest;
        permissionRequest = new PermissionRequest(this, new PermissionRequest.PermissionCallback() {
            @Override
            public void onSuccessful(List<String> grantedPermissions) {
                // 权限申请成功回调。
                onPermissionSuccessful();
            }

            @Override
            public void onFailure(List<String> deniedPermissions) {

                if (AndPermission.hasAlwaysDeniedPermission(activity, deniedPermissions)) {
                    // 第一种：用默认的提示语。
                    AndPermission.defaultSettingDialog(activity, 100).show();

                }
            }
        });

        // 先判断是否有权限。
        if (permissionRequest.hasPermission(p)) {
            // 有权限，直接do anything.
            onPermissionSuccessful();
        } else {
            permissionRequest.request(p);
        }

    }


    /**
     * 设置标题栏抬头文字
     */
    public void setTitle(String title) {
        TextView tv = findViewById(R.id.nav_title);
        if (tv != null) {
            tv.setText(title);
        }
    }


    protected void setRightIv(int ResId) {
        ImageView navRightIv = (ImageView) findViewById(R.id.nav_right_iv);
        if (navRightIv != null) {
            navRightIv.setVisibility(View.VISIBLE);
            navRightIv.setImageResource(ResId);
        }
    }

    protected void setRightIv(int ResId, View.OnClickListener listener) {
        ImageView navRightIv = (ImageView) findViewById(R.id.nav_right_iv);
        if (navRightIv != null) {
            navRightIv.setVisibility(View.VISIBLE);
            navRightIv.setImageResource(ResId);
            navRightIv.setOnClickListener(listener);
        }
    }

    protected void setLeftTitle() {
        RelativeLayout backLayout = findViewById(R.id.nav_left);
        if (backLayout != null) {
            backLayout.setVisibility(View.VISIBLE);
            backLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    protected void setLeftImg(int ResId) {
        RelativeLayout backLayout = findViewById(R.id.nav_left);
        if (backLayout != null) {
            backLayout.setVisibility(View.VISIBLE);
        }
        ImageView ivLeft = (ImageView) findViewById(R.id.iv_left);
        ivLeft.setImageResource(ResId);

    }

    /**
     * activity中搭载了多个fragment，需要显示某个fragment时使用,
     * <br><B>注：同时加载多个Fragment，但只显示一个Fragment时才使用</B>
     *
     * @param fragmentList fragment的集合
     * @param add2LayoutId 此fragment要添加到的布局，布局的ID
     * @param position     此fragment在集合中的下标
     */
    public void addFragmentStack(List<? extends Fragment> fragmentList, int add2LayoutId, int position) {
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = fragmentList.get(position);
        if (!fragment.isAdded()) {
            ft.add(add2LayoutId, fragment);

        }
        for (int i = 0; i < fragmentList.size(); i++) {
            if (i == position) {
                ft.show(fragmentList.get(i));
            } else {
                ft.hide(fragmentList.get(i));
            }
        }
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    public void jumpTo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

}
