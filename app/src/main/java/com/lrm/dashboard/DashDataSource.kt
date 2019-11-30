package com.lrm.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.lrm.model.DashBoard
import com.lrm.model.DashBoardList
import com.lrm.retrofit.ApiInterface
import com.lrm.util.State
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class DashDataSource(private val token:String,
                     private val networkService: ApiInterface,
                     private val compositeDisposable: CompositeDisposable
)
    : PageKeyedDataSource<Int, DashBoard>() {

    var state: MutableLiveData<State> = MutableLiveData()
    private var retryCompletable: Completable? = null


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, DashBoard>) {
        updateState(State.LOADING)
        compositeDisposable.add(
            networkService.getDashBoardList(token,1, params.requestedLoadSize)
                .subscribe(
                    { response ->
                        updateState(State.DONE)
                        callback.onResult(response.data, null, 2)
                    },
                    {
                        it.printStackTrace()
                        updateState(State.ERROR)
                        setRetry(Action { loadInitial(params, callback) })
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, DashBoard>) {
        updateState(State.LOADING)
        compositeDisposable.add(
            networkService.getDashBoardList(token,params.key, params.requestedLoadSize)
                .subscribe(
                    { response ->
                        updateState(State.DONE)
                        callback.onResult(response.data, params.key + 1)
                    },
                    {
                        it.printStackTrace()
                        updateState(State.ERROR)
                        setRetry(Action { loadAfter(params, callback) })
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, DashBoard>) {
    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }

}