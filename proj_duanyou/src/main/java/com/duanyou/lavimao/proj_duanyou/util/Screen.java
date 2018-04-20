package com.duanyou.lavimao.proj_duanyou.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * @author yang
 * @date 2014-5-26 下午5:14:16
 */
public class Screen {

	static Screen SCREEN = new Screen();

	public int widthPixels;// 屏幕宽
	public int heightPixels;// 屏幕高
	public int barHeight;// 状态栏高度
	public float density;
	public float scaledDensity;
	public int densityDpi;
	public float xdpi;
	public float ydpi;

	private Screen() {

	}

	public static void initScreen(Context context) {
		DisplayMetrics display = context.getResources().getDisplayMetrics();
		SCREEN.widthPixels = display.widthPixels;
		SCREEN.heightPixels = display.heightPixels;
		SCREEN.density = display.density;
		SCREEN.scaledDensity = display.scaledDensity;
		SCREEN.densityDpi = display.densityDpi;
		SCREEN.xdpi = display.xdpi;
		SCREEN.ydpi = display.ydpi;
	}

	public static void setBarHeight(int barHeight) {
		getInstance().barHeight = barHeight;
	}

	public static Screen getInstance() {
		return SCREEN;
	}
}
