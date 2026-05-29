package keemgames.footballcompanion.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import keemgames.footballcompanion.data.repository.FootballRepositoryImpl
import keemgames.footballcompanion.data.repository.PreferencesRepositoryImpl
import keemgames.footballcompanion.domain.repository.FootballRepository
import keemgames.footballcompanion.domain.repository.PreferencesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindFootballRepository(
        footballRepositoryImpl: FootballRepositoryImpl
    ): FootballRepository

    @Binds
    @Singleton
    abstract fun bindPreferencesRepository(
        preferencesRepositoryImpl: PreferencesRepositoryImpl
    ): PreferencesRepository
}
