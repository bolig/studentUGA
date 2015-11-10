package com.peoit.android.studentuga.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.peoit.android.studentuga.R;

/**
 * author:libo
 * time:2015/9/9
 * E-mail:boli_android@163.com
 * last: ...
 */
public class MySliderTextView extends BaseSliderView {

    public MySliderTextView(Context context) {
        super(context);
    }

    private ImageView daimajiaSliderImage;
    private ProgressBar loadingBar;
    private LinearLayout llMark;
    private TextView description;

    @Override
    public View getView() {

        View view = LayoutInflater.from(mContext).inflate(R.layout.render_type_text, null);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSliderClickListener != null) {
                    mOnSliderClickListener.onSliderClick(MySliderTextView.this);
                }
            }
        });

        daimajiaSliderImage = (ImageView) view.findViewById(R.id.daimajia_slider_image);
        loadingBar = (ProgressBar) view.findViewById(R.id.loading_bar);
        llMark = (LinearLayout) view.findViewById(R.id.description_layout);
        description = (TextView) view.findViewById(R.id.description);

        description.setText(getDescription());
        loadImage(daimajiaSliderImage);

        return view;
    }

    private void loadImage(ImageView daimajiaSliderImage) {
        if (!TextUtils.isEmpty(getUrl())) {
            DrawableTypeRequest<String> imageRequestManager = null;
            try {
                imageRequestManager = Glide.with(mContext).load(getUrl());
            } catch (Exception e) {
                return;
            }

            if (getEmpty() != 0) {
                imageRequestManager.placeholder(getEmpty());
            }
            if (getError() != 0) {
                imageRequestManager.error(getError());
            }

            daimajiaSliderImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

            imageRequestManager.listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    loadingBar.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    loadingBar.setVisibility(View.GONE);
                    return false;
                }
            });

            imageRequestManager.into(daimajiaSliderImage);
        } else if (getEmpty() != 0) {
            Glide.with(mContext).load(getEmpty()).into(daimajiaSliderImage);
        }
    }
}
