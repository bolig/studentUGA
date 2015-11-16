package com.peoit.android.studentuga.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.entity.GoodsStyleInfo;
import com.peoit.android.studentuga.ui.adapter.GoodsStyleAdapter;

import java.util.List;

/**
 * author:libo
 * time:2015/10/16
 * E-mail:boli_android@163.com
 * last: ...
 */
public class SelectGoodsStyleDialog extends Dialog implements View.OnClickListener {
    private final Activity mAc;

    private Animation anim_tans_in;
    private Animation anim_tans_out;
    private Animation anim_alpha_in;
    private Animation anim_alpha_out;

    private boolean mCanceledOnTouchOutside;
    private View.OnClickListener mOkListener;
    private View.OnClickListener mCancelListener;
    private GoodsStyleAdapter mGoodsStyleAdapter;
    private List<GoodsStyleInfo> mGoodsStyleList;
    private GoodsStyleAdapter.OnGoodsStyleListener mGoodsStyleListener;
    private String mGoodsTitle;
    private String mImageUrl;
    private int mGoodsCount = 1;
    private ImageView ivClose;
    private int mButtonWidth;
    private LinearLayout llNoStyle;
    public int mMaxCount = Integer.MAX_VALUE;

    public List<GoodsStyleInfo> getmGoodsStyleList() {
        return mGoodsStyleList;
    }

    public SelectGoodsStyleDialog(Activity mAc) {
        super(mAc, R.style.dialog);
        this.mAc = mAc;
        init();
    }

    private void init() {
        anim_tans_in = AnimationUtils.loadAnimation(getContext(), R.anim.anim_trans_in);
        anim_tans_out = AnimationUtils.loadAnimation(getContext(), R.anim.anim_trans_out);
        anim_alpha_in = AnimationUtils.loadAnimation(getContext(), R.anim.anim_alpha_in);
        anim_alpha_out = AnimationUtils.loadAnimation(getContext(), R.anim.anim_alpha_out);

        mButtonWidth = CommonUtil.w_screeen / 2;
    }

    private View viewBg;
    private LinearLayout llBody;
    private View viewTitleBg;
    private TextView tvGoodsName;
    private ImageView ivGoodsCountMinus;
    private TextView tvGoodsCountAtEdit;
    private ImageView ivGoodsCountAdd;
    private ImageView ivGoodsImg;
    private ListView lvStyle;
    private TextView tvCancel;
    private TextView tvOk;

    private void assignViews() {
        viewBg = findViewById(R.id.view_bg);
        llBody = (LinearLayout) findViewById(R.id.ll_body);
        viewTitleBg = findViewById(R.id.view_title_bg);
        tvGoodsName = (TextView) findViewById(R.id.tv_goodsName);
        ivGoodsCountMinus = (ImageView) findViewById(R.id.iv_goodsCountMinus);
        tvGoodsCountAtEdit = (TextView) findViewById(R.id.tv_goodsCountAtEdit);
        ivGoodsCountAdd = (ImageView) findViewById(R.id.iv_goodsCountAdd);
        ivGoodsImg = (ImageView) findViewById(R.id.iv_goods_img);
        lvStyle = (ListView) findViewById(R.id.lv_style);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvOk = (TextView) findViewById(R.id.tv_ok);

        ivClose = (ImageView) findViewById(R.id.iv_close);
        llNoStyle = (LinearLayout) findViewById(R.id.ll_noStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_goods_style);
        // 经测试在代码里直接声明透明状态栏更有效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        initView();
        initListener();
    }

