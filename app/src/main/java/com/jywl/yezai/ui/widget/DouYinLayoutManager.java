package com.jywl.yezai.ui.widget;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import timber.log.Timber;

/**
 * created by Buzz
 * on 2020/7/6
 * email lmx2060918@126.com
 */
public class DouYinLayoutManager extends LinearLayoutManager implements RecyclerView.OnChildAttachStateChangeListener{

    //判断是否上滑还是下滑
    private int mDrift;
    private OnViewPagerListener onViewPagerListener;

    //吸顶，吸底
    private PagerSnapHelper pagerSnapHelper;

    public OnViewPagerListener getOnViewPagerListener() {
        return onViewPagerListener;
    }

    public void setOnViewPagerListener(OnViewPagerListener onViewPagerListener) {
        this.onViewPagerListener = onViewPagerListener;
    }

    public DouYinLayoutManager(Context context) {
        super(context);
    }

    public DouYinLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        pagerSnapHelper = new PagerSnapHelper();
    }

    /**
     * 当manager完全添加到recyclerview中是会被调用
     * @param view
     */
    @Override
    public void onAttachedToWindow(RecyclerView view) {
        view.addOnChildAttachStateChangeListener(this);
        pagerSnapHelper.attachToRecyclerView(view);
        super.onAttachedToWindow(view);
    }

    @Override
    public boolean canScrollVertically() {
        return super.canScrollVertically();
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        mDrift = dy;
        return super.scrollVerticallyBy(dy, recycler, state);

    }

    @Override
    public void onChildViewAttachedToWindow(@NonNull View view) {
        if (mDrift > 0){
            //向上滑
            if (onViewPagerListener != null && Math.abs(mDrift) == view.getHeight()){
                onViewPagerListener.onPageSelected(false, view);
                Timber.e("onChildViewAttachedToWindow" + " false");
            }
        }else {
            //向下滑
            if (onViewPagerListener != null && Math.abs(mDrift) == view.getHeight()){
                onViewPagerListener.onPageSelected(true, view);
                Timber.e("onChildViewAttachedToWindow" + " true");
            }
        }
    }

    @Override
    public void onChildViewDetachedFromWindow(@NonNull View view) {
        if (mDrift >= 0){
            //向上滑
            if (onViewPagerListener != null){
                onViewPagerListener.onPageRelease(true, view);
                Timber.e("onChildViewDetachedFromWindow" + " true");
            }
        }else {
            //向下滑
            if (onViewPagerListener != null){
                onViewPagerListener.onPageRelease(false, view);
                Timber.e("onChildViewDetachedFromWindow" + " false");
            }
        }
    }

    @Override
    public void onScrollStateChanged(int state) {
        switch (state){
            case RecyclerView.SCROLL_STATE_IDLE:
                //当前显示的item
                View snapView = pagerSnapHelper.findSnapView(this);
                if (onViewPagerListener != null){
                    onViewPagerListener.onPageSelected(false, snapView);
                    Timber.e("SCROLL_STATE_IDLE" + " false");
                }
                break;
        }
        super.onScrollStateChanged(state);

    }

    public interface OnViewPagerListener {
        //停止播放的监听
        void onPageRelease(boolean isNest, View position);

        //播放的监听
        void onPageSelected(boolean isDownSlide, View position);
    }
}

