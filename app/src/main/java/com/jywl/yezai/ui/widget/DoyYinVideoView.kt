package com.jywl.yezai.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import cn.jzvd.*
import com.jywl.yezai.R
import kotlinx.android.synthetic.main.layout_my_jiaozi_video_view.view.*
import timber.log.Timber

class DoyYinVideoView: JzvdStd {
    private var onStateChangeListener:OnVideoStateChangeListener? = null
    fun setOnStateChangeListener(onStateChangeListener:OnVideoStateChangeListener){
        this.onStateChangeListener = onStateChangeListener
    }
    private var onProgressChangeListener:OnProgressChangeListener? = null
    fun setOnProgressChangeListener(onProgressChangeListener:OnProgressChangeListener){
        this.onProgressChangeListener = onProgressChangeListener
    }

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun getLayoutId(): Int {
        return R.layout.layout_my_jiaozi_video_view
    }

    override fun onVideoSizeChanged() {
        Timber.d( "onVideoSizeChanged " + " [" + this.hashCode() + "] ")

        val screenWidth = mainRl.width.toFloat()
        val screenHeight = mainRl.height.toFloat()
        val screenRatio = screenHeight / screenWidth
        val params = surface_container.layoutParams
        val width = JZMediaManager.instance().currentVideoWidth.toFloat()
        val height =  JZMediaManager.instance().currentVideoHeight.toFloat()
        val ratio = height/ width

        //文字动画的宽高不做适配
        if (height != 960f && width != 544f){
            if (ratio >= screenRatio){
                //视频长宽比大于屏幕长宽比
                params?.height = mainRl?.height
                params?.width = (params?.height!! / ratio).toInt()
            }else{
                //视频长宽比小于屏幕长宽比
                params?.width = mainRl?.width
                params?.height = (ratio * params?.width!!).toInt()
            }
            //container?.layoutParams = params
        }

        if (JZMediaManager.textureView != null) {
            if (videoRotation != 0) {
                JZMediaManager.textureView.rotation = videoRotation.toFloat()
            }
            JZMediaManager.textureView.setVideoSize(params?.width!!, params.height)
        }
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        if (v?.id == R.id.surface_container){
            //startButton.callOnClick()
            when(currentState){
                Jzvd.CURRENT_STATE_IDLE ->{
                    Timber.e("RecyclerViewPager"+ "状态为Idle,还未初始化不能播放")
                }
                Jzvd.CURRENT_STATE_NORMAL ->{
                    //startVideo()
                    if (!jzDataSource.currentUrl.toString().startsWith("file") &&
                            !jzDataSource.currentUrl.toString().startsWith("/") &&
                            !JZUtils.isWifiConnected(context) && !Jzvd.WIFI_TIP_DIALOG_SHOWED) {
                        showWifiDialog()
                        return
                    }
                    startVideo()
                    onEvent(JZUserActionStd.ON_CLICK_START_THUMB)//开始的事件应该在播放之后，此处特殊
                }
                Jzvd.CURRENT_STATE_PAUSE ->{
                    startButton.callOnClick()
                }
                Jzvd.CURRENT_STATE_PLAYING ->{
                    startButton.callOnClick()
                }
            }
        }
    }

    fun mStartVideo(){
        if (!jzDataSource.currentUrl.toString().startsWith("file") &&
                !jzDataSource.currentUrl.toString().startsWith("/") &&
                !JZUtils.isWifiConnected(context) && !Jzvd.WIFI_TIP_DIALOG_SHOWED) {
            showWifiDialog()
            return
        }
        startVideo()
    }


    override fun init(context: Context?) {
        super.init(context)
        setVideoImageDisplayType(Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_FILL_PARENT)
        //取消进度条
        progressBar?.visibility = View.GONE
        //取消全屏按钮
        fullscreenButton?.visibility = View.GONE
        //取消当前进度时间
        currentTimeTextView?.visibility = View.GONE
        //取消视频总时长
        totalTimeTextView?.visibility = View.GONE
        //取消退出按钮
        backButton?.visibility = View.GONE
        //取消小退出按钮
        tinyBackImageView?.visibility = View.GONE
        //取消电量
        batteryTimeLayout?.visibility = View.GONE
        //取消电池图标
        batteryLevel?.visibility = View.GONE
        //取消当前时间显示
        videoCurrentTime?.visibility = View.GONE
        //底部进度条
        bottomProgressBar?.visibility = View.GONE
        //取消进度条计时器
        cancelProgressTimer()

    }

