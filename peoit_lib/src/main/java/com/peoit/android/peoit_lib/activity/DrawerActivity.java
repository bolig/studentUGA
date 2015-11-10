package com.peoit.android.peoit_lib.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.peoit.android.peoit_lib.R;
import com.peoit.android.peoit_lib.base.BaseActivity;

public abstract class DrawerActivity extends BaseActivity {

    private FrameLayout nav_layout;
    private View nav_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.layout_drawer_main);
        nav_layout = (FrameLayout) findViewById(R.id.fl_drawer);
        nav_view = createNavView();
        if (nav_view != null)
            nav_layout.addView(nav_view);
    }

    protected abstract View createNavView();

}
