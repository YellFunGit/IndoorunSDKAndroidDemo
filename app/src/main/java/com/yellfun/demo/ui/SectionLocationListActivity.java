package com.yellfun.demo.ui;

import android.content.Intent;

import com.yellfun.demo.ui.location.LocationIdrActivity;
import com.yellfun.demo.ui.location.LocationStandActivity;
import com.yellfun.demo.ui.location.MultiLocateActivity;

import java.util.List;

public class SectionLocationListActivity extends BaseSectionListActivity {

    @Override
    protected void addData(List<String> data) {
        data.add("绑定到Idr");
        data.add("单独开启关闭");
        data.add("多点定位");
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
            case 2:
                startActivity(new Intent(this, MultiLocateActivity.class));
                break;
        }
    }

    @Override
    protected String InitTitle() {
        return "定位";
    }
}
