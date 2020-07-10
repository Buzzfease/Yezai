package com.jywl.yezai.ui.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class WidthSquareImageView extends AppCompatImageView {

    private float hwRatio = 1f;
    private static final String key_hwRatio = "hwRatio";

    public WidthSquareImageView(Context context) {
        super(context);
        setAttributes(context, null);
    }

    public WidthSquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttributes(context, attrs);
    }

    public WidthSquareImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setAttributes(context, attrs);
    }

    private void setAttributes(Context context, AttributeSet attrs) {
        if (attrs == null) return;
        String packageName = "http://schemas.android.com/apk/res-auto";
        hwRatio = attrs.getAttributeFloatValue(packageName, key_hwRatio, 1f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        //定高度宽度对齐
//        int calculatedWidth = calculateWidthByRatio(height);
//        if (calculatedWidth != width){
//            setMeasuredDimension(calculatedWidth, height);
//        }

        //定宽度高度对齐
        int calculatedHeight = calculateHeightByRatio(width);

        if (calculatedHeight != height) {
            setMeasuredDimension(width, calculatedHeight);
        }

    }

    private int calculateWidthByRatio(int height) {
        return (int) (hwRatio * (float) height);
    }

    private int calculateHeightByRatio(int width) { return (int) (hwRatio * (float) width); }

    public float getHwRatio() {
        return hwRatio;
    }

    public void setXyRatio(float xyRatio) {
        this.hwRatio = xyRatio;
        this.invalidate();
    }

}