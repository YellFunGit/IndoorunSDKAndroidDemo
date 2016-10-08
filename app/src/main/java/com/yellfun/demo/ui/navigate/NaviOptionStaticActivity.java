package com.yellfun.demo.ui.navigate;

import android.os.Bundle;

import com.indoorun.mapapi.control.Idr;
import com.indoorun.mapapi.map.gl.IdrMapView;
import com.indoorun.mapapi.view.SpinnerView;
import com.yellfun.demo.R;
import com.yellfun.demo.ui.BaseActionbarActivity;

import butterknife.BindView;

public class NaviOptionStaticActivity extends BaseActionbarActivity {

    @BindView(R.id.map_switcher_view)
    IdrMapView mapView;

    @BindView(R.id.map_switcher)
    SpinnerView spinnerView;

    Idr idr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi_option);
        setTitleTxt("OPTION导航（静态导航）");
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

    @Override
    protected void onDestroy() {
        idr.destory();
        super.onDestroy();
    }
}
