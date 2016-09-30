package com.indoorun.testnewsdk.ui;

import android.os.Bundle;

import com.indoorun.testnewsdk.R;

public class MapLoadSwitcherActivity extends BaseActionbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_load_switcher);
        setTitleTxt("加载楼层切换器");
    }
}
