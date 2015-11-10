package com.peoit.android.studentuga.ui.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.peoit_lib.adapter.BaseListAdapter;
import com.peoit.android.peoit_lib.adapter.BaseViewHolder;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.GoodDetInfo;
import com.peoit.android.studentuga.entity.GoodsStyleInfo;
import com.peoit.android.studentuga.entity.ShopCarInfo;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.net.server.GoodsServer;
import com.peoit.android.studentuga.net.server.ShopCarServer;
import com.peoit.android.studentuga.ui.HomeActivity;
import com.peoit.android.studentuga.ui.dialog.SelectGoodsStyleDialog;

import java.util.List;

/**
 * author:libo
 * time:2015/9/11
 * E-mail:boli_android@163.com
 * last: ...
 */
public class ShopCarContentAdapter1 extends BaseListAdapter<ShopCarInfo> {

    private OnDataChangeListener mOndataChangeListener;

    private OnSuccessCallBack mSuccessCallBack;

    public void setOnDelCallBack(OnSuccessCallBack callBack) {
        this.mSuccessCallBack = callBack;
    }

    public ShopCarContentAdapter1(Activity mAc) {
        super(mAc);
    }

    @Override
    protected BaseViewHolder getBaseViewHolder() {
        return new ViewHolder();
    }

