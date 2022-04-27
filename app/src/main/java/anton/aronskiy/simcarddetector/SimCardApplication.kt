package anton.aronskiy.simcarddetector

import android.app.Application
import anton.aronskiy.simcarddetector.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class SimCardApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin{
            androidLogger(Level.ERROR)
            androidContext(this@SimCardApplication)
            modules(appModule)
        }
    }
}