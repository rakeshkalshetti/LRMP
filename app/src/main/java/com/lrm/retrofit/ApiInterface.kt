package com.lrm.retrofit

import com.lrm.model.DashBoardList
import com.lrm.model.LoginUser
import com.lrm.model.User
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.Call
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.http.*

interface ApiInterface {

    @Headers("Content-Type: application/json")
    @POST("auth/login")
    fun login(@Body user: User) : retrofit2.Call<LoginUser>

    @Headers("Content-Type: application/json")
    @GET("connections/connections/all?")
    fun getDashBoardList(@Header("authorization") auth : String, @Query("offset") page: Int, @Query("limit") pageSize: Int) : Single<DashBoardList>

}