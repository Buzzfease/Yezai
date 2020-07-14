package com.jywl.yezai.ui.content.search

import android.content.Intent
import android.view.View
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.jywl.yezai.R
import com.jywl.yezai.ui.content.BaseMvpFragment
import kotlinx.android.synthetic.main.fragment_condition_search.*

/**
 * created by Buzz
 * on 2020/7/14
 * email lmx2060918@126.com
 */
class ConditionSearchFragment : BaseMvpFragment<ConditionSearchPresenter>(), ConditionSearchContract.View, View.OnClickListener {

    private var pvOption: OptionsPickerView<String>? = null
    private var optType: Int = -1
    private var optionList = ArrayList<String>()

    companion object {
        val instance: ConditionSearchFragment
            get() = ConditionSearchFragment()

        const val TYPE_AGE = 0
        const val TYPE_AREA = 1
        const val TYPE_HEIGHT = 2
        const val TYPE_BODY_SIZE = 3
        const val TYPE_EDUCATION = 4
        const val TYPE_INCOME = 5

        const val TYPE_JOB = 6
        const val TYPE_JIGUAN = 7
        const val TYPE_HOUSE = 8
        const val TYPE_CAR = 9
        const val TYPE_MARRIAGE = 10
        const val TYPE_CHILD = 11
        const val TYPE_STAR = 12
        const val TYPE_HAS_AVATAR = 13
        const val TYPE_LOGIN_STATUS = 14
    }

    override fun initInject() {
        getFragmentComponent().inject(this)
    }

    override fun layoutResID(): Int {
        return R.layout.fragment_condition_search
    }

    override fun beforeOnCreate() {
    }

    override fun initEventView() {
        llAge.setOnClickListener(this)
        llArea.setOnClickListener(this)
        llHeight.setOnClickListener(this)
        llBodySize.setOnClickListener(this)
        llEducation.setOnClickListener(this)
        llIncome.setOnClickListener(this)
        llJob.setOnClickListener(this)
        llJiguan.setOnClickListener(this)
        llHouse.setOnClickListener(this)
        llCar.setOnClickListener(this)
        llMarriage.setOnClickListener(this)
        llChild.setOnClickListener(this)
        llStar.setOnClickListener(this)
        llHasAvatar.setOnClickListener(this)
        llLoginStatus.setOnClickListener(this)
        tvStartSearching.setOnClickListener(this)

        initOptionPicker()
    }

