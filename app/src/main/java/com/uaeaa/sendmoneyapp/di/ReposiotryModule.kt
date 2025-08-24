package com.uaeaa.sendmoneyapp.di

import android.content.Context
import com.uaeaa.sendmoneyapp.data.database.dao.RequestHistoryDao
import com.uaeaa.sendmoneyapp.data.datasource.local.historyrequest.RequestHistoryLocalDataSoruce
import com.uaeaa.sendmoneyapp.data.datasource.local.historyrequest.RequestHistoryLocalDataSoruceImpl
import com.uaeaa.sendmoneyapp.data.reposiotry.loginreposiotry.ILoginRepsoiotry
import com.uaeaa.sendmoneyapp.data.reposiotry.loginreposiotry.LoginRepositoryImpl
import com.uaeaa.sendmoneyapp.data.reposiotry.requestshistory.RequestHistoryReposiotry
import com.uaeaa.sendmoneyapp.data.reposiotry.requestshistory.RequestHistoryRepositoryImpl
import com.uaeaa.sendmoneyapp.data.reposiotry.sendmoneyreposiotry.ISendMoneyReposiotry
import com.uaeaa.sendmoneyapp.data.reposiotry.sendmoneyreposiotry.SendMoneyReposiotryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object ReposiotryModule {


    @Provides
    fun providesISendMoneyReposiotry(@ApplicationContext context: Context,requestHistoryLocalDataSoruce: RequestHistoryLocalDataSoruce): ISendMoneyReposiotry {
        return  SendMoneyReposiotryImpl(context,requestHistoryLocalDataSoruce)
    }
    @Provides
    fun providesRequestrHistoryReposiotry(requestHistoryLocalDataSoruce: RequestHistoryLocalDataSoruce): RequestHistoryReposiotry {
        return  RequestHistoryRepositoryImpl(requestHistoryLocalDataSoruce)
    }

    @Provides
    fun providesRequestHistoryLocalDataSoruce( requestHistoryDao: RequestHistoryDao): RequestHistoryLocalDataSoruce {
        return  RequestHistoryLocalDataSoruceImpl(requestHistoryDao)
    }
    @Provides
    fun providesIloginReposiotry( ): ILoginRepsoiotry {
        return  LoginRepositoryImpl()
    }
}
