package com.supersonic.heartrate

import android.content.Context
import com.supersonic.heartrate.db.HearRateDatabase
import com.supersonic.heartrate.db.HeartRateDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHearRateDatabase(@ApplicationContext context: Context): HearRateDatabase {
        return HearRateDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideHeartRateDao(hearRateDatabase: HearRateDatabase): HeartRateDao {
        return hearRateDatabase.heartRateDao()
    }
}