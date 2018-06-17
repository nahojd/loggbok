package se.uav.loggbok.di

import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import se.uav.loggbok.model.TripRepository
import se.uav.loggbok.model.TripRepositoryImpl
import se.uav.loggbok.model.TripViewModel

val appModule: Module = applicationContext {
    viewModel { TripViewModel(get()) }
    bean { TripRepositoryImpl(this.androidApplication()) as TripRepository }
}