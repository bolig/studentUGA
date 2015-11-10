package com.peoit.android.studentuga.ui.adapter;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.peoit_lib.adapter.BaseListAdapter;
import com.peoit.android.peoit_lib.adapter.BaseViewHolder;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.entity.ShopCarInfo;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.net.server.ShopCarServer;
import com.peoit.android.studentuga.view.NoSrcollListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车列表adapter
 * <p/>
 * author:libo
 * time:2015/9/11
 * E-mail:boli_android@163.com
 * last: ...
 */
public class ShopCarAdapter1 extends BaseListAdapter<ShopCarInfo> {

    private List<ShopCarInfo> selectShopCarGoodsInfos;
    private OnShopCarGoodsChangeListener mOnShopCarGoodsChangeListener;
    private boolean isSelectAllGoods = false;
    private BaseServer.OnSuccessCallBack mOnSuccessCallBack;

    public ShopCarAdapter1(Activity mAc) {
        super(mAc);
    }

    public ShopCarAdapter1(Fragment fragment) {
        super(fragment);
    }

    @Override
    protected BaseViewHolder getBaseViewHolder() {
        return new ViewHolder();
    }

    private Handler mChangeShopGoodsHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (mOnShopCarGoodsChangeListener != null) {
                mOnShopCarGoodsChangeListener.onGoodsChange(selectShopCarGoodsInfos, getDatas(), isSelectAllGoods);
            }
        }
    };

    /**
     * 当数据发生改变时回调改变数据
     */
    public void changeShopGoods() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (datas == null || datas.size() == 0) {
                    isSelectAllGoods = false;
                    if (selectShopCarGoodsInfos != null && selectShopCarGoodsInfos.size() > 0) {
                        selectShopCarGoodsInfos.clear();
                    }
                    mChangeShopGoodsHandler.sendEmptyMessage(0);
                    return;
                }
                if (selectShopCarGoodsInfos == null) {
                    selectShopCarGoodsInfos = new ArrayList<>();
                }
                if (selectShopCarGoodsInfos.size() > 0) {
                    selectShopCarGoodsInfos.clear();
                }
                isSelectAllGoods = true;
                for (ShopCarInfo info : datas) {
                    if (!info.isSelect()) {
                        isSelectAllGoods = false;
                    } else {
                        selectShopCarGoodsInfos.add(info);
                    }
                }
                mChangeShopGoodsHandler.sendEmptyMessage(0);
            }
        }).start();
    }

    @Override
    protected void initView(int position, final ShopCarInfo data, ViewHolderBase holderBase, View convertView) {
        final ViewHolder mHolder = (ViewHolder) holderBase;

        data.change();

        mHolder.tvShopName.setText(data.getTitle());
        List<ShopCarInfo> list = new ArrayList<>();
        list.add(data);
        mHolder.contentAdapter.upDateList(list);
        mHolder.tvShopName.setText(data.getTitle());
        mHolder.tvEditGoodsAtShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editGoodsText = mHolder.tvEditGoodsAtShop.getText().toString();
                final boolean isEdit = "编辑".equals(editGoodsText);
                mHolder.tvEditGoodsAtShop.setText(isEdit ? "保存" : "编辑");
                data.setChange(isEdit);
                if (!isEdit) {
                    if (!data.isDataNoChange()) {
                        new ShopCarServer((ActivityBase) mAc).requestUpDataGoodsAtShopCar(data.getId() + ""
                                , data.getNumber() + ""
                                , data.getSpec(), new BaseServer.OnSuccessCallBack() {
                            @Override
                            public void onSuccess(int mark) {
                                if (mOnSuccessCallBack != null) {
                                    mOnSuccessCallBack.onSuccess(mark);
                                }
                                if (mark == 2) {
                                    data.restore();
                                    mHolder.contentAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                } else {
                    mHolder.contentAdapter.notifyDataSetChanged();
                }
            }
        });
        mHolder.ivSelectAllGoodsAtShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHolder.ivSelectAllGoodsAtShop.setSelected(!mHolder.ivSelectAllGoodsAtShop.isSelected());
                data.setSelect(mHolder.ivSelectAllGoodsAtShop.isSelected());
                notifyDataSetChanged();
                changeShopGoods();
            }
        });
        mHolder.contentAdapter.setOnDelCallBack(new ShopCarContentAdapter1.OnSuccessCallBack() {
            @Override
            public void onSuccess(ShopCarInfo info) {
                removeDataItem(info);
            }
        });
        mHolder.ivSelectAllGoodsAtShop.setSelected(data.isSelect());
    }

    public class ViewHolder extends BaseViewHolder {
        private ImageView ivSelectAllGoodsAtShop;
        private LinearLayout llToShop;
        private TextView tvShopName;
        private TextView tvEditGoodsAtShop;
        private NoSrcollListView nlvGoodsAtShop;

        private ShopCarContentAdapter1 contentAdapter;

        @Override
        protected int getConvertViewByLayoutId() {
            return R.layout.frag_shop_car_list_item;
        }

        @Override
        public void inflaer(View convertView) {
            ivSelectAllGoodsAtShop = (ImageView) convertView.findViewById(R.id.iv_selectAllGoodsAtShop);
            llToShop = (LinearLayout) convertView.findViewById(R.id.ll_toShop);
            tvShopName = (TextView) convertView.findViewById(R.id.tv_shopName);
            tvEditGoodsAtShop = (TextView) convertView.findViewById(R.id.tv_editGoodsAtShop);
            nlvGoodsAtShop = (NoSrcollListView) convertView.findViewById(R.id.nlv_goodsAtShop);

            contentAdapter = new ShopCarContentAdapter1(mAc);
            contentAdapter.setOnDataChengeListener(new ShopCarContentAdapter1.OnDataChangeListener() {
                @Override
                public void onDataChange() {
                    changeShopGoods();
                    notifyDataSetChanged();
                }
            });
            nlvGoodsAtShop.setAdapter(contentAdapter);
        }
    }

    public void setUpdataSuccessCallBack(BaseServer.OnSuccessCallBack callBack) {
        this.mOnSuccessCallBack = callBack;
    }

    public void setOnShopCarGoodsChangeListener(OnShopCarGoodsChangeListener mListener) {
        this.mOnShopCarGoodsChangeListener = mListener;
    }

    public interface OnShopCarGoodsChangeListener {
        void onGoodsChange(List<ShopCarInfo> selectShopCarGoods, List<ShopCarInfo> allGoods, boolean isSelectAllGoods);
    }
}
