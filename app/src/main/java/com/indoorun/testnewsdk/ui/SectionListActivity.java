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

public class SectionListActivity extends BaseActionbarActivity {

    @BindView(R.id.section_list)
    ListView listView;

    List<String> menus = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_list);
        setTitleTxt("功能列表");
        setBackViewVisible(false);
        menus.add("加载地图");
        listView.setAdapter(new QuickAdapter<String>(this, android.R.layout.simple_list_item_1, menus) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {
                helper.setText(android.R.id.text1, item);
            }
        });
    }

    @OnItemClick(R.id.section_list)
    public void itemGo(int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, MapLoadActivity.class));
                break;
        }
    }
}
