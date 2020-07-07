package com.jywl.yezai.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dueeeke.videoplayer.controller.ControlWrapper;
import com.dueeeke.videoplayer.controller.IControlComponent;
import com.dueeeke.videoplayer.player.VideoView;
import com.jywl.yezai.R;
import com.like.LikeButton;

import timber.log.Timber;

public class TikTokView extends RelativeLayout implements IControlComponent {

    private ImageView ivThumb;
    private LikeButton likeButton;
    private TextView tvLikeCount;
    private ImageView ivComment;
    private TextView tvComment;
    private ImageView ivShare;
    private TextView tvShare;
    private RoundImageView ivAvatar;
    private TextView tvUserName;
    private TextView tvUserIntro;
    private ControlWrapper mControlWrapper;
    private int mScaledTouchSlop;
    private int mStartX, mStartY;

    public TikTokView(@NonNull Context context) {
        super(context);
    }

    public TikTokView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TikTokView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_douyin_view, this, true);
        ivThumb = findViewById(R.id.ivThumb);
        likeButton = findViewById(R.id.likeButton);
        tvLikeCount = findViewById(R.id.tvLikeCount);
        ivComment = findViewById(R.id.ivComment);
        tvComment = findViewById(R.id.tvComment);
        ivShare = findViewById(R.id.ivShare);
        tvShare = findViewById(R.id.tvShare);
        ivAvatar = findViewById(R.id.ivAvatar);
        tvUserName = findViewById(R.id.tvUserName);
        tvUserIntro = findViewById(R.id.tvUserIntro);


        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mControlWrapper.togglePlay();
            }
        });
        mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    /**
     * 解决点击和VerticalViewPager滑动冲突问题
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mStartX = (int) event.getX();
                mStartY = (int) event.getY();
                return true;
            case MotionEvent.ACTION_UP:
                int endX = (int) event.getX();
                int endY = (int) event.getY();
                if (Math.abs(endX - mStartX) < mScaledTouchSlop
                        && Math.abs(endY - mStartY) < mScaledTouchSlop) {
                    performClick();
                }
                break;
        }
        return false;
    }

    @Override
    public void attach(@NonNull ControlWrapper controlWrapper) {
        mControlWrapper = controlWrapper;
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onVisibilityChanged(boolean isVisible, Animation anim) {

    }

    @Override
    public void onPlayStateChanged(int playState) {
        switch (playState) {
            case VideoView.STATE_IDLE:
                Timber.e("STATE_IDLE " + hashCode());
                ivThumb.setVisibility(VISIBLE);
                break;
            case VideoView.STATE_PLAYING:
                Timber.e("STATE_PLAYING " + hashCode());
                ivThumb.setVisibility(GONE);
                break;
            case VideoView.STATE_PAUSED:
                Timber.e("STATE_PAUSED " + hashCode());
                ivThumb.setVisibility(GONE);
                break;
            case VideoView.STATE_PREPARED:
                Timber.e("STATE_PREPARED " + hashCode());
                break;
            case VideoView.STATE_ERROR:
                Timber.e("STATE_ERROR " + hashCode());
                Toast.makeText(getContext(), "dkplayer_error_message", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onPlayerStateChanged(int playerState) {

    }

    @Override
    public void setProgress(int duration, int position) {

    }

    @Override
    public void onLockStateChanged(boolean isLocked) {

    }
}