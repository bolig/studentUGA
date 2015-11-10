package com.peoit.android.studentuga.net.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.GoodDetInfo;
import com.peoit.android.studentuga.entity.GoodsInfo;
import com.peoit.android.studentuga.entity.GoodsStyleInfo;
import com.peoit.android.studentuga.entity.GoodsTypeInfo;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.ui.adapter.GoodsListAdapter;
import com.peoit.android.studentuga.ui.adapter.SortTypeAdapter;
import com.peoit.android.studentuga.ui.showUI.SimpleShowUiShow;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayout;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayoutDirection;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * author:libo
 * time:2015/10/10
 * E-mail:boli_android@163.com
 * last: ...
 */
public class GoodsServer extends BaseServer implements SwipyRefreshLayout.OnRefreshListener {
    private SortTypeAdapter mSortTypeAdapter;
    private GoodsListAdapter mGoodsListAdapter;
    private RequestParams params;

    public GoodsServer(ActivityBase activityBase) {
        super(activityBase);
    }

    public SortTypeAdapter getSortTypeAdapter() {
        mSortTypeAdapter = new SortTypeAdapter(mActBase.getActivity());
        return mSortTypeAdapter;
    }

    /**
     * 获取商品类型
     */
    public void loadGoodsType(final SimpleShowUiShow mUIShow) {
        mUIShow.showLoading();
        request(NetConstants.NET_GOODS_TYPE, GoodsTypeInfo.class, getRequestParams(), new BaseCallBack<GoodsTypeInfo>() {
            @Override
            public void onResponseSuccessList(List<GoodsTypeInfo> result) {
                mUIShow.showData();
                if (mSortTypeAdapter != null) {
                    mSortTypeAdapter.upDateList(result);
                }
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
                mUIShow.showError();
            }
        });
    }

    public GoodsListAdapter getGoodsListAdapter() {
        mGoodsListAdapter = new GoodsListAdapter(mActBase.getActivity());
        return mGoodsListAdapter;
    }

    /**
     * 获取某个类型的商品
     *
     * @param typeid     商品类型id
     * @param title      商品标题(非必须,模糊查询)
     * @param queryphone 商品所属的人账户(电话号码，完全匹配查询)
     * @param oderType   排序方式 	//不填写默认按照创建时间顺序排列
     *                   //0:创建 时间倒序
     *                   //1:销量 从大到小
     *                   //2:销量 从小到大
     *                   //3:价格 从大到小
     *                   //4:价格 从小到大
     * @param pageNo     分页参数从1开始，每页20条数据
     */
    public void loadGoodsList(String typeid,
                              String title,
                              String queryphone,
                              String oderType,
                              String pageNo, final SimpleShowUiShow mUIShow) {
        params = getRequestParams();
        params.put("typeid", typeid);
        params.put("title", title);
        params.put("queryphone", queryphone);
        params.put("oderType", oderType);
        params.put("pageNo", "1");
        params.put("usertype", 2 + "");
        mUIShow.showLoading();
        request(NetConstants.NET_GOODS_LIST, GoodsInfo.class, params, new BaseCallBack<GoodsInfo>() {
            @Override
            public void onResponseSuccessList(List<GoodsInfo> result) {
                if (result == null || result.size() == 0) {
                    mUIShow.setTvErrorMsg("暂无此类商品");
                    mUIShow.showError();
                } else {
                    mGoodsListAdapter.upDateList(result);
                    mUIShow.showData();
                    mNo++;
                }
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
                mUIShow.setTvErrorMsg("加载失败, 请稍后再试");
                mUIShow.showError();
            }
        });
    }

    private int mNo = 1;

    /**
     * 获取某个类型的商品
     */
    public void loadGoodsList(final SwipyRefreshLayout layout, final SwipyRefreshLayoutDirection direction) {
        params.put("pageNo", direction == SwipyRefreshLayoutDirection.BOTTOM ? mNo + "" : "1");
        request(NetConstants.NET_GOODS_LIST, GoodsInfo.class, params, new BaseCallBack<GoodsInfo>() {
            @Override
            public void onFinish() {
                layout.setRefreshing(false);
            }

            @Override
            public void onResponseSuccessList(List<GoodsInfo> result) {
                if (result == null || result.size() == 0) {
                    mActBase.showToast("暂无更多商品了");
                } else {
                    switch (direction) {
                        case TOP:
                            mGoodsListAdapter.addHeadDataList(result);
                            break;
                        case BOTTOM:
                            mNo++;
                            mGoodsListAdapter.addFootDataList(result);
                            break;
                    }
                }
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
                mActBase.showToast("商品加载失败");
            }
        });
    }

    /**
     * 下载商品详情
     *
     * @param id
     */
    public void loadGoodsDet(String id, String uid, final SimpleShowUiShow mUIShow, final OnGoodsDetCallBack callBack) {
        RequestParams params = getRequestParams();
        params.put("id", id);
        params.put("uid", uid);
        if (mUIShow != null) {
            mUIShow.showLoading();
        }
        request(NetConstants.NET_GOODS_DET, GoodDetInfo.class, params, new BaseCallBack<GoodDetInfo>() {
            @Override
            public void onFinish() {
                mActBase.hideLoadingDialog();
            }

            @Override
            public void onParseCallBackData(String response) {
                List<GoodsStyleInfo> infos = null;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String json = jsonObject.getString("obj1");
                    Type type = new TypeToken<List<GoodsStyleInfo>>() {
                    }.getType();
                    infos = new Gson().fromJson(json, type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (callBack != null) {
                    callBack.onGoodsStyle(infos);
                }
            }

            @Override
            public void onResponseSuccess(GoodDetInfo result) {
                if (mUIShow != null) {
                    mUIShow.showData();
                }
                if (callBack != null) {
                    callBack.onDetBack(result);
                }
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
                if (mUIShow != null) {
                    mUIShow.showError();
                }
            }
        });
    }

    @Override
    public void onRefresh(SwipyRefreshLayout layout, SwipyRefreshLayoutDirection direction) {
        loadGoodsList(layout, direction);
    }

    public interface OnGoodsDetCallBack {

        void onDetBack(GoodDetInfo info);

        void onGoodsStyle(List<GoodsStyleInfo> info);
    }
}
