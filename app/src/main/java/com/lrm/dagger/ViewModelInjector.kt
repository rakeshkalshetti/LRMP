package com.lrm.dagger

import com.lrm.dashboard.DashboardViewModel
import com.lrm.retrofit.RequestManager
import com.lrm.ui.login.LoginViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(RequestManager::class)])
interface ViewModelInjector {

    fun injectLoginViewModel(postListViewModel: LoginViewModel)

    fun injectDashboardViewModel(dashboardViewModel: DashboardViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: RequestManager): Builder
    }
}