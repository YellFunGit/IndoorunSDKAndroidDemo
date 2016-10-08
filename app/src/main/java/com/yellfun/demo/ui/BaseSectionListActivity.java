package com.yellfun.demo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.yellfun.demo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnItemClick;

/**
 * 列表基类
 * Created by sharyuke on 16-10-8.
 */
public abstract class BaseSectionListActivity extends BaseActionbarActivity {

    @BindView(R.id.section_list)
    ListView listView;

    private List<String> menus = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_list);
        setTitleTxt(InitTitle());
        addData(menus);
        listView.setAdapter(new QuickAdapter<String>(this, android.R.layout.simple_list_item_1, menus) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {
                helper.setText(android.R.id.text1, item);
            }
        });
    }

    @OnItemClick(R.id.section_list)
    public void itemGo(int position) {
        itemClick(position);
    }

    protected abstract void addData(List<String> data);

    protected abstract void itemClick(int position);

    protected abstract String InitTitle();
}
