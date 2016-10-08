package com.indoorun.testnewsdk.ui.navigate;

import android.os.Bundle;

import com.indoorun.mapapi.control.Idr;
import com.indoorun.mapapi.map.gl.IdrMapView;
import com.indoorun.mapapi.view.SpinnerView;
import com.indoorun.testnewsdk.R;
import com.indoorun.testnewsdk.ui.BaseActionbarActivity;

import butterknife.BindView;

public class NaviBaseActivity extends BaseActionbarActivity {

    @BindView(R.id.map_switcher_view)
    IdrMapView mapView;

    @BindView(R.id.map_switcher)
    SpinnerView spinnerView;

    Idr idr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi_base);
        setTitleTxt("基本导航");
        idr = Idr.with(mapView);
        idr.loadRegion("14428254382730015")// 加载region
                .loadFloor(spinnerView);//加载楼层切换器
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
}