    private void initListener() {
        viewBg.setOnClickListener(this);
        viewTitleBg.setOnClickListener(this);
        tvCancel.setOnClickListener(mCancelListener != null ? mCancelListener : new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvOk.setOnClickListener(mOkListener);
        ivGoodsCountAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGoodsCount == mMaxCount) {
                    Toast.makeText(getContext(), "已达最大库存", Toast.LENGTH_SHORT).show();
                    ivGoodsCountAdd.setEnabled(false);
                } else {
                    mGoodsCount++;
                    tvGoodsCountAtEdit.setText(mGoodsCount + "");
                }
                if (mMaxCount <= 1) {
                    ivGoodsCountMinus.setEnabled(false);
                } else {
                    ivGoodsCountMinus.setEnabled(true);
                }
            }
        });
        ivGoodsCountMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGoodsCount > 0) {
                    mGoodsCount--;
                    tvGoodsCountAtEdit.setText(mGoodsCount + "");
                } else {
                    ivGoodsCountMinus.setEnabled(false);
                }
                ivGoodsCountAdd.setEnabled(true);
            }
        });
        if (mMaxCount == 1) {
            ivGoodsCountMinus.setEnabled(false);
        }
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void initView() {
        assignViews();

        tvGoodsName.setText(mGoodsTitle);
        tvGoodsCountAtEdit.setText(mGoodsCount + "");

        if (!TextUtils.isEmpty(mImageUrl))
            Glide.with(mAc).load(mImageUrl).error(R.drawable.noproduct).into(ivGoodsImg);

        mGoodsStyleAdapter = new GoodsStyleAdapter(mAc);
        mGoodsStyleAdapter.upDateList(mGoodsStyleList);
        if (mGoodsStyleList == null || mGoodsStyleList.size() == 0) {
            llNoStyle.setVisibility(View.VISIBLE);
        } else {
            llNoStyle.setVisibility(View.INVISIBLE);
        }
        mGoodsStyleAdapter.setOnGoodsStyleListener(mGoodsStyleListener != null ?
                mGoodsStyleListener :
                new GoodsStyleAdapter.OnGoodsStyleListener() {
                    @Override
                    public void onGoodsStyle(boolean isAll, int errorPosition, String style) {
                        if (!isAll) {
                            Toast.makeText(mAc, "请选择" + mGoodsStyleList.get(errorPosition).getName(), Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(mAc, "您选择" + style, Toast.LENGTH_SHORT).show();
                    }
                });
        lvStyle.setAdapter(mGoodsStyleAdapter);

        ViewGroup.LayoutParams params = tvCancel.getLayoutParams();
        ViewGroup.LayoutParams param1 = tvOk.getLayoutParams();

        params.width = mButtonWidth;
        param1.width = mButtonWidth;
    }

    @Override
    public void show() {
        super.show();
        llBody.startAnimation(anim_tans_in);
    }

    @Override
    public void dismiss() {
        anim_tans_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                SelectGoodsStyleDialog.super.dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        llBody.startAnimation(anim_tans_out);
    }

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        this.mCanceledOnTouchOutside = cancel;
        super.setCanceledOnTouchOutside(cancel);
    }

    public void setGoodsStyleList(List<GoodsStyleInfo> infos) {
        this.mGoodsStyleList = infos;
        if (mGoodsStyleAdapter != null)
            mGoodsStyleAdapter.upDateList(mGoodsStyleList);
        if (llNoStyle != null) {
            if (mGoodsStyleList == null || mGoodsStyleList.size() == 0) {
                llNoStyle.setVisibility(View.VISIBLE);
            } else {
                llNoStyle.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void setCancelListener(View.OnClickListener listener) {
        this.mCancelListener = listener;
        if (tvCancel != null) {
            tvCancel.setOnClickListener(listener);
        }
    }

    public void setOkListener(View.OnClickListener listener) {
        this.mOkListener = listener;
        if (tvOk != null) {
            tvOk.setOnClickListener(listener);
        }
    }

    public void setOnSelectGoodsStyleListener(GoodsStyleAdapter.OnGoodsStyleListener listener) {
        this.mGoodsStyleListener = listener;
        if (mGoodsStyleAdapter != null) {
            mGoodsStyleAdapter.setOnGoodsStyleListener(mGoodsStyleListener);
        }
    }

    public void setTitle(String title) {
        this.mGoodsTitle = title;
        if (tvGoodsName != null) {
            tvGoodsName.setText(title);
        }
    }

    public void setImgUrl(String imgUrl) {
        this.mImageUrl = imgUrl;
        if (ivGoodsImg != null) {
            Glide.with(mAc).load(imgUrl).error(R.drawable.noproduct).into(ivGoodsImg);
        }
    }

    public int getmGoodsCount() {
        return mGoodsCount;
    }

    public void setmGoodsCount(int count) {
        this.mGoodsCount = count;
        if (tvGoodsCountAtEdit != null)
            tvGoodsCountAtEdit.setText(mGoodsCount + "");
    }

    @Override
    public void onClick(View v) {
        if (v == viewBg) {
            if (mCanceledOnTouchOutside) {
                dismiss();
            }
        } else if (v == viewTitleBg) {
            if (mCanceledOnTouchOutside) {
                dismiss();
            }
        }
    }
}
