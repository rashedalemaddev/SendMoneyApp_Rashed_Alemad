package com.uaeaa.sendmoneyapp.domain.usecases

import com.uaeaa.sendmoneyapp.domain.Field

interface IGetProviderFields {
    suspend   fun getFromFileds(lan :String,serviceBName:String,providerId:String):List<Field>

}