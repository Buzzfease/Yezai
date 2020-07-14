package com.jywl.yezai.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.jywl.yezai.FinalParams;
import com.jywl.yezai.R;
import com.jywl.yezai.ui.content.imagepreview.ImagePreviewActivity;
import com.jywl.yezai.utils.TempUtil;
import com.jywl.yezai.utils.glide.GlideCenter;

import java.util.List;

import timber.log.Timber;

/**
 * created by Buzz
 * on 2020/7/13
 * email lmx2060918@126.com
 */
public class MyMultiStateView extends FrameLayout {
    private LayoutInflater mInflater;//加载各种状态布局的Inflater；
    private View onePicView;//当处于一张图状态下要显示的View；
    private View twoPicView;//当处于两张图状态下要显示的View；
    private View threePicView;//当处于三张图状态下要显示的View；
    private View moreThanThreePicView;//当处于大于三张图状态下要显示的View；
    private View mInXMLView;//正常状态显示的View；（就是我们在XML中定义MyMultiStateView时包裹的子View）
    private ViewStatus mViewStatus = ViewStatus.onePicStatus;//记录当前正处于的状态；
    private final int STATUS_ONE_PIC = 0;//以下四种为四种不同状态标识；
    private final int STATUS_TWO_PIC = 1;
    private final int STATUS_THREE_PIC = 2;
    private final int STATUS_MORE_THAN_THREE_PIC = 3;
    private final int STATUS_IN_XML_VIEW = 4;
 
 
    /**
     * when creating a view from code.
     * 当我们在代码中以new的方式创建View时调用当前方法创建View对象；
     */
    public MyMultiStateView(@NonNull Context context) {
        this(context, null);
    }
 
    /***
     * 当在Layout中定义MyMultiStateView时，系统调用当前方法创建View对象，可以通过AttributeSet获取自定义属性内容；
     */
    public MyMultiStateView(@NonNull Context context, AttributeSet attrs) {
        this(context, attrs, 0);
 
    }

    public MyMultiStateView(@NonNull Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }
 
    public MyMultiStateView(@NonNull Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
 
    private void init(AttributeSet attrs) {
        mInflater = LayoutInflater.from(getContext());
        //获取各个状态对应的View，并添加到当前View；
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.MyMultiStateView);
        int onePicViewId = ta.getResourceId(R.styleable.MyMultiStateView_one_pic, -1);
        if (onePicViewId != -1) {
            onePicView = mInflater.inflate(onePicViewId, null, false);
            addView(onePicView);
        }
        int twoPicViewId = ta.getResourceId(R.styleable.MyMultiStateView_two_pic, -1);
        if (twoPicViewId != -1) {
            twoPicView = mInflater.inflate(twoPicViewId, null, false);
            addView(twoPicView);
        }
        int threePicViewId = ta.getResourceId(R.styleable.MyMultiStateView_three_pic, -1);
        if (threePicViewId != -1) {
            threePicView = mInflater.inflate(threePicViewId, null, false);
            addView(threePicView);
        }
        int moreThanThreeViewId = ta.getResourceId(R.styleable.MyMultiStateView_more_than_three_pic, -1);
        if (moreThanThreeViewId != -1) {
            moreThanThreePicView = mInflater.inflate(moreThanThreeViewId, null, false);
            addView(moreThanThreePicView);
        }
        //获取初始状态时定义的显示状态；
        int initailViewStatus = ta.getInt(R.styleable.MyMultiStateView_initialViewStatus, -1);
        if (initailViewStatus != -1) {
            switch (initailViewStatus) {
                case STATUS_ONE_PIC:
                    mViewStatus = ViewStatus.onePicStatus;
                    break;
                case STATUS_TWO_PIC:
                    mViewStatus = ViewStatus.twoPicStatus;
                    break;
                case STATUS_THREE_PIC:
                    mViewStatus = ViewStatus.threePicStatus;
                    break;
                case STATUS_MORE_THAN_THREE_PIC:
                    mViewStatus = ViewStatus.moreThanThreePicStatus;
                    break;
                case STATUS_IN_XML_VIEW:
                    mViewStatus = ViewStatus.inXMLViewStatus;
                    break;
            }
        }
        ta.recycle();
    }
 
