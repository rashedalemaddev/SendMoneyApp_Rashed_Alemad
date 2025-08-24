package com.uaeaa.sendmoneyapp.di

import android.content.Context
import androidx.room.Room
import com.uaeaa.sendmoneyapp.data.database.AppDatabase
import com.uaeaa.sendmoneyapp.data.database.dao.RequestHistoryDao
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
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "send_money_db"
        )

            .build()
    }

    @Provides
    fun provideRequestHistoryDao(db: AppDatabase): RequestHistoryDao {
        return db.requestHistoryDao()
    }
}