    private fun initOptionPicker(){
        pvOption = OptionsPickerBuilder(requireContext(), OnOptionsSelectListener { options1, _, _, v ->
            when (optType) {
                TYPE_AGE -> {
                    tvAge?.text = optionList[options1]
                }
                TYPE_AREA -> {
                    tvArea?.text = optionList[options1]
                }
                TYPE_HEIGHT -> {
                    tvHeight?.text = optionList[options1]
                }
                TYPE_BODY_SIZE -> {
                    tvBodySize?.text = optionList[options1]
                }
                TYPE_EDUCATION -> {
                    tvEducation?.text = optionList[options1]
                }
                TYPE_INCOME-> {
                    tvIncome?.text = optionList[options1]
                }
                TYPE_JOB -> {
                    tvJob?.text = optionList[options1]
                }
                TYPE_JIGUAN -> {
                    tvJiguan?.text = optionList[options1]
                }
                TYPE_HOUSE-> {
                    tvHouse?.text = optionList[options1]
                }
                TYPE_CAR -> {
                    tvCar?.text = optionList[options1]
                }
                TYPE_MARRIAGE-> {
                    tvMarriage?.text = optionList[options1]
                }
                TYPE_CHILD -> {
                    tvChild?.text = optionList[options1]
                }
                TYPE_STAR -> {
                    tvStar?.text = optionList[options1]
                }
                TYPE_HAS_AVATAR -> {
                    tvHasAvatar?.text = optionList[options1]
                }
                TYPE_LOGIN_STATUS -> {
                    tvLoginStatus?.text = optionList[options1]
                }
            }
        }).isDialog(false).build()
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.llAge -> {
                optType = TYPE_AGE
                optionList.clear()
                optionList.add("18-25岁")
                optionList.add("26-35岁")
                optionList.add("35岁以上")
                optionList.add("不限")
                pvOption?.setPicker(optionList)
                pvOption?.show()
            }
            R.id.llArea -> {
                optType = TYPE_AREA
                optionList.clear()
                optionList.add("本地")
                optionList.add("非本地")
                optionList.add("不限")
                pvOption?.setPicker(optionList)
                pvOption?.show()
            }
            R.id.llHeight -> {
                optType = TYPE_HEIGHT
                optionList.clear()
                optionList.add("160")
                optionList.add("170")
                optionList.add("180")
                optionList.add("不限")
                pvOption?.setPicker(optionList)
                pvOption?.show()
            }
            R.id.llBodySize -> {
                optType = TYPE_BODY_SIZE
                optionList.clear()
                optionList.add("瘦子")
                optionList.add("胖子")
                optionList.add("中等")
                optionList.add("不限")
                pvOption?.setPicker(optionList)
                pvOption?.show()
            }
            R.id.llEducation -> {
                optType = TYPE_EDUCATION
                optionList.clear()
                optionList.add("初中")
                optionList.add("高中")
                optionList.add("本科")
                optionList.add("硕士以上")
                optionList.add("不限")
                pvOption?.setPicker(optionList)
                pvOption?.show()
            }
            R.id.llIncome -> {
                optType = TYPE_INCOME
                optionList.clear()
                optionList.add("3000")
                optionList.add("4000")
                optionList.add("5000")
                optionList.add("6000")
                optionList.add("7000")
                optionList.add("不限")
                pvOption?.setPicker(optionList)
                pvOption?.show()
            }
            R.id.llJob -> {
                optType = TYPE_JOB
                optionList.clear()
                optionList.add("公务员")
                optionList.add("银行")
                optionList.add("医疗")
                optionList.add("教育")
                optionList.add("不限")
                pvOption?.setPicker(optionList)
                pvOption?.show()
            }
            R.id.llJiguan -> {
                optType = TYPE_JIGUAN
                optionList.clear()
                optionList.add("陕西")
                optionList.add("不限")
                pvOption?.setPicker(optionList)
                pvOption?.show()
            }
            R.id.llHouse -> {
                optType = TYPE_HOUSE
                optionList.clear()
                optionList.add("有房")
                optionList.add("无房")
                optionList.add("不限")
                pvOption?.setPicker(optionList)
                pvOption?.show()
            }
            R.id.llCar -> {
                optType = TYPE_CAR
                optionList.clear()
                optionList.add("有车")
                optionList.add("无车")
                optionList.add("不限")
                pvOption?.setPicker(optionList)
                pvOption?.show()
            }
            R.id.llMarriage -> {
                optType = TYPE_MARRIAGE
                optionList.clear()
                optionList.add("已婚")
                optionList.add("未婚")
                optionList.add("不限")
                pvOption?.setPicker(optionList)
                pvOption?.show()
            }
            R.id.llChild -> {
                optType = TYPE_CHILD
                optionList.clear()
                optionList.add("有子女")
                optionList.add("无子女")
                optionList.add("不限")
                pvOption?.setPicker(optionList)
                pvOption?.show()
            }
            R.id.llStar -> {
                optType = TYPE_STAR
                optionList.clear()
                optionList.add("白羊座")
                optionList.add("不限")
                pvOption?.setPicker(optionList)
                pvOption?.show()
            }
            R.id.llHasAvatar -> {
                optType = TYPE_HAS_AVATAR
                optionList.clear()
                optionList.add("有头像")
                optionList.add("不限")
                pvOption?.setPicker(optionList)
                pvOption?.show()
            }
            R.id.llLoginStatus -> {
                optType = TYPE_LOGIN_STATUS
                optionList.clear()
                optionList.add("经常登录")
                optionList.add("不限")
                pvOption?.setPicker(optionList)
                pvOption?.show()
            }
            R.id.tvStartSearching -> {
                val intent = Intent(activity, SearchResultActivity::class.java)
                startActivity(intent)
            }
        }
    }
}