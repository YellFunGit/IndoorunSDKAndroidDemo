package com.yellfun.demo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.indoorun.mapapi.control.Idr;
import com.indoorun.mapapi.control.locate.LocatorMultiHelper;
import com.indoorun.mapapi.core.net.module.com.model.ResMultiLocatingModel;
import com.indoorun.mapapi.map.gl.IdrMapView;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.yellfun.demo.R;

import static com.yellfun.demo.App.REGION_ID;

public class MultiLocateActivity extends AppCompatActivity implements View.OnClickListener {
    Idr idr;

    EditText uuidEditView;
    ListView uuidListView;

    QuickAdapter<String> adapter;
    View infoLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multi_locate_actiovity);
        IdrMapView idrMap3DView = (IdrMapView) findViewById(R.id.mapView);
        infoLayout = findViewById(R.id.info);
        uuidEditView = (EditText) findViewById(R.id.uuid_view);
        findViewById(R.id.add_uuid).setOnClickListener(this);
        findViewById(R.id.locate).setOnClickListener(this);
        uuidListView = (ListView) findViewById(R.id.uuid_list);
        uuidListView.setAdapter(adapter = new QuickAdapter<String>(this, R.layout.item_uuid) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {
                helper.setText(R.id.item_uuid, item);
            }
        });
        idr = Idr.with(idrMap3DView);
        idr.loadRegion(REGION_ID).loadFloor();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_uuid:
                if (!TextUtils.isEmpty(uuidEditView.getText().toString())) {
                    adapter.add(uuidEditView.getText().toString());
                    uuidEditView.setText("");
                }
                break;
            case R.id.locate:
                if (uuidListView.getVisibility() == View.VISIBLE) {
                    uuidListView.setVisibility(View.GONE);
                    infoLayout.setVisibility(View.GONE);
                    LocatorMultiHelper helper = idr.locateMulti();
                    for (int i = 0; i < adapter.getCount(); i++) {
                        helper.addUUID(adapter.getItem(i));
                    }
                    helper
                            .onSuccess(dataEntities -> {// 成功定位
                                for (ResMultiLocatingModel.DataEntity item : dataEntities) {
                                    if (item != null) {
                                        Log.d("locate", String.format("success %.2f   %.2f", item.getPos().getX(), item.getPos().getY()));
                                    }
                                }
                            })
                            .onFailed(s -> {//定位失败
                                Log.d("locate", "failed");
                            })
                            .locate();
                }

                break;
        }
    }
}
