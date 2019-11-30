package com.lrm.base

import androidx.lifecycle.ViewModel
import com.lrm.dagger.DaggerViewModelInjector
import com.lrm.dagger.ViewModelInjector
import com.lrm.dashboard.DashboardViewModel
import com.lrm.retrofit.RequestManager
import com.lrm.ui.login.LoginViewModel

abstract class BaseViewModel: ViewModel(){
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(RequestManager)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is LoginViewModel -> injector.injectLoginViewModel(this)

            is DashboardViewModel -> injector.injectDashboardViewModel(this)
        }
    }

}