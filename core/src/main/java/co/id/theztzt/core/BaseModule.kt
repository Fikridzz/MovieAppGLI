package co.id.theztzt.core

import co.id.theztzt.data.repository.MovieDataStore
import co.id.theztzt.data.repository.MovieRepository
import co.id.theztzt.domain.usecase.MovieInteractor
import co.id.theztzt.domain.usecase.MovieUseCase
import org.koin.dsl.module

val baseModule = module {

    single<MovieRepository> { MovieDataStore(get()) }

    factory<MovieUseCase> { MovieInteractor(get()) }

}