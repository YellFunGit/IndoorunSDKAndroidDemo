package com.indoorun.testnewsdk.ui;

import android.content.Intent;

import java.util.List;

public class SectionHomeListActivity extends BaseSectionListActivity {

    @Override
    protected void addData(List<String> data) {
        data.add("加载地图");
        data.add("定位");
        data.add("导航");
    }

    @Override
    protected void itemClick(int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, SectionMapListActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, SectionLocationListActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, SectionNaviListActivity.class));
                break;
        }
    }

    @Override
    protected String InitTitle() {
        setBackViewVisible(false);
        return "功能列表";
    }
}