    @Override
    public void addView(View child) {
        if (isInXMLView(child))
            mInXMLView = child;
        super.addView(child);
    }
 
    @Override
    public void addView(View child, int index) {
        if (isInXMLView(child))
            mInXMLView = child;
        super.addView(child, index);
    }
 
    @Override
    public void addView(View child, int width, int height) {
        if (isInXMLView(child))
            mInXMLView = child;
        super.addView(child, width, height);
    }
 
    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if (isInXMLView(child))
            mInXMLView = child;
        super.addView(child, params);
    }
 
    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (isInXMLView(child))
            mInXMLView = child;
        super.addView(child, index, params);
    }
 
    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params) {
        if (isInXMLView(child))
            mInXMLView = child;
        return super.addViewInLayout(child, index, params);
    }
 
    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params, boolean preventRequestLayout) {
        if (isInXMLView(child))
            mInXMLView = child;
        return super.addViewInLayout(child, index, params, preventRequestLayout);
    }
 
    /**
     * 判断是不是XML中MyMultiStatusView的子类；
     *
     * @param child
     * @return
     */
    private boolean isInXMLView(View child) {
        if (mInXMLView != null && mInXMLView != child) {
            return false;
        }
        if (child != onePicView && child != twoPicView && child != threePicView && child != moreThanThreePicView) {
            return true;
        }
        return false;
    }
 
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Timber.e("onAttachedToWindow()");
        if (mInXMLView == null)
            throw new IllegalArgumentException("MyMultiStateView in XML must have child");
        //设置初始View；
        setInitailView();
    }
 
    private void setInitailView() {
        switch (mViewStatus) {
            case onePicStatus:
                if (onePicView == null)
                    throw new NullPointerException("LoadingView is null");
                setViewShowOrHidden(onePicView);
                break;
            case twoPicStatus:
                if (twoPicView == null)
                    throw new NullPointerException("EmptyView is null");
                setViewShowOrHidden(twoPicView);
                break;
            case threePicStatus:
                if (threePicView == null)
                    throw new NullPointerException("ErrorView is null");
                setViewShowOrHidden(threePicView);
                break;
            case moreThanThreePicStatus:
                if (moreThanThreePicView == null)
                    throw new NullPointerException("ErrorView is null");
                setViewShowOrHidden(moreThanThreePicView);
                break;
            case inXMLViewStatus:
                if (mInXMLView == null)
                    throw new NullPointerException("InXMLView is null");
                setViewShowOrHidden(mInXMLView);
                break;
            default:
                break;
        }
 
    }
 
    private void setViewShowOrHidden(View v) {
        mInXMLView.setVisibility(GONE);
        onePicView.setVisibility(GONE);
        twoPicView.setVisibility(GONE);
        threePicView.setVisibility(GONE);
        moreThanThreePicView.setVisibility(GONE);
        v.setVisibility(VISIBLE);
    }
 
    /**
     * 通过指定状态获取对应的View；
     *
     * @param status
     * @return
     */
    public View getViewByState(ViewStatus status) {
        switch (status) {
            case onePicStatus:
                return onePicView;
            case twoPicStatus:
                return twoPicView;
            case threePicStatus:
                return threePicView;
            case moreThanThreePicStatus:
                return moreThanThreePicView;
            case inXMLViewStatus:
                return mInXMLView;
        }
        return null;
    }
 
    /**
     * 获取当前状态；
     *
     * @return
     */
    public ViewStatus getNowViewStatus() {
        return mViewStatus;
    }
 
    /**
     * 设置View状态；
     *
     * @param status
     */
    public void setViewStatus(@NonNull ViewStatus status) {
        mViewStatus = status;
        setInitailView();
    }

    public void setImageData(List<Object> list){
        if (list != null && !list.isEmpty()){
            int count = list.size();
            if (count == 1){
                WidthSquareImageView pic1 = onePicView.findViewById(R.id.ivPic1);
                setViewStatus(MyMultiStateView.ViewStatus.onePicStatus);
                GlideCenter.Companion.get().showCrossFadeImage(pic1, R.mipmap.ic_avatar);
                pic1.setOnClickListener(view -> {
                    Intent intent = new Intent(getContext(), ImagePreviewActivity.class);
                    intent.putExtra(FinalParams.IMAGE_LIST, TempUtil.INSTANCE.createRandomImageListBySize(count));
                    intent.putExtra(FinalParams.START_IMAGE_POSITION, 0);
                    getContext().startActivity(intent);
                });
            }else if (count == 2){
                WidthSquareImageView pic1 = twoPicView.findViewById(R.id.ivPic1);
                WidthSquareImageView pic2 = twoPicView.findViewById(R.id.ivPic2);
                setViewStatus(MyMultiStateView.ViewStatus.twoPicStatus);
                GlideCenter.Companion.get().showCrossFadeImage(pic1, R.mipmap.ic_avatar);
                GlideCenter.Companion.get().showCrossFadeImage(pic2, R.mipmap.ic_avatar);
                pic1.setOnClickListener(view -> {
                    Intent intent = new Intent(getContext(), ImagePreviewActivity.class);
                    intent.putExtra(FinalParams.IMAGE_LIST, TempUtil.INSTANCE.createRandomImageListBySize(count));
                    intent.putExtra(FinalParams.START_IMAGE_POSITION, 0);
                    getContext().startActivity(intent);
                });
                pic2.setOnClickListener(view -> {
                    Intent intent = new Intent(getContext(), ImagePreviewActivity.class);
                    intent.putExtra(FinalParams.IMAGE_LIST, TempUtil.INSTANCE.createRandomImageListBySize(count));
                    intent.putExtra(FinalParams.START_IMAGE_POSITION, 1);
                    getContext().startActivity(intent);
                });
            }else if (count == 3){
                WidthSquareImageView pic1 = threePicView.findViewById(R.id.ivPic1);
                WidthSquareImageView pic2 = threePicView.findViewById(R.id.ivPic2);
                WidthSquareImageView pic3 = threePicView.findViewById(R.id.ivPic3);
                setViewStatus(ViewStatus.threePicStatus);
                GlideCenter.Companion.get().showCrossFadeImage(pic1, R.mipmap.ic_avatar);
                GlideCenter.Companion.get().showCrossFadeImage(pic2, R.mipmap.ic_avatar);
                GlideCenter.Companion.get().showCrossFadeImage(pic3, R.mipmap.ic_avatar);
                pic1.setOnClickListener(view -> {
                    Intent intent = new Intent(getContext(), ImagePreviewActivity.class);
                    intent.putExtra(FinalParams.IMAGE_LIST, TempUtil.INSTANCE.createRandomImageListBySize(count));
                    intent.putExtra(FinalParams.START_IMAGE_POSITION, 0);
                    getContext().startActivity(intent);
                });
                pic2.setOnClickListener(view -> {
                    Intent intent = new Intent(getContext(), ImagePreviewActivity.class);
                    intent.putExtra(FinalParams.IMAGE_LIST, TempUtil.INSTANCE.createRandomImageList());
                    intent.putExtra(FinalParams.START_IMAGE_POSITION, 1);
                    getContext().startActivity(intent);
                });
                pic3.setOnClickListener(view -> {
                    Intent intent = new Intent(getContext(), ImagePreviewActivity.class);
                    intent.putExtra(FinalParams.IMAGE_LIST, TempUtil.INSTANCE.createRandomImageList());
                    intent.putExtra(FinalParams.START_IMAGE_POSITION, 2);
                    getContext().startActivity(intent);
                });
            }else {
                WidthSquareImageView pic1 = moreThanThreePicView.findViewById(R.id.ivPic1);
                WidthSquareImageView pic2 = moreThanThreePicView.findViewById(R.id.ivPic2);
                WidthSquareImageView pic3 = moreThanThreePicView.findViewById(R.id.ivPic3);
                TextView tvCount = moreThanThreePicView.findViewById(R.id.tvCount);
                setViewStatus(ViewStatus.moreThanThreePicStatus);
                GlideCenter.Companion.get().showCrossFadeImage(pic1, R.mipmap.ic_avatar);
                GlideCenter.Companion.get().showCrossFadeImage(pic2, R.mipmap.ic_avatar);
                GlideCenter.Companion.get().showCrossFadeImage(pic3, R.mipmap.ic_avatar);
                tvCount.setVisibility(View.VISIBLE);
                tvCount.setText("共" + count + "张");

                pic1.setOnClickListener(view -> {
                    Intent intent = new Intent(getContext(), ImagePreviewActivity.class);
                    intent.putExtra(FinalParams.IMAGE_LIST, TempUtil.INSTANCE.createRandomImageList());
                    intent.putExtra(FinalParams.START_IMAGE_POSITION, 0);
                    getContext().startActivity(intent);
                });
                pic2.setOnClickListener(view -> {
                    Intent intent = new Intent(getContext(), ImagePreviewActivity.class);
                    intent.putExtra(FinalParams.IMAGE_LIST, TempUtil.INSTANCE.createRandomImageList());
                    intent.putExtra(FinalParams.START_IMAGE_POSITION, 1);
                    getContext().startActivity(intent);
                });
                pic3.setOnClickListener(view -> {
                    Intent intent = new Intent(getContext(), ImagePreviewActivity.class);
                    intent.putExtra(FinalParams.IMAGE_LIST, TempUtil.INSTANCE.createRandomImageList());
                    intent.putExtra(FinalParams.START_IMAGE_POSITION, 2);
                    getContext().startActivity(intent);
                });
            }
        }
    }
 
    /**
     * 设置指定状态对应的Layout；
     * @param resouseId
     * @param status
     */
    public void setViewBindStatus(@LayoutRes int resouseId, ViewStatus status) {
        setViewBindStatus(mInflater.inflate(resouseId, null), status);
    }
    /**
     * 设置指定状态对应的Layout，并包含是否马上切换到当前状态；
     * @param resouseId
     * @param status
     */
    public void setViewBindStatus(@LayoutRes int resouseId, ViewStatus status, boolean showRightNow) {
        setViewBindStatus(mInflater.inflate(resouseId, null), status, showRightNow);
    }
 
    /**
     * 外部设置status对应的View
     *
     * @param v
     * @param status
     */
    public void setViewBindStatus(@NonNull View v, @NonNull ViewStatus status) {
        setViewBindStatus(v, status, false);
    }
 
    /**
     * 设置status对应的View，并设置是否马上显示当前状态；
     *
     * @param v            status对应的view;
     * @param status       四种status对应的状态；
     * @param showRightNow 是否马上切换至当前状态；
     */
    public void setViewBindStatus(@NonNull View v, @NonNull ViewStatus status, boolean showRightNow) {
        switch (status) {
            case onePicStatus:
                if (onePicView != null)
                    removeView(onePicView);
                onePicView = v;
                addView(onePicView);
                break;
            case twoPicStatus:
                if (twoPicView != null)
                    removeView(twoPicView);
                twoPicView = v;
                addView(twoPicView);
                break;
            case threePicStatus:
                if (threePicView != null)
                    removeView(threePicView);
                threePicView = v;
                addView(threePicView);
                break;
            case moreThanThreePicStatus:
                if (moreThanThreePicView != null)
                    removeView(moreThanThreePicView);
                moreThanThreePicView = v;
                addView(moreThanThreePicView);
                break;
            case inXMLViewStatus:
                if (mInXMLView != null)
                    removeView(mInXMLView);
                mInXMLView = v;
                addView(mInXMLView);
                break;
        }
        //如果支持马上去显示当前状态下的View；
        if (showRightNow) {
            mViewStatus = status;
            setInitailView();
        }
    }
 
 
    /**
     * 在此控件被通过xml声明的方式创建之后调用;
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Timber.e("onFinishInflate()");
    }
 
    public enum ViewStatus {
        onePicStatus,
        twoPicStatus,
        threePicStatus,
        moreThanThreePicStatus,
        inXMLViewStatus
 
    }
}