    /**
     * MediaPlayer.OnInfoListener
    Constant Value: 800 (0x00000320)
    public static final int MEDIA_INFO_BUFFERING_END
    Added in API level 9 MediaPlayer is resuming playback after filling buffers.

    See Also
    MediaPlayer.OnInfoListener
    Constant Value: 702 (0x000002be)
    public static final int MEDIA_INFO_BUFFERING_START
    Added in API level 9 MediaPlayer is temporarily pausing playback internally in order to buffer more data.

    See Also
    MediaPlayer.OnInfoListener
    Constant Value: 701 (0x000002bd)
    public static final int MEDIA_INFO_METADATA_UPDATE
    Added in API level 5 A new set of metadata is available.

    See Also
    MediaPlayer.OnInfoListener
    Constant Value: 802 (0x00000322)
    public static final int MEDIA_INFO_NOT_SEEKABLE
    Added in API level 3 The media cannot be seeked (e.g live stream)

    See Also
    MediaPlayer.OnInfoListener
    Constant Value: 801 (0x00000321)
    public static final int MEDIA_INFO_UNKNOWN
    Added in API level 3 Unspecified media player info.

    See Also
    MediaPlayer.OnInfoListener
    Constant Value: 1 (0x00000001)
    public static final int MEDIA_INFO_VIDEO_RENDERING_START
    Added in API level 17 The player just pushed the very first video frame for rendering.

    See Also
    MediaPlayer.OnInfoListener
    Constant Value: 3 (0x00000003)
    public static final int MEDIA_INFO_VIDEO_TRACK_LAGGING
    Added in API level 3 The video is too complex for the decoder: it can't decode frames fast enough. Possibly only the audio plays fine at this stage.

    See Also
    MediaPlayer.OnInfoListener
    Constant Value: 700 (0x000002bc)
     */
    override fun onInfo(what: Int, extra: Int) {
        super.onInfo(what, extra)
        Timber.e("RecyclerViewPager" + " onInfo : what = " + what + "; extra = " + extra)
    }


    override fun onProgress(progress: Int, position: Long, duration: Long) {
        super.onProgress(progress, position, duration)
        onProgressChangeListener?.onProgressChange(position)
    }

    //onState 代表了播放器引擎的回调，播放视频各个过程的状态的回调
    override fun onStateNormal() {
        super.onStateNormal()
        Timber.e("RecyclerViewPager"+ " onStateNormal")
        onStateChangeListener?.onStateNormal()
        startButton.visibility = View.GONE
    }

    override fun onStatePrepared() {
        super.onStatePrepared()
        Timber.e("RecyclerViewPager"+ " onStatePrepared")
    }

    override fun onStatePreparing() {
        super.onStatePreparing()
        Timber.e("RecyclerViewPager"+ " onStatePreparing")
        onStateChangeListener?.onStatePreparing()
    }

    override fun onStatePlaying() {
        super.onStatePlaying()
        Timber.e("RecyclerViewPager"+ " onStatePlaying")
        onStateChangeListener?.onStatePlaying()
    }

    override fun onStatePause() {
        super.onStatePause()
        Timber.e("RecyclerViewPager"+ " onStatePause")
        onStateChangeListener?.onStatePause()
    }

    override fun onStateError() {
        super.onStateError()
        Timber.e("RecyclerViewPager"+ " onStateError")
        onStateChangeListener?.onStateError()
    }

    override fun onStateAutoComplete() {
        super.onStateAutoComplete()
        Timber.e("RecyclerViewPager"+ " onStateAutoComplete")
        onStateChangeListener?.onStateAutoComplete()
    }

    //changeUiTo 真能能修改ui的方法
    override fun changeUiToNormal() {
        super.changeUiToNormal()
        bottomProgressBar?.visibility = View.GONE
    }

    override fun changeUiToPreparing() {
        super.changeUiToPreparing()
        bottomProgressBar?.visibility = View.GONE
    }

    override fun changeUiToPlayingShow() {
        super.changeUiToPlayingShow()
        bottomProgressBar?.visibility = View.GONE
    }

    override fun changeUiToPlayingClear() {
        super.changeUiToPlayingClear()
        bottomProgressBar?.visibility = View.GONE
    }

    override fun changeUiToPauseShow() {
        super.changeUiToPauseShow()
        bottomProgressBar?.visibility = View.GONE
    }

    override fun changeUiToPauseClear() {
        super.changeUiToPauseClear()
        bottomProgressBar?.visibility = View.GONE
    }

    override fun changeUiToComplete() {
        super.changeUiToComplete()
        bottomProgressBar?.visibility = View.GONE
    }

    override fun changeUiToError() {
        super.changeUiToError()
        bottomProgressBar?.visibility = View.GONE
    }


    interface OnVideoStateChangeListener{
        fun onStateNormal()
        fun onStatePreparing()
        fun onStatePlaying()
        fun onStatePause()
        fun onStateError()
        fun onStateAutoComplete()
    }

    interface OnProgressChangeListener{
        fun onProgressChange(position:Long)
    }

}