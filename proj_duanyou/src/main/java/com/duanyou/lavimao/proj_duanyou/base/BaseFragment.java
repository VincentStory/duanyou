package com.duanyou.lavimao.proj_duanyou.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.net.Api;
import com.duanyou.lavimao.proj_duanyou.net.BaseResponse;
import com.duanyou.lavimao.proj_duanyou.net.GetContentResult;
import com.duanyou.lavimao.proj_duanyou.net.NetUtil;
import com.duanyou.lavimao.proj_duanyou.net.request.GetContentRequest;
import com.duanyou.lavimao.proj_duanyou.net.request.GetUserUploadContentRequest;
import com.duanyou.lavimao.proj_duanyou.util.UserInfo;
import com.xiben.ebs.esbsdk.callback.ResultCallback;


/**
 * @author Lavimao
 * @date 2017/10/24
 */

public abstract class BaseFragment extends Fragment {
    protected final String TAG = getClass().getSimpleName();
    protected Activity activity;

    /**
     * 创建视图，将此fragment与布局文件绑定
     *
     * @return 布局文件的视图
     */
    public abstract View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * 初始化控件，通常是findView或执行new方法
     *
     * @param view 布局文件的视图
     */
    public abstract void initWidget(View view);

    /**
     * 开始进行操作，对控件、对象进行操作在此完成
     *
     * @param view 布局文件的视图
     */
    public abstract void startInvoke(View view);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = onCreate(inflater, container, savedInstanceState);
        initWidget(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        startInvoke(this.getView());
    }

    protected void setRightIv(View view, int ResId) {
        ImageView navRightIv = (ImageView) view.findViewById(R.id.nav_right_iv);
        if (navRightIv != null) {
            navRightIv.setVisibility(View.VISIBLE);
            navRightIv.setImageResource(ResId);
        }
    }

    /**
     * 设置标题栏抬头文字
     */
    public void setTitle(View view, String title) {
        TextView tv = view.findViewById(R.id.nav_title);
        if (tv != null) {
            tv.setText(title);
        }
    }

    protected void setLeftImg(View view, int ResId) {
        RelativeLayout backLayout = view.findViewById(R.id.nav_left);
        if (backLayout != null) {
            backLayout.setVisibility(View.VISIBLE);
        }
        ImageView ivLeft = (ImageView) view.findViewById(R.id.iv_left);
        ivLeft.setImageResource(ResId);

    }

    /**
     * 获取首页段子列表
     */
    protected void getContent(final Activity context, String type, final GetContentResult result) {
        GetContentRequest request = new GetContentRequest();
        request.setType(type);
        NetUtil.getData(Api.GETCONTENT, context, request, new ResultCallback() {
            @Override
            public void onResult(final String jsonResult) {
                try {
                    BaseResponse response = JSON.parseObject(jsonResult, BaseResponse.class);
                    if (response.getRespCode().equals("0")) {

                        result.success(jsonResult);
                    } else {

                    }
                } catch (Exception e) {
                    Log.i("TAG", "json解析异常");
                }

            }

            @Override
            public void onError(Exception ex) {

                result.error(ex);
            }
        });
    }


      /**
     * 1.4.1	 获取段友发布的段子
       */
    protected void getUserUploadContent(final Activity context, String targetId, String beginContentId,final GetContentResult result) {
        GetUserUploadContentRequest request = new GetUserUploadContentRequest();
        request.setDeviceID(UserInfo.getDeviceId());
        request.setToken(UserInfo.getToken());
        request.setDyID(UserInfo.getDyId());
        request.setTargetDyID(targetId);
        request.setBeginContentID(beginContentId);
        NetUtil.getData(Api.getUserUploadContent, context, request, new ResultCallback() {
            @Override
            public void onResult(final String jsonResult) {
                try {
                    BaseResponse response = JSON.parseObject(jsonResult, BaseResponse.class);
                    if (response.getRespCode().equals("0")) {

                        result.success(jsonResult);
                    } else {

                    }
                } catch (Exception e) {
                    Log.i("TAG", "json解析异常");
                }

            }

            @Override
            public void onError(Exception ex) {

                result.error(ex);
            }
        });
    }





    protected void gotoActivity(Class activity) {
        Intent intent = new Intent(getContext(), activity);
        startActivity(intent);
    }


}
