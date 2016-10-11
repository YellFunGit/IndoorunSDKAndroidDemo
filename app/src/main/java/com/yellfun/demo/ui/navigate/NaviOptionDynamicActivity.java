package com.yellfun.demo.ui.navigate;

import android.os.Bundle;

import com.indoorun.mapapi.control.Idr;
import com.indoorun.mapapi.control.NaviOptions;
import com.indoorun.mapapi.control.map.MapLoader;
import com.indoorun.mapapi.control.navi.NaviResult;
import com.indoorun.mapapi.map.gl.IdrMapView;
import com.indoorun.mapapi.view.SpinnerView;
import com.yellfun.demo.R;
import com.yellfun.demo.ui.BaseActionbarActivity;

import butterknife.BindView;

public class NaviOptionDynamicActivity extends BaseActionbarActivity {

    @BindView(R.id.map_switcher_view)
    IdrMapView mapView;

    @BindView(R.id.map_switcher)
    SpinnerView spinnerView;

    Idr idr;

    NaviResult naviResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi_option_dynamic);
        idr = Idr.with(mapView);
        idr.locateWithSwitcher().bindStartAndStopLocateToMapHelper();// 开启定位
        MapLoader m = idr.loadRegion("14428254382730015")// 加载region
                .onMapUnitClick((mapLoader, unit) -> {
                    idr.naviOptions().setToFloorId(unit.getFloorId())//设置终点楼层
                            .setToPoint(unit.getPointF())//设置终点坐标
                            .setToMarker(R.mipmap.car_position);//设置终点marker
                    if (naviResult != null) {
                        naviResult.stopNavi();//停止上一次导航
                    }
                    naviResult = idr.startNavi();//开启导航（已经配置为动态导航）
                    return true;
                })
                .loadFloor(spinnerView);//加载楼层切换器
        idr.naviOptions().setSwitcher(m).setType(NaviOptions.Type.DYNAMIC_NAVI);//设置为动态导航
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
