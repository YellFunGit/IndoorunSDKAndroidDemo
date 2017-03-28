package com.yellfun.demo.ui.map;

import android.os.Bundle;

import com.indoorun.mapapi.control.Idr;
import com.indoorun.mapapi.map.gl.IdrMapView;
import com.indoorun.mapapi.view.SpinnerView;
import com.yellfun.demo.R;
import com.yellfun.demo.ui.BaseActionbarActivity;

import butterknife.BindView;

import static com.yellfun.demo.App.REGION_ID;

/**
 * 加载楼层切换器，可以自定义楼层切换工具
 * 只要实现了 {@link com.indoorun.mapapi.control.event.Loader} 接口，就可以自定义加载楼层的切换工具
 * UI库默认实现为 {@link SpinnerView}
 * 通过这种方法加载楼层，可以切换不同地图
 */
public class MapLoadSwitcherActivity extends BaseActionbarActivity {

    @BindView(R.id.map_switcher_view)
    IdrMapView mapView;

    @BindView(R.id.map_switcher)
    SpinnerView spinnerView;

    Idr idr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_load_switcher);
        setTitleTxt("加载楼层切换器");
        idr = Idr.with(mapView);
        idr.loadRegion(REGION_ID)// 加载region
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
