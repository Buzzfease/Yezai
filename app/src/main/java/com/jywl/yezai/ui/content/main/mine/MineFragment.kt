package com.jywl.yezai.ui.content.main.mine

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jywl.yezai.FinalParams
import com.jywl.yezai.R
import com.jywl.yezai.ui.content.BaseMvpFragment
import com.jywl.yezai.ui.content.gallery.MyGalleryActivity
import com.jywl.yezai.ui.content.main.liveaction.PreviewSelectAdapter
import com.jywl.yezai.utils.DisplayUtil
import com.luck.picture.lib.decoration.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_mine.*
import timber.log.Timber

class MineFragment : BaseMvpFragment<MinePresenter>(), MineContract.View {

    private var mAdapter: PreviewSelectAdapter? = null
    private var imageList:ArrayList<String> = ArrayList()

    override fun initInject() {
        getFragmentComponent().inject(this)
    }

    override fun layoutResID(): Int {
        return R.layout.fragment_mine
    }

    override fun beforeOnCreate() {

    }

    override fun initEventView() {
        initRecyclerView()
//        repeat(5){
//            imageList.add("https://api.xygeng.cn/Bing/")
//        }
        setData(imageList)
    }

    private fun initRecyclerView(){
        mAdapter = PreviewSelectAdapter(requireContext(), 9)
        mAdapter?.setSelectItemClickListener(object : PreviewSelectAdapter.MultiSelectItemClickListener {

            override fun addImageClick() {
                val intent = Intent(activity, MyGalleryActivity::class.java)
                intent.putStringArrayListExtra(FinalParams.MY_GALLERY_IMAGES, imageList)
                startActivityForResult(intent, 1024)
            }

            override fun selectImageClick(position: Int) {
                val intent = Intent(activity, MyGalleryActivity::class.java)
                intent.putStringArrayListExtra(FinalParams.MY_GALLERY_IMAGES, imageList)
                startActivityForResult(intent, 1024)
            }

            override fun selectImageLongClick(position: Int, holder: RecyclerView.ViewHolder) {
                Timber.e("selectImageLongClick")
            }
        })
        recyclerView?.layoutManager =  StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)
        recyclerView?.addItemDecoration(GridSpacingItemDecoration(4, DisplayUtil.dip2px(requireContext(), 10f), false))
        recyclerView?.adapter = mAdapter
    }

    private fun setData(imageList : ArrayList<String>){
        mAdapter?.updateItem(imageList)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                1024 -> {
                    imageList = data?.getStringArrayListExtra( FinalParams.MY_GALLERY_IMAGES) as ArrayList<String>
                    setData(imageList)
                }
            }
        }
    }

}