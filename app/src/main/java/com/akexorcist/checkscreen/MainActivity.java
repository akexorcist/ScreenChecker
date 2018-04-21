package com.akexorcist.checkscreen;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
    private View rootView;
    private TextView textViewSingleDeviceResolutionPx;
    private TextView textViewSingleDeviceResolutionDp;
    private TextView textViewSingleAppResolutionPx;
    private TextView textViewSingleAppResolutionDp;
    private TextView textViewMultiDeviceResolutionPx;
    private TextView textViewMultiDeviceResolutionDp;
    private TextView textViewMultiAppResolutionPx;
    private TextView textViewMultiAppResolutionDp;
    private TextView textViewMultiCurrentResolutionPx;
    private TextView textViewMultiCurrentResolutionDp;
    private TextView textViewDpi;
    private TextView textViewSize;
    private TextView textViewDensity;
    private TextView textViewRatio;
    private TextView textViewUiMode;
    private TextView textViewColorMode;
    private TextView textViewMultitouch;
    private LinearLayout layoutSingleResolution;
    private LinearLayout layoutMultiResolution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
        setupView();
    }

    private void bindView() {
        rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        textViewSingleDeviceResolutionPx = findViewById(R.id.textViewSingleDeviceResolutionPx);
        textViewSingleDeviceResolutionDp = findViewById(R.id.textViewSingleDeviceResolutionDp);
        textViewSingleAppResolutionPx = findViewById(R.id.textViewSingleAppResolutionPx);
        textViewSingleAppResolutionDp = findViewById(R.id.textViewSingleAppResolutionDp);
        textViewMultiDeviceResolutionPx = findViewById(R.id.textViewMultiDeviceResolutionPx);
        textViewMultiDeviceResolutionDp = findViewById(R.id.textViewMultiDeviceResolutionDp);
        textViewMultiCurrentResolutionPx = findViewById(R.id.textViewMultiCurrentResolutionPx);
        textViewMultiCurrentResolutionDp = findViewById(R.id.textViewMultiCurrentResolutionDp);
        textViewMultiAppResolutionPx = findViewById(R.id.textViewMultiAppResolutionPx);
        textViewMultiAppResolutionDp = findViewById(R.id.textViewMultiAppResolutionDp);
        textViewDpi = findViewById(R.id.textViewDpi);
        textViewSize = findViewById(R.id.textViewSize);
        textViewDensity = findViewById(R.id.textViewDensity);
        textViewRatio = findViewById(R.id.textViewRatio);
        textViewUiMode = findViewById(R.id.textViewUiMode);
        textViewColorMode = findViewById(R.id.textViewColorMode);
        textViewMultitouch = findViewById(R.id.textViewMultitouch);
        layoutSingleResolution = findViewById(R.id.layoutSingleResolution);
        layoutMultiResolution = findViewById(R.id.layoutMultiResolution);
    }

    private void setupView() {
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            layoutSingleResolution.setVisibility(View.GONE);
            layoutMultiResolution.setVisibility(View.VISIBLE);
            textViewMultiDeviceResolutionPx.setText(ScreenUtility.getDeviceResolutionPx(this));
            textViewMultiDeviceResolutionDp.setText(ScreenUtility.getDeviceResolutionDp(this));
            textViewMultiCurrentResolutionPx.setText(ScreenUtility.getCurrentResolutionPx(this));
            textViewMultiCurrentResolutionDp.setText(ScreenUtility.getCurrentResolutionDp(this));
        } else {
            layoutSingleResolution.setVisibility(View.VISIBLE);
            layoutMultiResolution.setVisibility(View.GONE);
            textViewSingleDeviceResolutionPx.setText(ScreenUtility.getCurrentResolutionPx(this));
            textViewSingleDeviceResolutionDp.setText(ScreenUtility.getCurrentResolutionDp(this));
        }
        textViewDpi.setText(ScreenUtility.getDpi(this));
        textViewSize.setText(ScreenUtility.getSize(this));
        textViewDensity.setText(ScreenUtility.getDensity(this));
        textViewRatio.setText(ScreenUtility.getRatio(this));
        textViewUiMode.setText(ScreenUtility.getUiMode(this));
        textViewColorMode.setText(ScreenUtility.getColorMode(this));
        textViewMultitouch.setText(ScreenUtility.getMultitouch(this));
    }

    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @SuppressWarnings("ConstantConditions")
        @Override
        public void onGlobalLayout() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                textViewMultiAppResolutionPx.setText(ScreenUtility.getAppResolutionPx(MainActivity.this, rootView));
                textViewMultiAppResolutionDp.setText(ScreenUtility.getAppResolutionDp(MainActivity.this, rootView));
            } else {
                textViewSingleAppResolutionPx.setText(ScreenUtility.getAppResolutionPx(MainActivity.this, rootView));
                textViewSingleAppResolutionDp.setText(ScreenUtility.getAppResolutionDp(MainActivity.this, rootView));
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            rootView.getViewTreeObserver().removeOnGlobalLayoutListener(globalLayoutListener);
        }
    }
}
