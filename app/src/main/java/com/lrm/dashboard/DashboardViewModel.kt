package com.lrm.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.lrm.base.BaseViewModel
import com.lrm.model.DashBoard
import com.lrm.retrofit.ApiInterface
import com.lrm.util.State
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class DashboardViewModel : BaseViewModel() {
    // TODO: Implement the ViewModel

    @Inject
    lateinit var apiInterface: ApiInterface

    var dashList: LiveData<PagedList<DashBoard>>
    private val compositeDisposable = CompositeDisposable()
    private val pageSize = 10
    private val dashDataSourceFactory: DashDataSourceFactory

    init {
        dashDataSourceFactory = DashDataSourceFactory(compositeDisposable, apiInterface)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()
        dashList = LivePagedListBuilder<Int, DashBoard>(dashDataSourceFactory, config).build()
    }


    fun getState(): LiveData<State> = Transformations.switchMap<DashDataSource,
            State>(dashDataSourceFactory.dashDataSourceLiveData, DashDataSource::state)

    fun retry() {
        dashDataSourceFactory.dashDataSourceLiveData.value?.retry()
    }

    fun listIsEmpty(): Boolean {
        return dashList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}

