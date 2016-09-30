package com.indoorun.testnewsdk.ui;

import android.os.Bundle;

import com.indoorun.testnewsdk.R;

public class MapLoadCustomActivity extends BaseActionbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_load_custom);
        setTitleTxt("加载指定楼层");
    }
}
