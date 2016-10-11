package com.yellfun.demo.ui.map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.TextView;

import com.indoorun.mapapi.control.Idr;
import com.indoorun.mapapi.map.gl.IdrMapView;
import com.yellfun.demo.R;
import com.yellfun.demo.ui.BaseActionbarActivity;

import java.util.Locale;

import butterknife.BindView;

public class MapLoadClickMarkerActivity extends BaseActionbarActivity {

    @BindView(R.id.map_click_view)
    IdrMapView mapView;

    @BindView(R.id.tipsView)
    TextView tipsView;

    Idr idr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_load_click_marker);
        idr = Idr.with(mapView);
        idr.loadRegion("14428254382730015")// 加载region
                .onMapClick((mapLoader, pointF) -> {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.car_position);
                    if (bitmap != null && mapLoader.getShowFloor() != null && pointF != null) {
                        mapLoader.addMarker(mapLoader.getShowFloor().getId(), bitmap, pointF.x, pointF.y, 0, bitmap.getHeight() / 2);
                    }
                    return true;//是否拦截点击事件
                })
                .onMarkerClick((marker) -> {// 这里返回的pointF是地图坐标，而不是屏幕坐标，也就是说可能为负数
                    tipsView.setText(String.format(Locale.CHINA, "点击的marker坐标： x-> %.2f  y-> %.2f", marker.getX(), marker.getY()));
                    marker.removeMe();
                    return true;//返回一个boolean， 用来拦截点击事件，如果返回true，则不响应其他的unit或者marker的点击事件，返回false则继续响应其他的点击事件
                })
                .loadFloor("14557583851000004");//加载指定楼层
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
