package com.yellfun.demo.ui;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * BaseActivity
 * Created by sharyuke on 16-9-30.
 */
public class BaseActivity extends FragmentActivity {
    public static Toast sToast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
    }

    protected void showToast(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            if (sToast == null) {
                sToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
            } else {
                sToast.setText(msg);
            }
            sToast.show();
        }
    }

}
