package com.lrm.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.lrm.model.DashBoard
import com.lrm.retrofit.ApiInterface
import io.reactivex.disposables.CompositeDisposable

class DashDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val networkService: ApiInterface)
    : DataSource.Factory<Int, DashBoard>() {

    val dashDataSourceLiveData = MutableLiveData<DashDataSource>()

    override fun create(): DataSource<Int, DashBoard> {
        val dashDataSource = DashDataSource(DashboardFragment.token, networkService, compositeDisposable)
        dashDataSourceLiveData.postValue(dashDataSource)
        return dashDataSource
    }
}