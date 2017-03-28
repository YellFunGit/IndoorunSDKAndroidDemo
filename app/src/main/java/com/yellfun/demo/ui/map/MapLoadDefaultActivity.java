package com.yellfun.demo.ui.map;

import android.os.Bundle;

import com.indoorun.mapapi.control.Idr;
import com.indoorun.mapapi.map.gl.IdrMapView;
import com.yellfun.demo.R;
import com.yellfun.demo.ui.BaseActionbarActivity;

import butterknife.BindView;

import static com.yellfun.demo.App.REGION_ID;

public class MapLoadDefaultActivity extends BaseActionbarActivity {

    @BindView(R.id.map_view)
    IdrMapView mapView;

    Idr idr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitleTxt("加载默认楼层");
        idr = Idr.with(mapView);
        idr.loadRegion(REGION_ID)// 加载region
                .loadFloor();//加载默认楼层
    }

    @Override
    protected void onResume() {
        super.onResume();
        idr.begin();// 调用此方法，用来resume MapView，以此来初始化地磁传感器
    }

    @Override
    protected void onPause() {
        idr.end();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        idr.destory();
        super.onDestroy();
    }
}
