package com.jywl.yezai.ui.content.search

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import com.jywl.yezai.R
import com.jywl.yezai.ui.content.BaseMvpFragment
import kotlinx.android.synthetic.main.fragment_target_search.*

/**
 * created by Buzz
 * on 2020/7/14
 * email lmx2060918@126.com
 */
class TargetSearchFragment : BaseMvpFragment<TargetSearchPresenter>(), TargetSearchContract.View {

    companion object {
        val instance: TargetSearchFragment
            get() = TargetSearchFragment()
    }

    override fun initInject() {
        getFragmentComponent().inject(this)
    }

    override fun layoutResID(): Int {
        return R.layout.fragment_target_search
    }

    override fun beforeOnCreate() {
    }

    override fun initEventView() {
        edSearch.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(ed: Editable) {
                if (ed.isNotEmpty()){
                    tvStartSearching.isEnabled = true
                    tvStartSearching.background = ContextCompat.getDrawable(requireContext(), R.drawable.shape_primary_button)
                }else{
                    tvStartSearching.isEnabled = false
                    tvStartSearching.background = ContextCompat.getDrawable(requireContext(), R.drawable.shape_primary_button_disabled)
                }
            }

            override fun beforeTextChanged(char: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(char: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        tvStartSearching.setOnClickListener {
            val intent = Intent(activity, SearchResultActivity::class.java)
            startActivity(intent)
        }
    }
}