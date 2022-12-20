package co.id.theztzt.presentation.featureModule

import co.id.theztzt.presentation.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val movieModule = module {
    viewModel { MovieViewModel(get()) }
}