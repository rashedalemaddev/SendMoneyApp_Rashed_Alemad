package com.uaeaa.sendmoneyapp.domain.usecases

import arrow.core.Either
import com.uaeaa.sendmoneyapp.domain.Field
import com.uaeaa.sendmoneyapp.domain.Provider
import com.uaeaa.sendmoneyapp.domain.Service

interface IGetServiceUseCase {
    suspend   fun getServices(lan :String):List<Service>


}