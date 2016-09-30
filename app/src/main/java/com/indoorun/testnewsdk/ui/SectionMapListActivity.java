package com.indoorun.testnewsdk.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.indoorun.testnewsdk.R;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnItemClick;

public class SectionMapListActivity extends BaseActionbarActivity {

    @BindView(R.id.map_section_list)
    ListView listView;

    List<String> menus = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_map_list);
        setTitleTxt("加载地图");
        menus.add("加载默认楼层");
        menus.add("加载指定楼层");
        menus.add("加载楼层切换器");
        listView.setAdapter(new QuickAdapter<String>(this, android.R.layout.simple_list_item_1, menus) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {
                helper.setText(android.R.id.text1, item);
            }
        });
    }

    @OnItemClick(R.id.map_section_list)
    public void itemGo(int position) {
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
}
