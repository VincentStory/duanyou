package com.duanyou.lavimao.proj_duanyou.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.activity.LoginActivity;
import com.duanyou.lavimao.proj_duanyou.activity.MainActivity;
import com.duanyou.lavimao.proj_duanyou.activity.UploadDuanziActivity;
import com.duanyou.lavimao.proj_duanyou.adapter.MainPagerAdapter;
import com.duanyou.lavimao.proj_duanyou.adapter.TitlesAdapter;
import com.duanyou.lavimao.proj_duanyou.base.BaseFragment;
import com.duanyou.lavimao.proj_duanyou.fragment.main.TagFragment;
import com.duanyou.lavimao.proj_duanyou.util.Constants;
import com.duanyou.lavimao.proj_duanyou.util.Contents;
import com.duanyou.lavimao.proj_duanyou.util.FileUtils;
import com.duanyou.lavimao.proj_duanyou.util.UserInfo;
import com.duanyou.lavimao.proj_duanyou.widgets.BottomPopupWindow;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class MainFragment extends BaseFragment {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.nav_right_iv)
    ImageView cameraIv;

    private List<String> mList;
    private TitlesAdapter mAdapter;
    private List<Fragment> fragments;
    private MainPagerAdapter pagerAdapter;
    private Uri imageUri;
    private String videoName;
    private String photoName;

    @Override
    public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initWidget(View view) {
        setTitle(view, "首页");
        setLeftImg(view, R.drawable.icon_logo);
        setRightIv(view, R.drawable.icon_camera);
        initTitles();
        initPagers();

    }

    @OnClick({R.id.nav_right_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nav_right_iv:
                if (!UserInfo.getLoginState()) {
                    ((MainActivity) getActivity()).jumpTo(LoginActivity.class);
                    return;
                }
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, Constants.MY_PERMISSIONS_REQUEST_CALL_PHOTO);
                } else {
                    showBottomDialog();
                }
                break;
        }
    }

    /**
     * 底部弹窗--选择拍照或摄像
     */
    public void showBottomDialog() {

        new BottomPopupWindow(getActivity()).builder().setTitle("选择模式")
                .addSheetItem("拍照", BottomPopupWindow.SheetItemColor.Blue, new BottomPopupWindow.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        photoStart();
                    }
                })
                .addSheetItem("摄像", BottomPopupWindow.SheetItemColor.Blue, new BottomPopupWindow.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        videoStart();
                    }
                }).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.MY_PERMISSIONS_REQUEST_CALL_PHOTO:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    showBottomDialog();
                } else {
                    ToastUtils.showShort("you denied the permission");
                }
                break;
        }
    }

    private void photoStart() {
        //创建file对象，用于存储拍照后的图片
        photoName = System.currentTimeMillis() + "output_image.png";
        File outputImage = new File(FileUtils.galleryPath, photoName);

        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(getActivity(), "com.dyouclub.jokefriends.fileprovider", outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }

        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, Constants.REQUEST_CODE_TAKE_PHOTO);
    }

    private void videoStart() {
        //创建file对象，用于存储摄像后的图片
        videoName = System.currentTimeMillis() + "output_video.mp4";
        File outputImage = new File(FileUtils.galleryPath, videoName);
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(getActivity(), "com.dyouclub.jokefriends.fileprovider", outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }

        //启动相机程序
        Intent intent = new Intent("android.media.action.VIDEO_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, Constants.REQUEST_CODE_TAKE_VIDEO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.REQUEST_CODE_TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        //Bitmap bm = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imageUri));
                        //cameraIv.setImageBitmap(bm);
                        Intent photoIntent = new Intent(getActivity(), UploadDuanziActivity.class);
                        photoIntent.putExtra(Constants.FILE_PATH, FileUtils.galleryPath + photoName);
                        photoIntent.putExtra(Constants.type, "3");
                        startActivity(photoIntent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case Constants.REQUEST_CODE_TAKE_VIDEO:
                if (resultCode == RESULT_OK) {
                    try {
                        Intent videoIntent = new Intent(getActivity(), UploadDuanziActivity.class);
                        videoIntent.putExtra(Constants.FILE_PATH, FileUtils.galleryPath + videoName);
                        videoIntent.putExtra(Constants.type, "4");
                        startActivity(videoIntent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

        }
    }

    @Override
    public void startInvoke(View view) {

    }

    /**
     * 初始化标签行
     */
    private void initTitles() {
        mList = new ArrayList<>();
        mList.add("关注");
        mList.add("推荐");
        mList.add("视频");
        mList.add("趣图");
        mList.add("段子");
        mList.add("精选");
        mList.add("段友秀");
        mAdapter = new TitlesAdapter(mList, getActivity());
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new TitlesAdapter.OnItemClickListener() {
            @Override
            public void click(int position) {
                viewPager.setCurrentItem(position);
                int firstPosition = layoutManager.findFirstVisibleItemPosition();
                int lastPosition = layoutManager.findLastVisibleItemPosition();
                int left = recyclerview.getChildAt(position - firstPosition).getLeft();
                int right = recyclerview.getChildAt(lastPosition - position).getLeft();
                recyclerview.smoothScrollBy((left - right) / 2, 0);
            }
        });
    }

    /**
     * 初始化ViewPager
     */
    private void initPagers() {
        fragments = new ArrayList<>();
        fragments.add(TagFragment.newInstance(Contents.CIRCLE_TYPE));//关注
        fragments.add(TagFragment.newInstance("1"));//推荐
        fragments.add(TagFragment.newInstance("4"));//视频
        fragments.add(TagFragment.newInstance("3"));//趣图
        fragments.add(TagFragment.newInstance("2"));//段子
        fragments.add(TagFragment.newInstance("0"));//精选
        fragments.add(TagFragment.newInstance("4"));//段友秀
        pagerAdapter = new MainPagerAdapter(getFragmentManager(), fragments, getActivity());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(7);
        viewPager.setCurrentItem(2);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mAdapter.setSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
