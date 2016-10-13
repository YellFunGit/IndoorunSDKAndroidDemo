package com.yellfun.demo.ui.navigate;

import android.os.Bundle;
import android.widget.TextView;

import com.indoorun.mapapi.control.Idr;
import com.indoorun.mapapi.control.NaviOptions;
import com.indoorun.mapapi.control.map.MapLoader;
import com.indoorun.mapapi.control.navi.NaviResult;
import com.indoorun.mapapi.map.gl.IdrMapView;
import com.indoorun.mapapi.view.SpinnerView;
import com.yellfun.demo.R;
import com.yellfun.demo.ui.BaseActionbarActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static com.indoorun.idrnlib.datawrap.NavigateStatus.ROUTE_NEXT_SUG_ARRIVE;
import static com.indoorun.idrnlib.datawrap.NavigateStatus.ROUTE_NEXT_SUG_FRONT;
import static com.indoorun.idrnlib.datawrap.NavigateStatus.ROUTE_NEXT_SUG_LEFT;
import static com.indoorun.idrnlib.datawrap.NavigateStatus.ROUTE_NEXT_SUG_RIGHT;
import static com.indoorun.idrnlib.datawrap.NavigateStatus.ROUTE_SUG_BACKWARD;
import static com.indoorun.idrnlib.datawrap.NavigateStatus.ROUTE_SUG_FRONT;
import static com.indoorun.idrnlib.datawrap.NavigateStatus.ROUTE_SUG_LEFT;
import static com.indoorun.idrnlib.datawrap.NavigateStatus.ROUTE_SUG_RIGHT;

public class NaviOptionDynamicActivity extends BaseActionbarActivity {

    @BindView(R.id.map_switcher_view)
    IdrMapView mapView;

    @BindView(R.id.map_switcher)
    SpinnerView spinnerView;

    @BindView(R.id.navi_tips)
    TextView tipsView;

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
                    idr.naviOptions()
                            .setToUnit(unit)//设置终点unit
                            .onNaviInfoList((lineResults, navigateStatus) -> {
                                String sug, nextSug;
                                switch (navigateStatus.sug) {
                                    default:
                                    case ROUTE_SUG_FRONT:
                                        sug = "前行";
                                        break;
                                    case ROUTE_SUG_LEFT:
                                        sug = "左转";
                                        break;
                                    case ROUTE_SUG_BACKWARD:
                                        sug = "后方";
                                        break;
                                    case ROUTE_SUG_RIGHT:
                                        sug = "右转";
                                        break;
                                }
                                switch (navigateStatus.nextsug) {
                                    default:
                                    case ROUTE_NEXT_SUG_FRONT:
                                        nextSug = "前行";
                                        break;
                                    case ROUTE_NEXT_SUG_LEFT:
                                        nextSug = "左转";
                                        break;
                                    case ROUTE_NEXT_SUG_ARRIVE:
                                        nextSug = "到达";
                                        break;
                                    case ROUTE_NEXT_SUG_RIGHT:
                                        nextSug = "右转";
                                        break;
                                }
                                tipsView.setText(String.format("%s%.0f米后%s%.0f米", sug, navigateStatus.serialdist / 10, nextSug, navigateStatus.nextserialdist / 10));
                            })
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

    @OnClick(R.id.navi_stop_btn)
    public void stopNavi() {
        if (naviResult != null) {//停止导航
            naviResult.stopNavi();
            tipsView.setText("点击unit开始动态导航");
        }
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
