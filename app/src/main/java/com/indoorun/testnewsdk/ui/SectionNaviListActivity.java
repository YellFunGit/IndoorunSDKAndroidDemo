package com.indoorun.testnewsdk.ui;

import android.content.Intent;

import com.indoorun.testnewsdk.ui.navigate.NaviBaseActivity;
import com.indoorun.testnewsdk.ui.navigate.NaviOptionActivity;

import java.util.List;

public class SectionNaviListActivity extends BaseSectionListActivity {

    @Override
    protected void addData(List<String> data) {
        data.add("基本方法");
        data.add("OPTION方法");
    }

    @Override
    protected void itemClick(int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, NaviBaseActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, NaviOptionActivity.class));
                break;
        }
    }

    @Override
    protected String InitTitle() {
        return "导航";
    }
}
