package com.duanyou.lavimao.proj_duanyou.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import java.util.Timer;
import java.util.TimerTask;


public class UtilsDialog {

    public static void ShowTips(Context context, String msg,
                                DialogInterface.OnClickListener positive, DialogInterface.OnClickListener negative) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("温馨提示").setMessage(msg);
        if (positive != null) {
            builder = builder.setPositiveButton("确定", positive);
        }
        if (negative != null) {
            builder = builder.setNegativeButton("取消", negative);
        }
        builder.create().show();

    }

    public static void ShowTips(Context context, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("温馨提示").setMessage(msg);
        builder = builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }

        });
        builder.create().show();
    }

    public static void ShowTips(Context context, String msg, DialogInterface.OnClickListener positive, boolean bCanCancled) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("温馨提示").setMessage(msg);
        builder = builder.setPositiveButton("确定", positive);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(bCanCancled);
        dialog.setCanceledOnTouchOutside(bCanCancled);
        dialog.show();
    }


    public static void ShowTips(Context context,
                                String msg,
                                DialogInterface.OnClickListener positive,
                                DialogInterface.OnClickListener negative,
                                boolean bCanCancled) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("温馨提示").setMessage(msg);
        if (positive != null) {
            builder = builder.setPositiveButton("确定", positive);
        }
        if (negative != null) {
            builder = builder.setNegativeButton("取消", negative);
        }
        AlertDialog dialog = builder.create();
        dialog.setCancelable(bCanCancled);
        dialog.setCanceledOnTouchOutside(bCanCancled);
        dialog.show();
    }

    //
    // 可设置动画	http://bUtils_Log.csdn.net/wangjia55/article/details/12975255
    public static void ShowTipsAutoDismiss(final Activity activity, String msg, final boolean bFinishActivity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity).setMessage(msg);
        final AlertDialog dialog = builder.create();
        dialog.show();

        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                cancel();
                dialog.dismiss();

                if (bFinishActivity) {
                    activity.finish();
                }
            }

        }, 1000 * 2);


    }


}
