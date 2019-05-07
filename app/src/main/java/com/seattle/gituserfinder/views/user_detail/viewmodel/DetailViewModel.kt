package com.seattle.gituserfinder.views.user_detail.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.seattle.gituserfinder.BuildConfig
import com.seattle.gituserfinder.application.MyApplication
import com.seattle.gituserfinder.model.UserInfo
import com.seattle.gituserfinder.network_service.RestApiBuilder
import com.seattle.gituserfinder.utils.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(val myApplication: Application) : AndroidViewModel(myApplication), Callback<List<UserInfo>> {

    val TAG = DetailViewModel::class.java.simpleName
    var mProgressDialog: SingleLiveEvent<Boolean> = SingleLiveEvent<Boolean>()
    var mFollowersList: MutableLiveData<List<UserInfo>> =MutableLiveData<List<UserInfo>>()
    var apiStatus: MutableLiveData<String> = MutableLiveData()

    fun getFollowersList(username: String) {
        if (MyApplication.hasNetwork()) {
            mProgressDialog.value = true
            RestApiBuilder.getClient()?.getFollowers(username)?.enqueue(this)
        }
    }

    /**
     * @see onFailure   this method is being called when there's any issue occurred
     *                  while fetching data from server
     */
    override fun onFailure(call: Call<List<UserInfo>>, t: Throwable) {
        mProgressDialog.value = false
        if (BuildConfig.DEBUG)
            Log.d(TAG, t.toString())
    }

    /**
     * @see onResponse    this function is being called when server is return response
     * @param response    this object contains all the detail of user.
     */
    override fun onResponse(call: Call<List<UserInfo>>, response: Response<List<UserInfo>>) {
        mProgressDialog.value = false
        if (response.isSuccessful) {
            mFollowersList.value=response.body()
        } else
            apiStatus.value = response.message()
    }
}