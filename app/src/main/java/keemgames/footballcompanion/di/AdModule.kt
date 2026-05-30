package keemgames.footballcompanion.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import keemgames.footballcompanion.core.initialization.AdMobInitializer
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AdModule {

    @Provides
    @Singleton
    fun provideAdMobInitializer(@ApplicationContext context: Context): AdMobInitializer {
        return AdMobInitializer(context)
    }
}
