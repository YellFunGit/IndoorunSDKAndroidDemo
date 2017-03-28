package com.yellfun.demo.ui.map;

import android.os.Bundle;

import com.indoorun.mapapi.control.Idr;
import com.indoorun.mapapi.map.gl.IdrMapView;
import com.yellfun.demo.R;
import com.yellfun.demo.ui.BaseActionbarActivity;

import butterknife.BindView;

import static com.yellfun.demo.App.FLOOR_ID;
import static com.yellfun.demo.App.REGION_ID;

public class MapLoadCustomActivity extends BaseActionbarActivity {

    @BindView(R.id.map_custom_view)
    IdrMapView mapView;

    Idr idr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_load_custom);
        setTitleTxt("加载指定楼层");
        idr = Idr.with(mapView);
        idr.loadRegion(REGION_ID)// 加载region
                .loadFloor(FLOOR_ID);//加载指定楼层
    }

    @Override
    protected void onResume() {
        super.onResume();
        idr.begin();
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
