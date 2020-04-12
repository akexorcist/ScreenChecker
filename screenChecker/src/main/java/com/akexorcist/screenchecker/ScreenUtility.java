package com.akexorcist.screenchecker;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.lang.reflect.Method;

public class ScreenUtility {
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static Resolution getDeviceResolutionPx(Activity activity) {
        return ScreenUtility.getDeviceScreenResolutionPx(activity, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static Resolution getDeviceResolutionDp(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        Resolution screenResolution = ScreenUtility.getDeviceScreenResolutionPx(activity, display);
        int xDp = (int) (screenResolution.getX() * (1f / dm.density));
        int yDp = (int) (screenResolution.getY() * (1f / dm.density));
        return new Resolution(xDp, yDp);
    }

    public static Resolution getCurrentResolutionPx(Activity activity) {
        return ScreenUtility.getCurrentScreenResolutionPx(activity, null);
    }

    public static Resolution getCurrentResolutionDp(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        Resolution screenResolution = ScreenUtility.getCurrentScreenResolutionPx(activity, display);
        int xDp = (int) (screenResolution.getX() * (1f / dm.density));
        int yDp = (int) (screenResolution.getY() * (1f / dm.density));
        return new Resolution(xDp, yDp);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static Resolution getAppResolutionPx(View rootView) {
        return ScreenUtility.getAppScreenResolutionPx(rootView);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static Resolution getAppResolutionDp(Activity activity, View rootView) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        Resolution screenResolution = ScreenUtility.getAppScreenResolutionPx(rootView);
        int xDp = (int) (screenResolution.getX() * (1f / dm.density));
        int yDp = (int) (screenResolution.getY() * (1f / dm.density));
        return new Resolution(xDp, yDp);
    }

    public static int getDpi(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.densityDpi;
    }

    public static int getDensity(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.densityDpi;
    }

    public static int getSize(Context context) {
        return context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
    }

    public static int getLayout(Context context) {
        return context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_LONG_MASK;
    }

    public static @NonNull
    ColorMode getColorMode(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int hdr = context.getResources().getConfiguration().colorMode & Configuration.COLOR_MODE_HDR_MASK;
            int wideColorGamut = context.getResources().getConfiguration().colorMode & Configuration.COLOR_MODE_WIDE_COLOR_GAMUT_MASK;
            return new ColorMode(hdr, wideColorGamut);
        } else {
            return new ColorMode(0, 0);
        }
    }

    public static UiMode getUiMode(Context context) {
        int type = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_TYPE_MASK;
        int night = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return new UiMode(type, night);
    }

    public static int getMultitouch(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_JAZZHAND)) {
            return Multitouch.JAZZHAND;
        } else if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_DISTINCT)) {
            return Multitouch.DISTINCT;
        } else if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH)) {
            return Multitouch.SIMPLE;
        } else {
            return Multitouch.UNSUPPORTED;
        }
    }

    @SuppressWarnings("deprecation")
    private static Resolution getCurrentScreenResolutionPx(Activity activity, Display display) {
        if (display == null) {
            display = activity.getWindowManager().getDefaultDisplay();
        }
        int resolutionX, resolutionY;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                @SuppressWarnings("JavaReflectionMemberAccess")
                Method getRawHeightMethod = Display.class.getMethod("getRawHeight");
                @SuppressWarnings("JavaReflectionMemberAccess")
                Method getRawWidthMethod = Display.class.getMethod("getRawWidth");
                //noinspection ConstantConditions
                resolutionX = (Integer) getRawWidthMethod.invoke(display);
                //noinspection ConstantConditions
                resolutionY = (Integer) getRawHeightMethod.invoke(display);
            } catch (Exception e) {
                resolutionX = display.getWidth();
                resolutionY = display.getHeight();
            }
        } else {
            DisplayMetrics outMetrics = new DisplayMetrics();
            display.getRealMetrics(outMetrics);
            resolutionX = outMetrics.widthPixels;
            resolutionY = outMetrics.heightPixels;
        }
        return new Resolution(resolutionX, resolutionY);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private static Resolution getDeviceScreenResolutionPx(Activity activity, Display display) {
        if (display == null) {
            display = activity.getWindowManager().getDefaultDisplay();
        }
        float resolutionX = display.getMode().getPhysicalWidth();
        float resolutionY = display.getMode().getPhysicalHeight();
        return new Resolution(resolutionX, resolutionY);
    }

    private static Resolution getAppScreenResolutionPx(View rootView) {
        float resolutionX = rootView.getMeasuredWidth();
        float resolutionY = rootView.getMeasuredHeight();
        return new Resolution(resolutionX, resolutionY);
    }
}
