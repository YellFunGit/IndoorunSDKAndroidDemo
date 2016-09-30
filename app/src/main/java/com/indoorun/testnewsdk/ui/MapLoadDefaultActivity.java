package com.indoorun.testnewsdk.ui;

import android.os.Bundle;

import com.indoorun.mapapi.control.Idr;
import com.indoorun.mapapi.map.gl.IdrMapView;
import com.indoorun.testnewsdk.R;

import butterknife.BindView;

public class MapLoadDefaultActivity extends BaseActionbarActivity {

    @BindView(R.id.map_view)
    IdrMapView mapView;

    Idr idr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitleTxt("地图加载");
        idr = Idr.with(mapView);
        idr.loadRegion("14428254382730015")// 加载region
                .loadFloor();//加载默认楼层
    }
}
