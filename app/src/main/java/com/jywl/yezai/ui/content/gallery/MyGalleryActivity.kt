package com.jywl.yezai.ui.content.gallery

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jywl.yezai.FinalParams
import com.jywl.yezai.R
import com.jywl.yezai.entity.MultiTypeItem
import com.jywl.yezai.entity.UserBean
import com.jywl.yezai.ui.content.BaseMvpActivity
import com.jywl.yezai.utils.EasyPermission
import com.jywl.yezai.utils.EasyStatusBar
import com.jywl.yezai.utils.glide.GlideEngine
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.decoration.GridSpacingItemDecoration
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.yanzhenjie.permission.RequestExecutor
import com.yanzhenjie.permission.runtime.Permission
import kotlinx.android.synthetic.main.activity_my_gallery.*
import kotlinx.android.synthetic.main.layout_title_bar.*


/**
 * created by Buzz
 * on 2020/7/10
 * email lmx2060918@126.com
 */
class MyGalleryActivity :BaseMvpActivity<MyGalleryPresenter>(), MyGalleryContract.View {
    private var mAdapter = MyGalleryImageAdapter()
    private var imageList = ArrayList<String>()
    private var adapterList = ArrayList<MultiTypeItem<Any>>()

    companion object{
        const val SELECT_MAX_COUNT = 9
    }

    override fun initInject() {
        getActivityComponent().inject(this)
    }

    override fun layoutResID(): Int {
        return R.layout.activity_my_gallery
    }

    override fun initViewAndEvent() {
        EasyStatusBar.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary), false)

        imageList = intent.getStringArrayListExtra(FinalParams.MY_GALLERY_IMAGES) as ArrayList<String>
        handleData(imageList)

        initRecyclerView()

        ivAdd.setOnClickListener {
            goPictureSelect()
        }

        tvTitle.text = "我的相册"
        ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initRecyclerView(){
        //initAdapter
        mAdapter.run {
            setEnableLoadMore(false)
            setPreLoadNumber(0)
            setOnItemChildClickListener { _, view, position ->
                val url = mAdapter.getItem(position)?.getData() as String
                when (view.id) {
                    R.id.ivPic -> {
                        //goImagePreview
                        toast("去预览")
                    }

                    R.id.ivClose -> {
                        imageList.removeAt(position)
                        handleData(imageList)
                    }
                }
            }
        }

        //initRecyclerView
        recyclerView?.run{
            itemAnimator = DefaultItemAnimator()
            layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(GridSpacingItemDecoration(3,20, false))
            adapter = mAdapter
        }
    }


    private fun goPictureSelect(){
        if (Build.VERSION.SDK_INT in 23..29){
            EasyPermission.checkSingle(this, object : EasyPermission.OnGranted {
                override fun onGranted() {
                    openGallery(SELECT_MAX_COUNT - imageList.size)
                }
            }, object : EasyPermission.OnDenied {
                override fun onDenied() {
                    toast("权限被拒，可以再次申请")
                }
            }, object : EasyPermission.OnRationale {
                override fun onRationale(permissionStr: String?, executor: RequestExecutor?) {
                    executor?.execute()
                }
            }, object : EasyPermission.OnAlwaysDenied {
                override fun onAlwaysDenied(permissionStr: String?) {
                    toast("权限被拒，需要手都开启")
                }
            }, Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE)
        } else{
            openGallery(SELECT_MAX_COUNT - imageList.size)
        }
    }

    private fun openGallery(maxNumber:Int){
        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofImage())
            .isCamera(false)
            .imageEngine(GlideEngine.createGlideEngine())
            .maxSelectNum(maxNumber)
            .forResult(object : OnResultCallbackListener<LocalMedia> {
                override fun onResult(result: List<LocalMedia>) {
                    addData(result)
                }

                override fun onCancel() {
                    toast("选择取消")
                }
            })
    }

    private fun handleData(imageList:ArrayList<String>){
        adapterList.clear()
        if (imageList.isNotEmpty()){
            imageList.forEach {
                adapterList.add(MultiTypeItem(MyGalleryImageAdapter.ITEM_GALLERY_IMAGE, it))
            }
        }
        mAdapter.replaceData(adapterList)
    }

    private fun addData(localList: List<LocalMedia>) {
        if (localList.isNotEmpty()){
            localList.forEach {
                adapterList.add(MultiTypeItem(MyGalleryImageAdapter.ITEM_GALLERY_IMAGE, it.path))
                imageList.add(it.path)
            }
        }
        mAdapter.replaceData(adapterList)
        if (imageList.size >= SELECT_MAX_COUNT) ivAdd.visibility = View.GONE else ivAdd.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        intent.putExtra(FinalParams.MY_GALLERY_IMAGES , imageList)
        setResult(Activity.RESULT_OK, intent)
        super.onBackPressed()
    }
}