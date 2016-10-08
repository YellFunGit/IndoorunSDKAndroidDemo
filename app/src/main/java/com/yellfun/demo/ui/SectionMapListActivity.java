package com.yellfun.demo.ui;

import android.content.Intent;

import com.yellfun.demo.ui.map.MapLoadCustomActivity;
import com.yellfun.demo.ui.map.MapLoadDefaultActivity;
import com.yellfun.demo.ui.map.MapLoadSwitcherActivity;

import java.util.List;

public class SectionMapListActivity extends BaseSectionListActivity {
    @Override
    protected void addData(List<String> data) {
        data.add("加载默认楼层");
        data.add("加载指定楼层");
        data.add("加载楼层切换器");
    }

    @Override
    protected void itemClick(int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, MapLoadDefaultActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, MapLoadCustomActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, MapLoadSwitcherActivity.class));
                break;
        }
    }

    @Override
    protected String InitTitle() {
        return "加载地图";
    }
}
