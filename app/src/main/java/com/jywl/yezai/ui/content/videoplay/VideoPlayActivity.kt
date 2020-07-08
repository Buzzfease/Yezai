package com.jywl.yezai.ui.content.videoplay

import android.view.View
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener
import com.dueeeke.videoplayer.exo.ExoMediaPlayer
import com.dueeeke.videoplayer.player.VideoView
import com.jywl.yezai.R
import com.jywl.yezai.entity.VideoListBean
import com.jywl.yezai.ui.content.BaseMvpActivity
import com.jywl.yezai.ui.widget.TikTokController
import com.jywl.yezai.ui.widget.VerticalViewPager
import com.jywl.yezai.utils.DisplayUtil
import com.jywl.yezai.utils.EasyStatusBar
import com.jywl.yezai.utils.cache.PreloadManager
import com.jywl.yezai.utils.cache.ProxyVideoCacheManager
import com.jywl.yezai.utils.render.TikTokRenderViewFactory
import kotlinx.android.synthetic.main.activity_video_play.*
import timber.log.Timber


/**
 * created by Buzz
 * on 2020/7/7
 * email lmx2060918@126.com
 */
class VideoPlayActivity :BaseMvpActivity<VideoPlayPresenter>(), VideoPlayContract.View {
    /**
     * 当前播放位置
     */
    private var mCurPos = 0
    private var mVideoList: ArrayList<VideoListBean> = ArrayList()
    private var mTikTokAdapter: TikTokAdapter? = null
    private var mPreloadManager: PreloadManager? = null
    private var mController: TikTokController? = null
    private var mVideoView:VideoView<ExoMediaPlayer>? = null

    override fun initInject() {
        getActivityComponent().inject(this)
    }

    override fun layoutResID(): Int {
        return R.layout.activity_video_play
    }

    override fun initViewAndEvent() {
        EasyStatusBar.makeStatusBarTransparent(this, false, rlMain, llTop)
        initViewPager();
        initVideoView();
        mPreloadManager = PreloadManager.getInstance(this);
        ivBack.setOnClickListener {
            finish()
        }

        setData()

        verticalViewPager.post {
            startPlay(0);
        }
    }

    private fun initVideoView() {
        mController = TikTokController(this)
        mVideoView = VideoView<ExoMediaPlayer>(this)
        mVideoView?.run {
            setLooping(true)
            //以下只能二选一，看你的需求
            setRenderViewFactory(TikTokRenderViewFactory.create())
            //setScreenScaleType(VideoView.SCREEN_SCALE_CENTER_CROP);
            setVideoController(mController)
        }
    }

    private fun initViewPager() {
        mTikTokAdapter = TikTokAdapter(mVideoList)
        verticalViewPager.run {
            offscreenPageLimit = 4
            adapter = mTikTokAdapter
            overScrollMode = View.OVER_SCROLL_NEVER
            setOnPageChangeListener(object : SimpleOnPageChangeListener() {
                private var mCurItem = 0
                /**
                 * VerticalViewPager是否反向滑动
                 */
                private var mIsReverseScroll = false
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    if (position == mCurItem) {
                        return
                    }
                    mIsReverseScroll = position < mCurItem
                }

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    if (position == mCurPos) return
                    startPlay(position)
                }

                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    if (state == VerticalViewPager.SCROLL_STATE_DRAGGING) {
                        mCurItem = verticalViewPager.currentItem
                    }
                    if (state == VerticalViewPager.SCROLL_STATE_IDLE) {
                        mPreloadManager!!.resumePreload(mCurPos, mIsReverseScroll)
                    } else {
                        mPreloadManager!!.pausePreload(mCurPos, mIsReverseScroll)
                    }
                }
            })
        }
    }


    private fun setData(){
        val aCard = VideoListBean("Buzz",
            "https://api.xygeng.cn/Bing/",
            "https://api.xygeng.cn/Bing/",
            "https://gslb.miaopai.com/stream/IR3oMYDhrON5huCmf7sHCfnU5YKEkgO2.mp4",
            227, "自我介绍自我介绍！")

        repeat(10){
            mVideoList.add(aCard)
        }
        mTikTokAdapter?.notifyDataSetChanged()
        Timber.e("setData")
    }

    private fun startPlay(position: Int) {
        val count = verticalViewPager.childCount
        for (i in 0 until count) {
            val itemView = verticalViewPager.getChildAt(i)
            val viewHolder = itemView.tag as TikTokAdapter.ViewHolder
            if (viewHolder.mPosition == position) {
                mVideoView!!.release()
                DisplayUtil.removeViewFormParent(mVideoView)
                val tiktokBean: VideoListBean = mVideoList[position]
                val playUrl = mPreloadManager!!.getPlayUrl(tiktokBean.videoPath)
                Timber.e("startPlay: position: $position" + playUrl)
                mVideoView!!.setUrl(tiktokBean.videoPath)
                mController!!.addControlComponent(viewHolder.mTikTokView, true)
                viewHolder.mPlayerContainer.addView(mVideoView, 0)
                mVideoView!!.start()
                mCurPos = position
                break
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mVideoView?.release()
        mVideoView = null
        mPreloadManager!!.removeAllPreloadTask()
        //清除缓存，实际使用可以不需要清除，这里为了方便测试
        ProxyVideoCacheManager.clearAllCache(this)
    }
}