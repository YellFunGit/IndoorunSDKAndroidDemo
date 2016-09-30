package com.indoorun.testnewsdk.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.indoorun.testnewsdk.R;
import com.indoorun.testnewsdk.ui.location.LocationIdrActivity;
import com.indoorun.testnewsdk.ui.location.LocationStandActivity;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnItemClick;

public class SectionLocationListActivity extends BaseActionbarActivity {

    @BindView(R.id.location_list_view)
    ListView listView;

    List<String> menus = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_location_list);
        setTitleTxt("定位");
        menus.add("绑定到Idr");
        menus.add("单独开启关闭");
        listView.setAdapter(new QuickAdapter<String>(this, android.R.layout.simple_list_item_1, menus) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {
                helper.setText(android.R.id.text1, item);
            }
        });
    }

    @OnItemClick(R.id.location_list_view)
    public void itemGo(int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, LocationIdrActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, LocationStandActivity.class));
                break;
        }
    }
}
