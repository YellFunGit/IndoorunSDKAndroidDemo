package com.indoorun.testnewsdk.ui;

import android.os.Bundle;

import com.indoorun.testnewsdk.R;

public class LocationActivity extends BaseActionbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        setTitleTxt("定位");
    }
}
