package com.jywl.yezai

import android.app.Application
import com.dueeeke.videoplayer.exo.ExoMediaPlayerFactory
import com.dueeeke.videoplayer.player.VideoViewConfig
import com.dueeeke.videoplayer.player.VideoViewManager
import com.jywl.yezai.di.*
import timber.log.Timber


/**
 * Created by Buzz on 2019/4/23.
 * Email :lmx2060918@126.com
 */
class MyApplication : Application() {

    companion object {
        var appComponent: AppComponent? = null
        private var instance: Application? = null
        fun  instance() = instance!!
    }

    init{
        initDependency()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        //开启关闭Log
        if (Constant.getIsShowLog()) {
            Timber.plant(Timber.DebugTree())
        }
        //播放器核心
        VideoViewManager.setConfig(
            VideoViewConfig.newBuilder()
            //使用ExoPlayer解码
            .setPlayerFactory(ExoMediaPlayerFactory.create())
            .build());
    }

    private fun initDependency(){
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .httpModule(HttpModule())
                    .glideModule(GlideModule())
                    .build()
        }
    }
}