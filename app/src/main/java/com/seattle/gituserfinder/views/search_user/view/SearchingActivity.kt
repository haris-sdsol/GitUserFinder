package com.seattle.gituserfinder.views.search_user.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.seattle.gituserfinder.R
import com.seattle.gituserfinder.databinding.ActivitySearchBinding
import com.seattle.gituserfinder.model.UserInfo
import com.seattle.gituserfinder.utils.Constants
import com.seattle.gituserfinder.utils.CustomProgressDialog
import com.seattle.gituserfinder.utils.ImageLoader
import com.seattle.gituserfinder.views.search_user.viewmodel.SearchingViewModel
import com.seattle.gituserfinder.views.user_detail.view.DetailActivity

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

    private fun initObservables() {
        mViewModel?.mProgressDialog?.observe(this, Observer {
            if (it!!) mProgressDialog?.show() else mProgressDialog?.dismiss()
        })

        mViewModel?.mUserInfo?.observe(this, Observer { user ->
            mBinding?.noMatchFoundTv?.visibility = View.GONE
            updateViews(user)
        })

        mViewModel?.apiStatus?.observe(this, Observer {
            mBinding?.noMatchFoundTv?.text = it
            mBinding?.noMatchFoundTv?.visibility = View.VISIBLE
            mBinding?.userShortDetailLayout?.visibility = View.GONE
        })
    }

    fun updateViews(userInfo: UserInfo) {
        mBinding?.userShortDetailLayout?.visibility = View.VISIBLE
        ImageLoader.loadCircularImage(userInfo.avatarUrl!!, mBinding?.userProfileImg!!)
        mBinding?.usernameTv?.text = userInfo.login

        mBinding?.seeMoreDetailBtn?.setOnClickListener {
            startActivity(
                Intent(this@SearchingActivity, DetailActivity::class.java)
                    .putExtra(
                        Constants.USERNAME,
                        userInfo.login
                    ).putExtra(
                        Constants.EMAIL,
                        userInfo.email
                    ).putExtra(
                        Constants.PROFILE_URL,
                        userInfo.avatarUrl
                    )
            )
        }
    }
}