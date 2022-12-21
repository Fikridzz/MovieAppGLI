package co.id.theztzt.movieappgli.utils

import android.app.Application
import co.id.theztzt.core.baseModule
import co.id.theztzt.core.networkModule
import co.id.theztzt.presentation.featureModule.detailModule
import co.id.theztzt.presentation.featureModule.movieModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@AppApplication)
            modules(
                listOf(
                    networkModule,
                    baseModule,
                    movieModule,
                    detailModule
                )
            )
        }
    }
}