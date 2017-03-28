package com.yellfun.demo.ui.location;

import android.os.Bundle;

import com.indoorun.mapapi.control.Idr;
import com.indoorun.mapapi.control.locate.LocatorViewHelper;
import com.indoorun.mapapi.map.gl.IdrMapView;
import com.indoorun.mapapi.view.SpinnerView;
import com.yellfun.demo.R;
import com.yellfun.demo.ui.BaseActionbarActivity;

import butterknife.BindView;

import static com.yellfun.demo.App.REGION_ID;

public class LocationIdrActivity extends BaseActionbarActivity {
    @BindView(R.id.location_map_view)
    IdrMapView idrMapView;

    @BindView(R.id.map_switcher)
    SpinnerView spinnerView;

    Idr idr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        setTitleTxt("绑定到Idr");
        idr = Idr.with(idrMapView);
        idr.loadRegion(REGION_ID).loadFloor(spinnerView);// 加载地图
        LocatorViewHelper locatorViewHelper = idr.locateWithSwitcher()// 开启定位
                .bindStartAndStopLocateToMapHelper(); //绑定到Idr
        spinnerView.setLocator(locatorViewHelper);
    }

    @Override
    protected void onResume() {
        super.onResume();
        idr.begin();// 绑定到Idr，跟随Idr的生命周期开启和关闭定位。
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
