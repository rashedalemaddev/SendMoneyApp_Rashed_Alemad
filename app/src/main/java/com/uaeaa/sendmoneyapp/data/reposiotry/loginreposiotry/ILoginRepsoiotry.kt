package com.uaeaa.sendmoneyapp.data.reposiotry.loginreposiotry

import arrow.core.Either

interface ILoginRepsoiotry {

    suspend   fun login(userName:String,password:String): Either<String, String>

}