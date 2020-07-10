package com.jywl.yezai.ui.widget;

import android.graphics.Color;
import android.widget.TextView;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;

/**
 * Created by Haron on 2017/11/14.
 */

public class GradientColorTextView extends androidx.appcompat.widget.AppCompatTextView {

    private LinearGradient mLinearGradient;
    private Paint mPaint;
    private int mViewWidth = 0;
    private Rect mTextBound = new Rect();

    public GradientColorTextView (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mViewWidth = getMeasuredWidth();
        mPaint = getPaint();
        String mTipText = getText().toString();
        mPaint.getTextBounds(mTipText, 0, mTipText.length(), mTextBound);
        mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0,
                new int[]{Color.parseColor("#FF62A4"), Color.parseColor("#FF8961")},
                null, Shader.TileMode.REPEAT);
        mPaint.setShader(mLinearGradient);
        canvas.drawText(mTipText, 0, getMeasuredHeight() / 2 + mTextBound.height() / 2, mPaint);
    }
}