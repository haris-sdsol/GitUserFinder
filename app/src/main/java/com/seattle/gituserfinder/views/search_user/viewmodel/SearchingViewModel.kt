package com.seattle.gituserfinder.views.search_user.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableField
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

/**
 * @author Haris Bhatti
 * @see SearchingViewModel  this layer is used to deal with all
 * the business logics related to search user activity.
 * @param myApplication     this is context of application class
 * used to show toast or anything which requires context.
 */
class SearchingViewModel(val myApplication: Application) : AndroidViewModel(myApplication), Callback<UserInfo> {

    val TAG = SearchingViewModel::class.java.simpleName
    var mProgressDialog: SingleLiveEvent<Boolean> = SingleLiveEvent<Boolean>()
    var mUserInfo: MutableLiveData<UserInfo> = MutableLiveData<UserInfo>()
    var mSearchBar: ObservableField<String> = ObservableField<String>()
    var apiStatus: MutableLiveData<String> = MutableLiveData()

    fun getUserDetail() {
        if (MyApplication.hasNetwork()) {
            if (validateSearchBar()) {
                mProgressDialog.value = true
                RestApiBuilder.getClient()?.findUser(mSearchBar.get().toString())?.enqueue(this)
            } else
                Toast.makeText(myApplication, "Please Enter Username!", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * @see validateSearchBar this function is used to validate search bar field
     * so that user cannot proceed further without entering data
     */
    fun validateSearchBar(): Boolean {
        return !mSearchBar.get().isNullOrEmpty()
    }

    /**
     * @see onFailure   this method is being called when there's any issue occurred
     *                  while fetching data from server
     */
    override fun onFailure(call: Call<UserInfo>, t: Throwable) {
        mProgressDialog.value = false
        if (BuildConfig.DEBUG)
            Log.d(TAG, t.toString())
    }

    /**
     * @see onResponse    this function is being called when server is return response
     * @param response    this object contains alll the detail of user.
     */
    override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
        mProgressDialog.value = false
        if (response.isSuccessful) {
            mUserInfo.value = response.body()
        } else
            apiStatus.value = response.message()
    }
}
