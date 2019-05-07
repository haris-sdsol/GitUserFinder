package com.seattle.gituserfinder.views.search_user.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.seattle.gituserfinder.R
import com.seattle.gituserfinder.databinding.ActivitySearchBinding
import com.seattle.gituserfinder.model.UserInfo
import com.seattle.gituserfinder.utils.CustomProgressDialog
import com.seattle.gituserfinder.utils.ImageLoader
import com.seattle.gituserfinder.views.search_user.viewmodel.SearchingViewModel

class SearchingActivity : AppCompatActivity() {

    var mProgressDialog: CustomProgressDialog? = null
    var mViewModel: SearchingViewModel? = null
    var mBinding: ActivitySearchBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        mViewModel = ViewModelProviders.of(this).get(SearchingViewModel::class.java)
        mBinding?.viewModel = mViewModel
        mProgressDialog = CustomProgressDialog(this)
        initObservables()

        mBinding?.searchBtn?.setOnClickListener {
            mViewModel?.getUserDetail()
        }
    }

    fun initObservables() {
        mViewModel?.mProgressDialog?.observe(this, Observer {
            if (it!!) mProgressDialog?.show() else mProgressDialog?.dismiss()
        })

        mViewModel?.mUserInfo?.observe(this, Observer { user ->
            if (user != null) {
                mBinding?.noMatchFoundTv?.visibility = View.GONE
                updateViews(user)
            } else {
                mBinding?.noMatchFoundTv?.text = resources.getText(R.string.sorry_no_match_found)
                mBinding?.noMatchFoundTv?.visibility = View.VISIBLE
            }
        })
    }

    fun updateViews(userInfo: UserInfo) {
        mBinding?.userShortDetailLayout?.visibility = View.VISIBLE
        ImageLoader.loadCircularImage(userInfo.avatarUrl!!, mBinding?.userProfileImg!!)
        mBinding?.usernameTv?.text = userInfo.name

        mBinding?.seeMoreDetailBtn?.setOnClickListener {

        }
    }
}