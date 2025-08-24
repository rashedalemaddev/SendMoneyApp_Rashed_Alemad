package com.uaeaa.sendmoneyapp.data.reposiotry.sendmoneyreposiotry

import android.content.Context
import android.util.Log
import com.uaeaa.sendmoneyapp.data.ConfigDto
import com.uaeaa.sendmoneyapp.data.datasource.local.historyrequest.RequestHistoryLocalDataSoruce
import com.uaeaa.sendmoneyapp.data.models.RequestHistoryEntity
import com.uaeaa.sendmoneyapp.data.toDomain
import com.uaeaa.sendmoneyapp.domain.Field
import com.uaeaa.sendmoneyapp.domain.Provider
import com.uaeaa.sendmoneyapp.domain.Service
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

class SendMoneyReposiotryImpl(val context: Context, val requestHistoryLocalDataSoruce: RequestHistoryLocalDataSoruce) : ISendMoneyReposiotry {
    val json = Json {
        ignoreUnknownKeys = true
    }
    private var cachedConfig: ConfigDto? = null

    fun getConfig(): ConfigDto {
        if (cachedConfig == null) {
            val inputStream = context.assets.open("datasource.json")
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            cachedConfig = json.decodeFromString<ConfigDto>(jsonString)
        }
        return cachedConfig!!
    }

    override suspend fun loadConfig(): ConfigDto {
        Log.d("loadingjson", "loadConfig: ${getConfig()}")
        return getConfig()
    }

    override suspend fun getServices(lang: String): List<Service> {
        Log.d("loadingjson", "loadConfig(lang).services: ${loadConfig().services}")

        return loadConfig().toDomain(lang).services

    }

    override suspend fun getProviders(lan: String, serviceName: String): List<Provider> {
        return loadConfig()
            .services
            .firstOrNull { it.name == serviceName }
            ?.providers
            ?.map { it.toDomain(lan) }
            ?: emptyList()
    }

    override suspend fun getFormFields(
        lang: String,
        serviceName: String,
        providerId: String
    ): List<Field> {
        val provider = getConfig().services
            .firstOrNull { it.name == serviceName }
            ?.providers
            ?.firstOrNull { it.id == providerId } ?: return emptyList()

        // Build localized schema

       return provider.required_fields.map { it.toDomain(lang)}?: emptyList()
    }

    override suspend fun sendRequest(requestHistoryEntity: RequestHistoryEntity) {
        requestHistoryLocalDataSoruce.insertRequest(requestHistoryEntity)

    }
}