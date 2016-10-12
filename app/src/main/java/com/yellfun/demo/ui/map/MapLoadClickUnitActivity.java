package com.yellfun.demo.ui.map;

import android.graphics.PointF;
import android.os.Bundle;
import android.widget.TextView;

import com.indoorun.mapapi.control.Idr;
import com.indoorun.mapapi.map.gl.IdrMapView;
import com.yellfun.demo.R;
import com.yellfun.demo.ui.BaseActionbarActivity;

import java.util.Locale;

import butterknife.BindView;

public class MapLoadClickUnitActivity extends BaseActionbarActivity {

    @BindView(R.id.map_click_view)
    IdrMapView mapView;

    @BindView(R.id.tipsView)
    TextView tipsView;

    Idr idr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_load_click_unit);
        setTitleTxt("unit点击事件");
        idr = Idr.with(mapView);
        idr.loadRegion("14428254382730015")// 加载region
                .onMapUnitClick((mapLoader, unit) -> {// 这里返回的pointF是地图坐标，而不是屏幕坐标，也就是说可能为负数
                    PointF pointF = unit.getPointF();
                    tipsView.setText(String.format(Locale.CHINA, "点击的unit名称：%s, 坐标： x-> %.2f  y-> %.2f", unit.getName(), pointF.x, pointF.y));
                    return true;//返回一个boolean， 用来拦截点击事件，如果返回true，则不响应其他的unit或者marker的点击事件，返回false则继续响应其他的点击事件
                })
                .loadFloor();//加载默认楼层
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
