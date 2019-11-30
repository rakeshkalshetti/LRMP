package com.lrm.ui.login

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.lrm.base.BaseViewModel
import com.lrm.model.LoginUser
import com.lrm.model.User
import com.lrm.retrofit.ApiInterface
import javax.inject.Inject

class LoginViewModel : BaseViewModel() {

    @Inject
    lateinit var api: ApiInterface

    var id = MutableLiveData<String>()
    var password = MutableLiveData<String>()

    private var userMutableLiveData: MutableLiveData<User>? = null

    private var mutableLiveData: MutableLiveData<LoginUser>? = null

    fun getUser(): MutableLiveData<User> {

        if (userMutableLiveData == null) {
            userMutableLiveData = MutableLiveData<User>()
        }
        return userMutableLiveData as MutableLiveData<User>

    }

    fun getLoginUser(): MutableLiveData<LoginUser> {
        if (mutableLiveData == null) {
            mutableLiveData = MutableLiveData()
        }
        return mutableLiveData as MutableLiveData<LoginUser>
    }

    fun onClick(view: View) {
        val loginUser = User(id.value, password.value, "", "")
        userMutableLiveData!!.setValue(loginUser)

    }

    fun login() {

        val user = User(id.value, password.value, "USERTYPE_EXISTING", "en")
        val call = api.login(user)

        call.enqueue(object : retrofit2.Callback<LoginUser> {
            override fun onFailure(k: retrofit2.Call<LoginUser>, t: Throwable) {
                mutableLiveData?.value = null
                t.printStackTrace()
            }

            override fun onResponse(
                call: retrofit2.Call<LoginUser>,
                response: retrofit2.Response<LoginUser>
            ) {
                mutableLiveData?.value = response.body()
            }

        })
    }
}
