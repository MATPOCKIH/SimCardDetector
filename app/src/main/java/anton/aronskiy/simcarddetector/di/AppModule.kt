package anton.aronskiy.simcarddetector.di

import anton.aronskiy.simcarddetector.MainViewModel
import anton.aronskiy.simcarddetector.providers.SimInfoProvider
import anton.aronskiy.simcarddetector.providers.SimInfoProviderImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    factory <SimInfoProvider> { SimInfoProviderImpl(context = get()) }

    viewModel { MainViewModel(simInfoProvider = get()) }

}