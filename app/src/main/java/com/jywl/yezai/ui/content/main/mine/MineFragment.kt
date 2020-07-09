package com.jywl.yezai.ui.content.main.mine

import android.os.Build
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jywl.yezai.R
import com.jywl.yezai.ui.content.BaseMvpFragment
import com.jywl.yezai.ui.widget.PreviewSelectAdapter
import com.jywl.yezai.ui.widget.SpaceItemDecoration
import com.jywl.yezai.utils.EasyPermission
import com.yanzhenjie.permission.RequestExecutor
import com.yanzhenjie.permission.runtime.Permission
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment : BaseMvpFragment<MinePresenter>(), MineContract.View {

    private var mAdapter: PreviewSelectAdapter? = null
    private var myCallBack:PicSwipeCallBack? = null
    private var itemTouchHelper: ItemTouchHelper? = null
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
    }

    private fun initRecyclerView(){
        mAdapter = PreviewSelectAdapter(requireContext(),9)
        mAdapter?.setSelectItemClickListener(object : PreviewSelectAdapter.MultiSelectItemClickListener {

            override fun addImageClick() {
                goPictureSelect()
            }

            override fun selectImageClick(position: Int) {
                //goImagePreview()
            }

            override fun selectImageLongClick(position: Int, holder: RecyclerView.ViewHolder) {
                itemTouchHelper?.startDrag(holder)
            }
        })
        recyclerView?.layoutManager =  StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        recyclerView?.addItemDecoration(SpaceItemDecoration(4))
        recyclerView?.adapter = mAdapter

        //绑定RecyclerView
        myCallBack = PicSwipeCallBack(mAdapter, imageList)
        itemTouchHelper = ItemTouchHelper(myCallBack!!)
        itemTouchHelper?.attachToRecyclerView(recyclerView)
        myCallBack?.setDragListener(object: PicSwipeCallBack.DragListener{
            override fun deleteState(delete: Boolean) {
                if (delete) {
                    tvDelete?.setBackgroundResource(R.color.colorPrimary)
                    tvDelete?.text = "释放手指进行删除"
                } else {
                    tvDelete?.text = "拖拽到此进行删除"
                }
            }

            override fun dragState(start: Boolean) {
                if (start) {
                    tvDelete?.visibility = View.VISIBLE
                } else {
                    tvDelete?.visibility = View.GONE
                }
            }

            override fun clearView() {
                imageList = myCallBack?.currentList as ArrayList<String>
            }
        })
    }

    private fun goPictureSelect(){
        if (Build.VERSION.SDK_INT in 23..28){
            EasyPermission.checkSingle(requireContext(), object : EasyPermission.OnGranted {
                override fun onGranted() {

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
            }, Permission.READ_EXTERNAL_STORAGE)
        }
    }
}