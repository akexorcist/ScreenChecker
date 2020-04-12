package com.akexorcist.screenchecker;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Method;

public class ScreenUtility {

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String getDeviceResolutionPx(Activity activity) {
        Resolution screenResolution = ScreenUtility.getDeviceScreenResolutionPx(activity, null);
        return (int) screenResolution.getX() + " x " + (int) screenResolution.getY() + " px";
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String getDeviceResolutionDp(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        Resolution screenResolution = ScreenUtility.getDeviceScreenResolutionPx(activity, display);
        int heightDp = (int) (screenResolution.getY() * (1f / dm.density));
        int widthDp = (int) (screenResolution.getX() * (1f / dm.density));
        return widthDp + " x " + heightDp + " dp";
    }

    public static String getCurrentResolutionPx(Activity activity) {
        Resolution screenResolution = ScreenUtility.getCurrentScreenResolutionPx(activity, null);
        return (int) screenResolution.getX() + " x " + (int) screenResolution.getY() + " px";
    }

    public static String getCurrentResolutionDp(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        Resolution screenResolution = ScreenUtility.getCurrentScreenResolutionPx(activity, display);
        int heightDp = (int) (screenResolution.getY() * (1f / dm.density));
        int widthDp = (int) (screenResolution.getX() * (1f / dm.density));
        return widthDp + " x " + heightDp + " dp";
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static String getAppResolutionPx(Activity activity, View rootView) {
        Resolution screenResolution = ScreenUtility.getAppScreenResolutionPx(rootView);
        return (int) screenResolution.getX() + " x " + (int) screenResolution.getY() + " px";
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static String getAppResolutionDp(Activity activity, View rootView) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        Resolution screenResolution = ScreenUtility.getAppScreenResolutionPx(rootView);
        int heightDp = (int) (screenResolution.getY() * (1f / dm.density));
        int widthDp = (int) (screenResolution.getX() * (1f / dm.density));
        return widthDp + " x " + heightDp + " dp";
    }

    public static String getDpi(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.densityDpi + " DPI";
    }

    public static String getSize(Context context) {
        int screenSize = context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK;
        if (screenSize == Configuration.SCREENLAYOUT_SIZE_SMALL) {
            return "Small";
        } else if (screenSize == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            return "Normal";
        } else if (screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            return "Large";
        } else if (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            return "Extra Large";
        } else if (screenSize == Configuration.SCREENLAYOUT_SIZE_UNDEFINED) {
            return "Undefined";
        }
        return "Unknown";
    }

    public static String getDensity(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        if (dm.densityDpi == DisplayMetrics.DENSITY_LOW) {
            return "Low";
        } else if (dm.densityDpi == DisplayMetrics.DENSITY_MEDIUM) {
            return "Medium";
        } else if (dm.densityDpi == DisplayMetrics.DENSITY_TV) {
            return "TV";
        } else if (dm.densityDpi == DisplayMetrics.DENSITY_HIGH) {
            return "High";
        } else if (dm.densityDpi == DisplayMetrics.DENSITY_XHIGH) {
            return "Extra High";
        } else if (dm.densityDpi == DisplayMetrics.DENSITY_XXHIGH) {
            return "Extra Extra High";
        } else if (dm.densityDpi == DisplayMetrics.DENSITY_XXXHIGH) {
            return "Extra Extra Extra High";
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1
                && dm.densityDpi == DisplayMetrics.DENSITY_260) {
            return "Density 260";
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1
                && dm.densityDpi == DisplayMetrics.DENSITY_280) {
            return "280";
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1
                && dm.densityDpi == DisplayMetrics.DENSITY_300) {
            return "300";
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1
                && dm.densityDpi == DisplayMetrics.DENSITY_340) {
            return "340";
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && dm.densityDpi == DisplayMetrics.DENSITY_360) {
            return "360";
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && dm.densityDpi == DisplayMetrics.DENSITY_400) {
            return "400";
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && dm.densityDpi == DisplayMetrics.DENSITY_420) {
            return "420";
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                && dm.densityDpi == DisplayMetrics.DENSITY_560) {
            return "560";
        }
        return "Unknown";
    }

    public static String getRatio(Context context) {
        int screenSize = context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_LONG_MASK;
        if (screenSize == Configuration.SCREENLAYOUT_LONG_YES) {
            return "Long";
        } else if (screenSize == Configuration.SCREENLAYOUT_LONG_NO) {
            return "Not Long";
        } else if (screenSize == Configuration.SCREENLAYOUT_LONG_UNDEFINED) {
            return "Undefined";
        }
        return "Unknown";
    }

    public static String getColorMode(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int hdr = context.getResources().getConfiguration().colorMode & Configuration.COLOR_MODE_HDR_MASK;
            int wideColorGamut = context.getResources().getConfiguration().colorMode & Configuration.COLOR_MODE_WIDE_COLOR_GAMUT_MASK;
            if (hdr == Configuration.COLOR_MODE_HDR_YES && wideColorGamut == Configuration.COLOR_MODE_WIDE_COLOR_GAMUT_YES) {
                return "HDR & Wide Color Gamut";
            } else if (hdr == Configuration.COLOR_MODE_HDR_YES) {
                return "HDR";
            } else if (wideColorGamut == Configuration.COLOR_MODE_WIDE_COLOR_GAMUT_YES) {
                return "Wide Color Gamut";
            }
        }
        return "Not supported";
    }

    public static String getUiMode(Context context) {
        int uiModeType = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_TYPE_MASK;
        int uiModeNight = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        String uiMode = "Unknown";
        if (uiModeType == Configuration.UI_MODE_TYPE_APPLIANCE) {
            uiMode = "Appliance";
        } else if (uiModeType == Configuration.UI_MODE_TYPE_CAR) {
            uiMode = "Car";
        } else if (uiModeType == Configuration.UI_MODE_TYPE_DESK) {
            uiMode = "Desk";
        } else if (uiModeType == Configuration.UI_MODE_TYPE_NORMAL) {
            uiMode = "Normal";
        } else if (uiModeType == Configuration.UI_MODE_TYPE_TELEVISION) {
            uiMode = "Television";
        } else if (uiModeType == Configuration.UI_MODE_TYPE_VR_HEADSET) {
            uiMode = "VR Headset";
        } else if (uiModeType == Configuration.UI_MODE_TYPE_WATCH) {
            uiMode = "Watch";
        }
        if (uiModeNight == Configuration.UI_MODE_NIGHT_YES) {
            uiMode += " with Night";
        }
        return uiMode;
    }

    public static String getMultitouch(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_JAZZHAND)) {
            return "5+ Points";
        } else if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_DISTINCT)) {
            return "2-5 Points";
        } else if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH)) {
            return "2 Points";
        }
        return "Not supported";
    }

    @SuppressWarnings("deprecation")
    private static Resolution getCurrentScreenResolutionPx(Activity activity, Display display) {
        if (display == null) {
            display = activity.getWindowManager().getDefaultDisplay();
        }
        int resolutionX = 0, resolutionY = 0;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                Method mGetRawH = Display.class.getMethod("getRawHeight");
                Method mGetRawW = Display.class.getMethod("getRawWidth");
                resolutionX = (Integer) mGetRawW.invoke(display);
                resolutionY = (Integer) mGetRawH.invoke(display);
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
    @SuppressWarnings("deprecation")
    private static Resolution getDeviceScreenResolutionPx(Activity activity, Display display) {
        if (display == null) {
            display = activity.getWindowManager().getDefaultDisplay();
        }
        float resolutionX = display.getMode().getPhysicalWidth();
        float resolutionY = display.getMode().getPhysicalHeight();
        return new Resolution(resolutionX, resolutionY);
    }

    @SuppressWarnings("deprecation")
    private static Resolution getAppScreenResolutionPx(View rootView) {
        float resolutionX = rootView.getMeasuredWidth();
        float resolutionY = rootView.getMeasuredHeight();
        return new Resolution(resolutionX, resolutionY);
    }

    private Display getDefaultDisplay(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        return display;
    }
}
