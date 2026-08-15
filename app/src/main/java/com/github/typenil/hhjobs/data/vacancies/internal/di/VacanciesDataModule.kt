package com.github.typenil.hhjobs.data.vacancies.internal.di

import com.github.typenil.hhjobs.data.vacancies.VacancyRepository
import com.github.typenil.hhjobs.data.vacancies.internal.DefaultVacancyRepository
import com.github.typenil.hhjobs.data.vacancies.internal.remote.HeadHunterApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class VacanciesDataModule {

    @Binds
    @Singleton
    abstract fun bindVacancyRepository(
        impl: DefaultVacancyRepository,
    ): VacancyRepository

    companion object {
        @Provides
        @Singleton
        fun provideHeadHunterApiService(retrofit: Retrofit): HeadHunterApiService {
            return retrofit.create(HeadHunterApiService::class.java)
        }
    }
}
