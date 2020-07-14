package com.jywl.yezai.utils

import com.jywl.yezai.entity.PicWithExt

/**
 * created by Buzz
 * on 2020/7/14
 * email lmx2060918@126.com
 */
object TempUtil {

    fun createRandomImageList():ArrayList<PicWithExt>{
        val imageList = ArrayList<PicWithExt>()
        repeat((1..9).random()){
            val picWithExt = PicWithExt("https://api.xygeng.cn/Bing/", "normal", "title")
            imageList.add(picWithExt)
        }
        return imageList
    }

    fun createRandomImageListBySize(size:Int):ArrayList<PicWithExt>{
        val imageList = ArrayList<PicWithExt>()
        repeat(size){
            val picWithExt = PicWithExt("https://api.xygeng.cn/Bing/", "normal", "title")
            imageList.add(picWithExt)
        }
        return imageList
    }
}