package com.yellfun.demo.ui;

import android.content.Intent;

import com.yellfun.demo.ui.navigate.NaviBaseDynamicActivity;
import com.yellfun.demo.ui.navigate.NaviBaseStaticActivity;
import com.yellfun.demo.ui.navigate.NaviOptionDynamicActivity;
import com.yellfun.demo.ui.navigate.NaviOptionStaticActivity;

import java.util.List;

public class SectionNaviListActivity extends BaseSectionListActivity {

    @Override
    protected void addData(List<String> data) {
        data.add("基本方法（静态导航）");
        data.add("基本方法（动态导航）");
        data.add("OPTION方法（静态导航）");
        data.add("OPTION方法（动态导航）");
    }

    @Override
    protected void itemClick(int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, NaviBaseStaticActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, NaviBaseDynamicActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, NaviOptionStaticActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, NaviOptionDynamicActivity.class));
                break;
        }
    }

    @Override
    protected String InitTitle() {
        return "导航";
    }
}
