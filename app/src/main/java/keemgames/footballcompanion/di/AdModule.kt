package keemgames.footballcompanion.di

import android.content.Context
import keemgames.footballcompanion.core.initialization.AppLovinInitializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AdModule {

    @Provides
    @Singleton
    fun provideAppLovinInitializer(@ApplicationContext context: Context): AppLovinInitializer {
        return AppLovinInitializer(context)
    }
}
