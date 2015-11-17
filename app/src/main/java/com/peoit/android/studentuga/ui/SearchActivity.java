package com.peoit.android.studentuga.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.net.server.SearchGoodsServer;
import com.peoit.android.studentuga.ui.adapter.SearchAdapter;
import com.peoit.android.studentuga.ui.showUI.SimpleShowUiShow;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvType;
    private EditText etSearch;
    private TextView tvSearch;
    private SwipyRefreshLayout pullLayout;
    private ListView lvInfo;
    private PopupWindow mSelectTypePupop;
    private SearchAdapter mSearchAdapter;
    private List<String> mSearchInfos = new ArrayList<>();
    private SearchType lastSearchType = SearchType.goods;
    private FrameLayout flRoot;
    private SimpleShowUiShow mUIShow;
    private SearchGoodsServer mSearchServer;
    private String mSearchInfo;

    public static void startThisActivity(Activity mAc) {
        Intent intent = new Intent(mAc, SearchActivity.class);
        mAc.startActivity(intent);
    }

    private void assignViews() {
        tvType = (TextView) findViewById(R.id.tv_type);
        etSearch = (EditText) findViewById(R.id.et_search);
        tvSearch = (TextView) findViewById(R.id.tv_search);
        pullLayout = (SwipyRefreshLayout) findViewById(R.id.pull_layout);
        lvInfo = (ListView) findViewById(R.id.lv_info);
        flRoot = (FrameLayout) findViewById(R.id.fl_root);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.act_search);
    }

    @Override
    public void initData() {
        mSearchInfos.add("商品");
        mSearchInfos.add("商户");
        mSearchAdapter = new SearchAdapter(mAct);
        mSearchAdapter.upDateList(mSearchInfos);
        mSearchServer = new SearchGoodsServer(mAct);
    }

    @Override
    public void initView() {
        assignViews();
        initPupoWindow();
        tvSearch.setEnabled(false);
        mUIShow = new SimpleShowUiShow(mAct);
        mUIShow.setRootView(flRoot);
        showNull();
//        lvInfo.setAdapter();
    }

    private void showNull() {
        mUIShow.setIvErrorImg(R.drawable.noproduct);
        mUIShow.setTvErrorMsg("请输入搜索条件...");
        mUIShow.setReLoad(false);
        mUIShow.showError();
    }

    private void initPupoWindow() {
        mSelectTypePupop = new PopupWindow(mAct);
        mSelectTypePupop.setBackgroundDrawable(new BitmapDrawable());
        mSelectTypePupop.setOutsideTouchable(true);
        mSelectTypePupop.setFocusable(true);
        mSelectTypePupop.setWidth(CommonUtil.dip2px(88));
        mSelectTypePupop.setHeight(CommonUtil.dip2px(48 + 1 + 48 + 8));

        View mSelectView = getSelectView();

        mSelectTypePupop.setContentView(mSelectView);

        mSelectTypePupop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                tvType.setSelected(false);
            }
        });
    }

    public void showPupopWindow() {
        mSelectTypePupop.showAsDropDown(tvType);
    }

    private View getSelectView() {
        View mView = getLayoutInflater().inflate(R.layout.act_registor_select_school_pupop_layout, null);
        ListView lvSchool = (ListView) mView.findViewById(R.id.lv_school);
        lvSchool.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectTypePupop.dismiss();
                SearchType type = SearchType.getType(mSearchInfos.get(position));
                if (lastSearchType != type) {
                    tvType.setText(mSearchInfos.get(position));
                    lastSearchType = type;
                    etSearch.setText("");
                    showNull();
                }
            }
        });
        lvSchool.setAdapter(mSearchAdapter);
        return mView;
    }

    @Override
    public void initListener() {
        tvType.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        pullLayout.setOnRefreshListener(mSearchServer);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() <= 0) {
                    tvSearch.setEnabled(false);
                } else {
                    tvSearch.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == tvType) {
            tvType.setSelected(true);
            showPupopWindow();
        } else if (v == tvSearch) {
            if (match()) {
                switch (lastSearchType) {
                    case goods:
                        lvInfo.setAdapter(mSearchServer.getGoodsListAdapter());
                        mSearchServer.loadGoodsList(mSearchInfo, "", mUIShow);
                        break;
                    case shanghu:
                        lvInfo.setAdapter(mSearchServer.getUserStuListAdapter());
                        mSearchServer.queryUserStuList(mSearchInfo,mUIShow);
                        break;
                }
            }
        }
    }

    /**
     * 检查用户搜索条件输入正确性
     *
     * @return
     */
    private boolean match() {
        mSearchInfo = etSearch.getText().toString();
        if (TextUtils.isEmpty(mSearchInfo)) {
            showToast("请输入搜索条件");
            return false;
        }
        return true;
    }

    public enum SearchType {
        goods("商品"),
        shanghu("商户");
        private final String mType;

        SearchType(String type) {
            this.mType = type;
        }

        public static SearchType getType(String type) {
            if (TextUtils.isEmpty(type)) {
                return goods;
            }
            for (int i = 0; i < values().length; i++) {
                if (type.equals(values()[i].mType)) {
                    return values()[i];
                }
            }
            return goods;
        }
    }
}
