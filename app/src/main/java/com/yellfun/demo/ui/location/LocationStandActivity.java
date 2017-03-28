package com.yellfun.demo.ui.location;

import android.os.Bundle;
import android.widget.Switch;

import com.indoorun.mapapi.control.Idr;
import com.indoorun.mapapi.control.event.LocateResult;
import com.indoorun.mapapi.control.locate.LocatorViewHelper;
import com.indoorun.mapapi.core.data.IndoorunSDKDataCenter;
import com.indoorun.mapapi.map.gl.IdrMapView;
import com.indoorun.mapapi.view.SpinnerView;
import com.yellfun.demo.R;
import com.yellfun.demo.ui.BaseActionbarActivity;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import rx.Observable;

import static com.yellfun.demo.App.REGION_ID;

public class LocationStandActivity extends BaseActionbarActivity {

    @BindView(R.id.location_stand_map_view)
    IdrMapView idrMapView;

    @BindView(R.id.map_switcher)
    SpinnerView spinnerView;

    Idr idr;

    LocateResult locate;// 定位结果

    boolean isLocating;// 用来判断是否正在定位, 以后的SDK版本在定位结果 LocateResult里面会有返回的.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_stand);
        setTitleTxt("独立定位");
        idr = Idr.with(idrMapView);
        idr.loadRegion(REGION_ID).loadFloor(spinnerView);// 加载地图
        LocatorViewHelper locatorViewHelper = idr.locateWithSwitcher();//开启定位
        locate = locatorViewHelper.readyToLocate();//获取到定位结果
        spinnerView.setLocator(locatorViewHelper);//设置红点显示当前楼层
    }

    @OnCheckedChanged(R.id.location_switch)
    public void control(Switch switcher, boolean checked) {
        if (checked) {
            switcher.setText("定位中");
            IndoorunSDKDataCenter.getInstance().getPhoneUUIDWithPermission()
                    .doOnNext(s -> locate.startLocate())
                    .onErrorResumeNext(Observable.empty())
                    .subscribe();
            isLocating = true;
        } else {
            locate.stopLocate();
            switcher.setText("未定位");
            isLocating = false;
        }
    }

    @Override
    public void onBackPressed() {
        if (isLocating) {
            showToast("您还未关闭定位,不能退出!请关闭定位之后再退出");
        } else {
            super.onBackPressed();
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
