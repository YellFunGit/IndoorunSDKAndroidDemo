package com.indoorun.testnewsdk.ui;

import android.os.Bundle;

import com.indoorun.mapapi.control.Idr;
import com.indoorun.mapapi.control.locate.LocatorViewHelper;
import com.indoorun.mapapi.map.gl.IdrMapView;
import com.indoorun.mapapi.view.PositionView;
import com.indoorun.mapapi.view.SpinnerView;
import com.indoorun.testnewsdk.R;

import butterknife.BindView;

public class MapLoadActivity extends BaseActionbarActivity {

    @BindView(R.id.map_view)
    IdrMapView mapView;

    @BindView(R.id.switcher_view)
    SpinnerView spinnerView;

    @BindView(R.id.position_view)
    PositionView positionView;

    Idr idr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitleTxt("地图加载");
        idr = Idr.with(mapView);
        LocatorViewHelper locatorViewHelper = idr.locateWithSwitcher();
        positionView.setLocator(locatorViewHelper)
                .setMapLoader(idr.loadRegion("14428254382730015")
                        .loadFloor(spinnerView));
        locatorViewHelper.bindStartAndStopLocateToMapHelper();
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
