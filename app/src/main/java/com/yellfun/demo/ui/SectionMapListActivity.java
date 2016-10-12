package com.yellfun.demo.ui;

import android.content.Intent;

import com.yellfun.demo.ui.map.MapLoadClickMapActivity;
import com.yellfun.demo.ui.map.MapLoadClickMarkerActivity;
import com.yellfun.demo.ui.map.MapLoadClickUnitActivity;
import com.yellfun.demo.ui.map.MapLoadCustomActivity;
import com.yellfun.demo.ui.map.MapLoadDefaultActivity;
import com.yellfun.demo.ui.map.MapLoadSwitcherActivity;
import com.yellfun.demo.ui.map.MapLoadUIActivity;

import java.util.List;

public class SectionMapListActivity extends BaseSectionListActivity {
    @Override
    protected void addData(List<String> data) {
        data.add("加载默认楼层");
        data.add("加载指定楼层");
        data.add("加载楼层切换器");
        data.add("地图点击事件");
        data.add("unit点击事件");
        data.add("marker点击事件");
        data.add("UI组件使用演示");
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
            case 3:
                startActivity(new Intent(this, MapLoadClickMapActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, MapLoadClickUnitActivity.class));
                break;
            case 5:
                startActivity(new Intent(this, MapLoadClickMarkerActivity.class));
                break;
            case 6:
                startActivity(new Intent(this, MapLoadUIActivity.class));
                break;
        }
    }

    @Override
    protected String InitTitle() {
        return "加载地图";
    }
}
