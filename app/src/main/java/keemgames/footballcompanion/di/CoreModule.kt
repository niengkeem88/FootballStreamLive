package keemgames.footballcompanion.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import keemgames.footballcompanion.core.analytics.AnalyticsHelper
import keemgames.footballcompanion.core.analytics.AnalyticsHelperImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CoreModule {

    @Binds
    @Singleton
    abstract fun bindAnalyticsHelper(
        analyticsHelperImpl: AnalyticsHelperImpl
    ): AnalyticsHelper
}
