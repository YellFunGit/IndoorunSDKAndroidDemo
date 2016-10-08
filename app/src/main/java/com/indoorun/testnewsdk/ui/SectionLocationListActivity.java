package com.indoorun.testnewsdk.ui;

import android.content.Intent;

import com.indoorun.testnewsdk.ui.location.LocationIdrActivity;
import com.indoorun.testnewsdk.ui.location.LocationStandActivity;

import java.util.List;

public class SectionLocationListActivity extends BaseSectionListActivity {

    @Override
    protected void addData(List<String> data) {
        data.add("绑定到Idr");
        data.add("单独开启关闭");
    }

    @Override
    protected void itemClick(int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, LocationIdrActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, LocationStandActivity.class));
                break;
        }
    }

    @Override
    protected String InitTitle() {
        return "定位";
    }
}
