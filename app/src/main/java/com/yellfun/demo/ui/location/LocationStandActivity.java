package com.yellfun.demo.ui.location;

import android.os.Bundle;
import android.widget.Switch;

import com.indoorun.mapapi.control.Idr;
import com.indoorun.mapapi.control.event.LocateResult;
import com.indoorun.mapapi.control.locate.LocatorViewHelper;
import com.indoorun.mapapi.map.gl.IdrMapView;
import com.indoorun.mapapi.view.SpinnerView;
import com.yellfun.demo.R;
import com.yellfun.demo.ui.BaseActionbarActivity;

import butterknife.BindView;
import butterknife.OnCheckedChanged;

public class LocationStandActivity extends BaseActionbarActivity {

    @BindView(R.id.location_stand_map_view)
    IdrMapView idrMapView;

    @BindView(R.id.map_switcher)
    SpinnerView spinnerView;

    Idr idr;

    LocateResult locate;// 定位结果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_stand);
        setTitleTxt("独立定位");
        idr = Idr.with(idrMapView);
        idr.loadRegion("14428254382730015").loadFloor(spinnerView);// 加载地图
        LocatorViewHelper locatorViewHelper = idr.locateWithSwitcher();//开启定位
        locate = locatorViewHelper.readyToLocate();//获取到定位结果
        spinnerView.setLocator(locatorViewHelper);//设置红点显示当前楼层
    }

    @OnCheckedChanged(R.id.location_switch)
    public void control(Switch switcher, boolean checked) {
        if (checked) {
            switcher.setText("定位中");
            locate.startLocate();
        } else {
            locate.stopLocate();
            switcher.setText("未定位");
        }
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
