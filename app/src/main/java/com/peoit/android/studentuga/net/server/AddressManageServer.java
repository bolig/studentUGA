package com.peoit.android.studentuga.net.server;

import android.app.Activity;
import android.content.Intent;

import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.peoit_lib.uishow.MyUIShow;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.AddressInfo;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.ui.adapter.AddressManageAdapter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * author:libo
 * time:2015/9/30
 * E-mail:boli_android@163.com
 * last: ...
 */
public class AddressManageServer extends BaseServer {

    private AddressManageAdapter mAddressAdapter;

    public AddressManageServer(ActivityBase activityBase) {
        super(activityBase);
    }

    public AddressManageAdapter getAddressAdapter() {
        mAddressAdapter = new AddressManageAdapter(mActBase.getActivity());
        return mAddressAdapter;
    }

    public void loadAddressList(final OnSuccessCallBack onSuccessCallBack) {
        RequestParams params = getSignRequestParams();
        if (params == null) {
            return;
        }
        ((MyUIShow) mActBase.getUIShowPresenter()
                .getUIShowView())
                .setErrorImag(R.drawable.nodeliveryaddress)
                .setErrorMsg("您还没有收货地址, 赶紧添加一个吧");
        mActBase.getUIShowPresenter().showLoading();
        request(NetConstants.NET_ADDRESS_QUERY_LIST, AddressInfo.class, params, new BaseCallBack<AddressInfo>() {
            @Override
            public void onResponseSuccessList(List<AddressInfo> result) {
                if (result == null || result.isEmpty()) {
                    mActBase.getUIShowPresenter().showError();
                } else {
                    mActBase.getUIShowPresenter().showData();
                    if (mAddressAdapter != null) {
                        Collections.sort(result, new AddressComparator());
                        mAddressAdapter.upDateList(result);
                        if (onSuccessCallBack != null) {
                            onSuccessCallBack.onSuccess(0);
                        }
                    }
                }
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
                mActBase.getUIShowPresenter().showError();
            }
        });
    }

    /**
     * 请求添加收货地址
     *
     * @param address       详细地址
     * @param province      省份
     * @param municipality  市
     * @param county        区
     * @param consignorName 收货人姓名
     * @param postalCode    邮编
     * @param telephone     手机号
     * @param idcard        证件号
     * @param isDefault     是否是默认地址
     */
    public void requestAddAddress(String address,
                                  String province,
                                  String municipality,
                                  String county,
                                  String consignorName,
                                  String postalCode,
                                  String telephone,
                                  String idcard,
                                  boolean isDefault) {
        RequestParams params = getSignRequestParams();
        if (params == null) {
            return;
        }
        params.put("address", address);
        params.put("province", province);
        params.put("municipality", municipality);
        params.put("county", county);
        params.put("consignorName", consignorName);
        params.put("postalCode", postalCode);
        params.put("telephone", telephone);
        params.put("idcard", idcard);
        params.put("isdefault", isDefault ? "Y" : "N");
        mActBase.showLoadingDialog("正在保存地址信息...");
        request(NetConstants.NET_ADDRESS_ADD, null, params, new BaseCallBack() {
            @Override
            public void onFinish() {
                mActBase.hideLoadingDialog();
            }

            @Override
            public void onResponseSuccess(Object result) {
                Intent intent = new Intent();
                mActBase.getActivity().setResult(Activity.RESULT_OK, intent);
                mActBase.showToast("保存成功");
                mActBase.getActivity().finish();
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
            }
        });
    }

    /**
     * 请求删除地址信息
     *
     * @param id
     */
    public void requestDelAddress(String id) {
        RequestParams params = getSignRequestParams();
        if (params == null) {
            return;
        }
        params.put("id", id);
        mActBase.showLoadingDialog("正在删除地址信息...");
        request(NetConstants.NET_ADDRESS_DEL, null, params, new BaseCallBack() {
            @Override
            public void onFinish() {
                mActBase.hideLoadingDialog();
            }

            @Override
            public void onResponseSuccess(Object result) {
                mActBase.showToast("删除成功");
                loadAddressList(null);
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
            }
        });
    }

    /**
     * 请求修改收货地址
     *
     * @param info
     */
    public void requestModAddress(AddressInfo info, final boolean isFinish) {
        RequestParams params = getSignRequestParams();
        if (params == null) {
            return;
        }
        params.put("id", info.getId() + "");
        params.put("address", info.getAddress());
        params.put("province", info.getProvince());
        params.put("municipality", info.getMunicipality());
        params.put("county", info.getCounty());
        params.put("consignorName", info.getConsignorName());
        params.put("postalCode", info.getPostalCode());
        params.put("telephone", info.getTelephone());
        params.put("idcard", "11");
        params.put("isdefault", info.getIsdefault());
        mActBase.showLoadingDialog("正在保存地址信息...");
        request(NetConstants.NET_ADDRESS_UPDATA, null, params, new BaseCallBack() {
            @Override
            public void onFinish() {
                mActBase.hideLoadingDialog();
            }

            @Override
            public void onResponseSuccess(Object result) {
                mActBase.showToast("修改成功");
                if (isFinish) {
                    mActBase.getActivity().finish();
                } else {
                    loadAddressList(null);
                }
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
            }
        });
    }

    public void requestDefaultAddress(final OnSuccessCallBack callBack) {
        RequestParams params = getSignRequestParams();
        if (params == null) {
            return;
        }
        request(NetConstants.NET_DEFAULT_ADDRESS, AddressInfo.class, params, new BaseCallBack<AddressInfo>() {
            @Override
            public void onStart() {
                if (callBack != null) {
                    callBack.onSuccess(0);
                }
            }

            @Override
            public void onResponseSuccess(AddressInfo result) {
                CommonUtil.mDefaultAddress = result;
                if (result == null || result.isNull()) {
                    if (callBack != null) {
                        callBack.onSuccess(2);
                    }
                } else {
                    if (callBack != null) {
                        callBack.onSuccess(1);
                    }
                }
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                if (callBack != null) {
                    callBack.onSuccess(2);
                }
            }
        });
    }

    public static class AddressComparator implements Comparator<AddressInfo> {
        @Override
        public int compare(AddressInfo lhs, AddressInfo rhs) {
            return lhs.isDefault() ? 1 : 0;
        }
    }
}
