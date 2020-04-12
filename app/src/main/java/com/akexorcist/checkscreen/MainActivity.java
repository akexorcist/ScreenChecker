package com.akexorcist.checkscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.akexorcist.screenchecker.ScreenCheckerActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, ScreenCheckerActivity.class));
        finish();
    }
}