    @Override
    protected void initView(int position, final ShopCarInfo data, ViewHolderBase holderBase, View convertView) {
        final ViewHolder mHolder = (ViewHolder) holderBase;
        Glide.with(mAc).load(NetConstants.IMG_HOST + data.getImgurl()).into(mHolder.ivGoodsIcon);
        mHolder.llSelectGoods.setSelected(data.isSelect());
        if (data.isChange()) {
            mHolder.llChangeGoodsStyle.setVisibility(View.VISIBLE);
            mHolder.llGoodsDisplay.setVisibility(View.INVISIBLE);
        } else {
            mHolder.llChangeGoodsStyle.setVisibility(View.INVISIBLE);
            mHolder.llGoodsDisplay.setVisibility(View.VISIBLE);
        }
        mHolder.llSelectGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHolder.llSelectGoods.setSelected(!mHolder.llSelectGoods.isSelected());
                data.setSelect(mHolder.llSelectGoods.isSelected());
                onDataChange();
            }
        });
        mHolder.tvCurPrice.setText("￥" + data.getPrice());
        mHolder.tvGoodsContent.setText(data.getSpec());
        mHolder.tvGoodsCount.setText("数量:" + data.getNumber());
        mHolder.tvGoodsCountAtEdit.setText(data.getNumber() + "");
        mHolder.tvDelGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialog = new AlertDialog.Builder(mAc)
                        .setMessage("确认删除" + data.getTitle())
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                new ShopCarServer((ActivityBase) mAc)
                                        .requestDelGoodsAtShopCar(data.getId() + "", new BaseServer.OnSuccessCallBack() {
                                            @Override
                                            public void onSuccess(int mark) {
                                                if (mSuccessCallBack != null) {
                                                    mSuccessCallBack.onSuccess(data);
                                                }
                                                onDataChange();
                                            }
                                        });
                            }
                        }).setTitle("提示").show();
            }
        });
        mHolder.ivGoodsCountAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.toAddCount();
                onDataChange();
                notifyDataSetChanged();
            }
        });
        mHolder.ivGoodsCountMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!data.toMinusCountAndIsZero()) {
                    notifyDataSetChanged();
                    onDataChange();
                } else {
                    final AlertDialog dialog = new AlertDialog.Builder(mAc)
                            .setMessage("确认删除" + data.getTitle())
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    new ShopCarServer((ActivityBase) mAc)
                                            .requestDelGoodsAtShopCar(data.getOrderId() + "", new BaseServer.OnSuccessCallBack() {
                                                @Override
                                                public void onSuccess(int mark) {
                                                    if (mSuccessCallBack != null) {
                                                        mSuccessCallBack.onSuccess(data);
                                                    }
                                                    onDataChange();
                                                }
                                            });
                                }
                            }).setTitle("提示").show();
                }
            }
        });
        mHolder.tvGoodsStyle.setText(data.getSpec());
        mHolder.llGoodsStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditGoods(data);
            }
        });
    }

    private String mStyle;
    private boolean mIsAll;
    private int mposition;

    private void onEditGoods(final ShopCarInfo data) {
        final SelectGoodsStyleDialog dialog = new SelectGoodsStyleDialog(mAc);
        dialog.setmGoodsCount(data.getNumber());
        dialog.setTitle(data.getTitle());
        dialog.setImgUrl(NetConstants.IMG_HOST + data.getImgurl());

        dialog.setOnSelectGoodsStyleListener(new GoodsStyleAdapter.OnGoodsStyleListener() {
            @Override
            public void onGoodsStyle(boolean isAll, int position, String style) {
                mIsAll = isAll;
                mStyle = style;
                mposition = position;
            }
        });
        dialog.setOkListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mIsAll) {
                    List<GoodsStyleInfo> info = dialog.getmGoodsStyleList();
                    String str = info.get(mposition).getName();
                    ((ActivityBase) mAc).showToast("请选择" + str);
                    return;
                }
                dialog.dismiss();
                data.setSpec(mStyle);
                data.setNumber(dialog.getmGoodsCount());
                notifyDataSetChanged();
            }
        });
        ((ActivityBase) mAc).showLoadingDialog("正在获取样式信息...");
        new GoodsServer((HomeActivity) mAc).loadGoodsDet(data.getCommodityId() + "", null, null, new GoodsServer.OnGoodsDetCallBack() {

            @Override
            public void onDetBack(GoodDetInfo info) {

            }

            @Override
            public void onGoodsStyle(List<GoodsStyleInfo> info) {
                dialog.setGoodsStyleList(info);
                dialog.show();
            }
        });
    }

    private class ViewHolder extends BaseViewHolder {
        private LinearLayout llSelectGoods;
        private ImageView ivGoodsIcon;
        private LinearLayout llGoodsDisplay;
        private TextView tvGoodsTitle;
        private TextView tvGoodsContent;
        private TextView tvCurPrice;
        private TextView tvRealPrice;
        private TextView tvGoodsRemark;
        private TextView tvGoodsCount;
        private LinearLayout llChangeGoodsStyle;
        private TextView tvGoodsCountAtEdit;
        private LinearLayout llGoodsStyle;
        private TextView tvGoodsStyle;
        private TextView tvDelGoods;
        private ImageView ivGoodsCountAdd;
        private ImageView ivGoodsCountMinus;

        @Override
        protected int getConvertViewByLayoutId() {
            return R.layout.frag_shop_car_list_edit_item;
        }

        @Override
        public void inflaer(View convertView) {
            llSelectGoods = (LinearLayout) convertView.findViewById(R.id.ll_selectGoods);
            ivGoodsIcon = (ImageView) convertView.findViewById(R.id.iv_goodsIcon);
            llGoodsDisplay = (LinearLayout) convertView.findViewById(R.id.ll_goodsDisplay);
            tvGoodsTitle = (TextView) convertView.findViewById(R.id.tv_goodsTitle);
            tvGoodsContent = (TextView) convertView.findViewById(R.id.tv_goodsContent);
            tvCurPrice = (TextView) convertView.findViewById(R.id.tv_curPrice);
            tvRealPrice = (TextView) convertView.findViewById(R.id.tv_realPrice);
            tvGoodsRemark = (TextView) convertView.findViewById(R.id.tv_goodsRemark);
            tvGoodsCount = (TextView) convertView.findViewById(R.id.tv_goodsCount);
            llChangeGoodsStyle = (LinearLayout) convertView.findViewById(R.id.ll_changeGoodsStyle);
            ivGoodsCountMinus = (ImageView) convertView.findViewById(R.id.iv_goodsCountMinus);
            tvGoodsCountAtEdit = (TextView) convertView.findViewById(R.id.tv_goodsCountAtEdit);
            ivGoodsCountAdd = (ImageView) convertView.findViewById(R.id.iv_goodsCountAdd);
            llGoodsStyle = (LinearLayout) convertView.findViewById(R.id.ll_goodsStyle);
            tvGoodsStyle = (TextView) convertView.findViewById(R.id.tv_goodsStyle);
            tvDelGoods = (TextView) convertView.findViewById(R.id.tv_delGoods);
        }
    }

    private void onDataChange() {
        if (mOndataChangeListener != null) {
            mOndataChangeListener.onDataChange();
        }
    }

    public void setOnDataChengeListener(OnDataChangeListener mListener) {
        this.mOndataChangeListener = mListener;
    }

    public interface OnDataChangeListener {
        void onDataChange();
    }

    public interface OnSuccessCallBack {
        void onSuccess(ShopCarInfo info);
    }
}
