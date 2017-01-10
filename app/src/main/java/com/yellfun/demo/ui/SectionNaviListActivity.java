package com.yellfun.demo.ui;

import android.content.Intent;

import com.yellfun.demo.ui.navigate.NaviDynamicActivity;
import com.yellfun.demo.ui.navigate.NaviStaticActivity;

import java.util.List;

public class SectionNaviListActivity extends BaseSectionListActivity {

    @Override
    protected void addData(List<String> data) {
        data.add("静态导航");
        data.add("动态导航");
    }

    @Override
    protected void itemClick(int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, NaviStaticActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, NaviDynamicActivity.class));
                break;
        }
    }

    @Override
    protected String InitTitle() {
        return "导航";
    }
}
