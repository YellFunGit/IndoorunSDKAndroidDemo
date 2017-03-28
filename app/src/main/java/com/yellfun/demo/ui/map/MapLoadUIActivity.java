package com.yellfun.demo.ui.map;

import android.os.Bundle;

import com.indoorun.mapapi.control.Idr;
import com.indoorun.mapapi.control.locate.LocatorViewHelper;
import com.indoorun.mapapi.control.map.MapLoader;
import com.indoorun.mapapi.map.gl.IdrMapView;
import com.indoorun.mapapi.view.NorthView;
import com.indoorun.mapapi.view.PositionView;
import com.indoorun.mapapi.view.SpinnerView;
import com.yellfun.demo.R;
import com.yellfun.demo.ui.BaseActionbarActivity;

import butterknife.BindView;

import static com.yellfun.demo.App.REGION_ID;

/**
 * UI界面，指南针，楼层切换器，定位点按钮
 */
public class MapLoadUIActivity extends BaseActionbarActivity {

    @BindView(R.id.map_view)
    IdrMapView idrMapView;

    @BindView(R.id.map_spinner)
    SpinnerView spinnerView; // 组件1 楼层切换器

    @BindView(R.id.map_north_view)
    NorthView northView;  // 组件2 楼层指南针

    @BindView(R.id.map_position_view)
    PositionView positionView;// 组件3 定位状态按钮

    Idr idr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_load_ui);
        setTitleTxt("添加UI组件");
        idr = Idr.with(idrMapView);

        MapLoader mapLoader = idr.loadRegion(REGION_ID)//加载指定region
                .onLoadedRegion(region -> northView.setMapView(idrMapView, region.getNorthDeflectionAngle()))// 组件2 楼层指南针 当region加载成功之后，设置指南针mapview对象，第二个参数为北偏角
                .loadFloor(spinnerView);// 加载楼层切换器（组件1 楼层切换器 ， 这样使用之后就可以用来切换楼层）
        LocatorViewHelper locatorViewHelper = idr.locateWithSwitcher().bindStartAndStopLocateToMapHelper();//开启定位
        spinnerView.setLocator(locatorViewHelper);// 组件1 楼层切换器 ， 调用此方法可以显示定位楼层
        positionView.setLocator(locatorViewHelper);// 组件3 定位状态按钮 用来确定定位楼层
        positionView.setMapLoader(mapLoader);// 组件3 定位状态按钮 用来自动切换到定位楼层

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
