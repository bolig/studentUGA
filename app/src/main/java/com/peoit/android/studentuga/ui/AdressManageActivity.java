package com.peoit.android.studentuga.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.entity.AddressInfo;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.net.server.AddressManageServer;
import com.peoit.android.studentuga.ui.adapter.AddressManageAdapter;
import com.peoit.android.studentuga.uitl.MyLogger;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayout;
import com.peoit.android.studentuga.view.list.swipe.SwipeMenu;
import com.peoit.android.studentuga.view.list.swipe.SwipeMenuCreator;
import com.peoit.android.studentuga.view.list.swipe.SwipeMenuItem;
import com.peoit.android.studentuga.view.list.swipe.SwipeMenuLayout;
import com.peoit.android.studentuga.view.list.swipe.SwipeMenuListView;

/**
 * 守护地址管理界面
 * <p/>
 * author:libo
 * time:2015/8/4
 * E-mail:boli_android@163.com
 * last: ...
 */
public class AdressManageActivity extends BaseActivity {
    private SwipyRefreshLayout pullLayout;
    private SwipeMenuListView lvmenuInfo;
    private AddressManageServer addressManageServer;
    private AddressManageAdapter addressAdapter;
    private boolean isOrder;

    private void assignViews() {
        pullLayout = (SwipyRefreshLayout) findViewById(R.id.pull_layout);
        lvmenuInfo = (SwipeMenuListView) findViewById(R.id.lvmenu_info);
    }

    public static void startThisActivity(Activity mAc, int requestCode, boolean isOrder) {
        if (CommonUtil.isLoginAndToLogin(mAc, false)){
            Intent intent = new Intent(mAc, AdressManageActivity.class);
            intent.putExtra("isOrder", isOrder);
            mAc.startActivityForResult(intent, requestCode);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_adress_manage);
    }

    @Override
    public void initData() {
        addressManageServer = new AddressManageServer(this);
        isOrder = getIntent().getBooleanExtra("isOrder", false);
    }

    @Override
    public void initView() {
        assignViews();
        if (isOrder) {
            getToolbar().setBack().setTvTitle("选择收货地址").setIvR(R.drawable.ic_add, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddAddressActivity.startThisActivity(mAct, null, addressAdapter.getCount() == 0);
                }
            });
        } else {
            getToolbar().setBack().setTvTitle("收货地址管理").setIvR(R.drawable.ic_add, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddAddressActivity.startThisActivity(mAct, null, addressAdapter.getCount() == 0);
                }
            });
        }
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                int type = menu.getViewType();
                if (type != 1) {
                    SwipeMenuItem menuItem = new SwipeMenuItem(mAct);
                    menuItem.setBackground(new ColorDrawable(getResources().getColor(R.color.col_main)));
                    menuItem.setTitle("默认");
                    menuItem.setWidth(CommonUtil.dip2px(88));
                    menuItem.setTitleColor(mAct.getResources().getColor(R.color.white_1));
                    menuItem.setTitleSize(14);
                    menu.addMenuItem(menuItem);
                }
                if (isOrder) {
                    SwipeMenuItem menuItem = new SwipeMenuItem(mAct);
                    menuItem.setBackground(new ColorDrawable(getResources().getColor(R.color.col_shop_mark)));
                    menuItem.setTitle("修改");
                    menuItem.setWidth(CommonUtil.dip2px(88));
                    menuItem.setTitleColor(mAct.getResources().getColor(R.color.white_1));
                    menuItem.setTitleSize(14);
                    menu.addMenuItem(menuItem);
                }
                SwipeMenuItem menuItem1 = new SwipeMenuItem(mAct);
                menuItem1.setBackground(mAct.getResources().getDrawable(R.drawable.draw_shop_close_sel));
                menuItem1.setIcon(R.drawable.ic_delete);
                menuItem1.setWidth(CommonUtil.dip2px(88));
                menuItem1.setTitleColor(mAct.getResources().getColor(R.color.white_1));
                menuItem1.setTitleSize(16);
                menu.addMenuItem(menuItem1);
            }
        };
        lvmenuInfo.setMenuCreator(creator);
        addressAdapter = addressManageServer.getAddressAdapter();
        lvmenuInfo.setAdapter(addressAdapter);
        addressManageServer.loadAddressList(null);
    }

    @Override
    public void initListener() {
        lvmenuInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AddressInfo info = addressAdapter.getItem(position);
                if (isOrder) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", info);
                    Intent data = new Intent();
                    data.putExtras(bundle);
                    setResult(RESULT_OK, data);
                    finish();
                } else {
                    AddAddressActivity.startThisActivity(mAct, info, false);
                }
            }
        });
        lvmenuInfo.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                AddressInfo info = addressAdapter.getItem(position);
                boolean isDeault = addressAdapter.getItem(position).isDefault();
                if (isDeault) {
                    switch (index) {
                        case 0:
                            if (isOrder) {
                                AddAddressActivity.startThisActivity(mAct, info, false);
                            } else {
                                delAddress(info);
                            }
                            break;
                        case 1:
                            delAddress(info);
                            break;
                    }
                } else {
                    switch (index) {
                        case 0:
                            setDefaultAddress(info);
                            break;
                        case 1:
                            if (isOrder) {
                                AddAddressActivity.startThisActivity(mAct, info, false);
                            } else {
                                delAddress(info);
                            }
                            break;
                        case 2:
                            delAddress(info);
                            break;
                    }
                }
            }
        });
        lvmenuInfo.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {
                pullLayout.setScroll(false);
            }

            @Override
            public void onSwipeEnd(int position) {

            }
        });
        lvmenuInfo.setOnMenuChangeListener(new SwipeMenuLayout.OnMenuStatChangeListener() {
            @Override
            public void onStatChange(boolean isOpen) {
                MyLogger.e("isOpen = " + isOpen);
                pullLayout.setScroll(!isOpen);
            }
        });
        lvmenuInfo.setOnSrcollListener(new SwipeMenuLayout.OnSrcollChangeListener() {
            @Override
            public void onSrcollChange(View root, int currentProgess, int totalProgress) {
                View iv_next = ((ViewGroup) root).getChildAt(2);
                iv_next.setAlpha(((float) totalProgress - (float) currentProgess) / (float) totalProgress);
            }
        });
    }

    /**
     * 请求设置默认收货地址
     *
     * @param info
     */
    private void setDefaultAddress(AddressInfo info) {
        info.setDefault(true);
        addressManageServer.requestModAddress(info, false);
    }

    /**
     * 请求删除收货地址
     *
     * @param info
     */
    private void delAddress(AddressInfo info) {
        addressManageServer.requestDelAddress(info.getId() + "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                addressManageServer.loadAddressList(new BaseServer.OnSuccessCallBack() {
                    @Override
                    public void onSuccess(int mark) {
                        lvmenuInfo.setAdapter(addressAdapter);
                    }
                });
            }
        }
    }
}
