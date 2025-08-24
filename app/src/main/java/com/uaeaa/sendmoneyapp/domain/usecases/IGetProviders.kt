package com.uaeaa.sendmoneyapp.domain.usecases

import com.uaeaa.sendmoneyapp.domain.Provider

interface IGetProviders {
    suspend   fun getProviders(lan :String,serviceBName:String):List<Provider>

}