package com.indoorun.testnewsdk.ui;

import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.indoorun.testnewsdk.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BaseActionbarActivity extends BaseActivity {

    @BindView(R.id.actionbar_title)
    TextView titleView;

    @BindView(R.id.back_img)
    TextView backView;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        View layout = getLayoutInflater().inflate(R.layout.activity_base_actionbar, null);
        ViewStub viewStub = (ViewStub) layout.findViewById(R.id.actionbar_content);
        viewStub.setLayoutResource(layoutResID);
        viewStub.inflate();
        setContentView(layout);
        ButterKnife.bind(this);
    }

    protected void setBackViewVisible(boolean viewVisible) {
        backView.setVisibility(viewVisible ? View.VISIBLE : View.GONE);
    }

    protected void setTitleTxt(String str) {
        titleView.setText(str);
    }

    protected void setTitleTxt(int str) {
        titleView.setText(str);
    }

    @Override
    @OnClick(R.id.back_img)
    public void onBackPressed() {
        super.onBackPressed();
    }
}
