package com.uaeaa.sendmoneyapp.di

import com.uaeaa.sendmoneyapp.data.reposiotry.loginreposiotry.ILoginRepsoiotry
import com.uaeaa.sendmoneyapp.data.reposiotry.requestshistory.RequestHistoryReposiotry
import com.uaeaa.sendmoneyapp.data.reposiotry.sendmoneyreposiotry.ISendMoneyReposiotry
import com.uaeaa.sendmoneyapp.domain.usecases.GetProviderFieldsImpl
import com.uaeaa.sendmoneyapp.domain.usecases.GetProvidersImpl
import com.uaeaa.sendmoneyapp.domain.usecases.GetServiceUseCaseImpl
import com.uaeaa.sendmoneyapp.domain.usecases.IGetProviderFields
import com.uaeaa.sendmoneyapp.domain.usecases.IGetProviders
import com.uaeaa.sendmoneyapp.domain.usecases.IGetServiceUseCase
import com.uaeaa.sendmoneyapp.domain.usecases.ILoginUseCase
import com.uaeaa.sendmoneyapp.domain.usecases.ILoginUseCaseImpl
import com.uaeaa.sendmoneyapp.domain.usecases.IRequestHistoryUseCase
import com.uaeaa.sendmoneyapp.domain.usecases.ISendMoneyUseCase
import com.uaeaa.sendmoneyapp.domain.usecases.ISendMoneyUseCaseImpl
import com.uaeaa.sendmoneyapp.domain.usecases.IValidateFormUseCase
import com.uaeaa.sendmoneyapp.domain.usecases.IValidateLoginUseCase
import com.uaeaa.sendmoneyapp.domain.usecases.RequestHistoryUseCaseImpl
import com.uaeaa.sendmoneyapp.domain.usecases.ValidateFormUseCaseImpl
import com.uaeaa.sendmoneyapp.domain.usecases.ValidateLoginUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun getServiceUseCase(iSendMoneyReposiotry: ISendMoneyReposiotry): IGetServiceUseCase {
        return GetServiceUseCaseImpl(iSendMoneyReposiotry)
    }


    @Provides
    fun getProvidersUseCase(iSendMoneyReposiotry: ISendMoneyReposiotry): IGetProviders {
        return GetProvidersImpl(iSendMoneyReposiotry)
    }


    @Provides
    fun getPRoviderFeildsUseCase(iSendMoneyReposiotry: ISendMoneyReposiotry): IGetProviderFields {
        return GetProviderFieldsImpl(iSendMoneyReposiotry)
    }

    @Provides
    fun getIValidateUseCase(): IValidateFormUseCase {
        return ValidateFormUseCaseImpl()
    }

    @Provides
    fun getISendMoneyUseCase(reposiotry: ISendMoneyReposiotry): ISendMoneyUseCase {
        return ISendMoneyUseCaseImpl(reposiotry)
    }

    @Provides
    fun getIRequesthistoryCase(requestHistoryReposiotry: RequestHistoryReposiotry): IRequestHistoryUseCase {
        return RequestHistoryUseCaseImpl(requestHistoryReposiotry)
    }

    @Provides
    fun getIValidationCase(): IValidateLoginUseCase {
        return ValidateLoginUseCaseImpl()
    }

    @Provides
    fun getILoginCase(iLoginRepsoiotry: ILoginRepsoiotry): ILoginUseCase {
        return ILoginUseCaseImpl(iLoginRepsoiotry)
    }
}