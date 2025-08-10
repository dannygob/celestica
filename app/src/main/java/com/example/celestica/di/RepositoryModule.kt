package com.example.celestic.di

import com.example.celestic.data.dao.CelesticDao
import com.example.celestic.data.repository.DetectionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDetectionRepository(celesticDao: CelesticDao): DetectionRepository {
        return DetectionRepository(celesticDao)
    }
}
