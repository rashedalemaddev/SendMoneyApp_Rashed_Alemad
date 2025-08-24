package com.uaeaa.sendmoneyapp.di

import com.uaeaa.sendmoneyapp.data.reposiotry.loginreposiotry.ILoginRepsoiotry
import com.uaeaa.sendmoneyapp.data.reposiotry.requestshistory.RequestHistoryReposiotry
import com.uaeaa.sendmoneyapp.data.reposiotry.sendmoneyreposiotry.ISendMoneyReposiotry
import com.uaeaa.sendmoneyapp.domain.usecases.dynamicfileds.GetProviderFieldsImpl
import com.uaeaa.sendmoneyapp.domain.usecases.provider.GetProvidersImpl
import com.uaeaa.sendmoneyapp.domain.usecases.service.GetServiceUseCaseImpl
import com.uaeaa.sendmoneyapp.domain.usecases.dynamicfileds.IGetProviderFields
import com.uaeaa.sendmoneyapp.domain.usecases.provider.IGetProviders
import com.uaeaa.sendmoneyapp.domain.usecases.service.IGetServiceUseCase
import com.uaeaa.sendmoneyapp.domain.usecases.login.ILoginUseCase
import com.uaeaa.sendmoneyapp.domain.usecases.login.ILoginUseCaseImpl
import com.uaeaa.sendmoneyapp.domain.usecases.history.IRequestHistoryUseCase
import com.uaeaa.sendmoneyapp.domain.usecases.sendmoney.ISendMoneyUseCase
import com.uaeaa.sendmoneyapp.domain.usecases.sendmoney.ISendMoneyUseCaseImpl
import com.uaeaa.sendmoneyapp.domain.usecases.fieldsvalidation.IValidateFormUseCase
import com.uaeaa.sendmoneyapp.domain.usecases.loginvalidation.IValidateLoginUseCase
import com.uaeaa.sendmoneyapp.domain.usecases.history.RequestHistoryUseCaseImpl
import com.uaeaa.sendmoneyapp.domain.usecases.fieldsvalidation.ValidateFormUseCaseImpl
import com.uaeaa.sendmoneyapp.domain.usecases.loginvalidation.ValidateLoginUseCaseImpl
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