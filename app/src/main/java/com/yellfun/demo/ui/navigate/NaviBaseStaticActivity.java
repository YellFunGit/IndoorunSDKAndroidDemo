package com.yellfun.demo.ui.navigate;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.indoorun.mapapi.control.Idr;
import com.indoorun.mapapi.control.map.MapLoader;
import com.indoorun.mapapi.control.navi.NaviResult;
import com.indoorun.mapapi.domain.IdrMapRegionFloorUnit;
import com.indoorun.mapapi.map.gl.IdrMapView;
import com.indoorun.mapapi.view.SpinnerView;
import com.yellfun.demo.R;
import com.yellfun.demo.ui.BaseActionbarActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class NaviBaseStaticActivity extends BaseActionbarActivity {

    /**
     * 起点marker类型
     */
    public static final int CLASS_START = 0x01;

    /**
     * 终点marker类型
     */
    public static final int CLASS_STOP = 0x02;

    @BindView(R.id.map_switcher_view)
    IdrMapView mapView;

    @BindView(R.id.map_switcher)
    SpinnerView spinnerView;

    Idr idr;

    /**
     * 地图加载器，用来添加marker
     */
    MapLoader mapLoader;

    IdrMapRegionFloorUnit currentUnit, startUnit, stopUnit;

    @BindView(R.id.navi_layout)
    View naviLayout;

    /**
     * 起点和终点的marker图标
     */
    Bitmap startBitmap, stopBitmap;

    @BindView(R.id.navi_unit_name)
    TextView unitName;

    /**
     * 导航结果
     */
    NaviResult naviResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi_base);
        setTitleTxt("基本导航（静态导航）");
        startBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.start_position);
        stopBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.car_position);
        idr = Idr.with(mapView);
        mapLoader = idr.loadRegion("14428254382730015")// 加载region
                .onMapUnitClick((mapLoader, unit) -> {
                    naviLayout.setVisibility(View.VISIBLE);
                    currentUnit = unit;
                    unitName.setText(unit.getName());
                    return true;
                })
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

    @OnClick(R.id.navi_start)
    public void naviStart() {
        if (currentUnit != null) {
            startUnit = currentUnit;
            mapLoader.removeMarkerByClass(CLASS_START);
            mapLoader.addMarker(startUnit, CLASS_START, startBitmap, 0, startBitmap.getHeight() / 2);
            navi();
        }
    }

    @OnClick(R.id.navi_stop)
    public void naviStop() {
        if (currentUnit != null) {
            stopUnit = currentUnit;
            mapLoader.removeMarkerByClass(CLASS_STOP);
            mapLoader.addMarker(stopUnit, CLASS_STOP, stopBitmap, 0, stopBitmap.getHeight() / 2);
            navi();
        }
    }

    private void navi() {
        if (startUnit != null && stopUnit != null) {
            naviResult = idr.naviFrom(startUnit.getFloorId(), startUnit.getPointF())
                    .to(stopUnit.getFloorId(), stopUnit.getPointF())
                    .startNavi();
        }
    }
}
