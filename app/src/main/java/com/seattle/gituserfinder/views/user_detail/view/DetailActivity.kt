package com.seattle.gituserfinder.views.user_detail.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.seattle.gituserfinder.R
import com.seattle.gituserfinder.model.UserInfo
import com.seattle.gituserfinder.utils.Constants
import com.seattle.gituserfinder.utils.CustomProgressDialog
import com.seattle.gituserfinder.utils.ImageLoader
import com.seattle.gituserfinder.views.search_user.viewmodel.SearchingViewModel
import com.seattle.gituserfinder.views.user_detail.adapter.FollowersListAdapter
import com.seattle.gituserfinder.views.user_detail.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {

    var mBinding: com.seattle.gituserfinder.databinding.ActivityDetailBinding? = null
    var mProgressDialog: CustomProgressDialog? = null
    var followersList: ArrayList<UserInfo>? = null
    var mViewModel: DetailViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        mViewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        mBinding?.viewModel = mViewModel
        mProgressDialog = CustomProgressDialog(this)

        followersList = ArrayList()
        mBinding?.followersRecyclerView?.layoutManager = LinearLayoutManager(this)
        mBinding?.followersRecyclerView?.adapter = FollowersListAdapter(this, followersList!!)

        updateViews()
        initObservables()

        mViewModel?.getFollowersList(intent.getStringExtra(Constants.USERNAME))

        mBinding?.backBtn?.setOnClickListener {
            finish()
        }

    }

    fun updateViews() {
        ImageLoader.loadCircularImage(intent.getStringExtra(Constants.PROFILE_URL), mBinding?.userProfileImg!!)
        mBinding?.usernameTv?.text = intent.getStringExtra(Constants.USERNAME)
        mBinding?.nameTv?.text = intent.getStringExtra(Constants.USERNAME)
        mBinding?.emailTv?.text = intent.getStringExtra(Constants.EMAIL)
    }

    private fun initObservables() {
        mViewModel?.mProgressDialog?.observe(this, Observer {
            if (it!!) mProgressDialog?.show() else mProgressDialog?.dismiss()
        })

        mViewModel?.mFollowersList?.observe(this, Observer { user ->
            followersList?.addAll(user)
            mBinding?.followersRecyclerView?.adapter?.notifyDataSetChanged()
        })
    }
}