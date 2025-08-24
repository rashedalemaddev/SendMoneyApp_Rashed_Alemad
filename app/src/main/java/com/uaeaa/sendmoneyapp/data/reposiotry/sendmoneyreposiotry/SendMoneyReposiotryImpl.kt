package com.uaeaa.sendmoneyapp.data.reposiotry.sendmoneyreposiotry

import android.content.Context
import arrow.core.Either
import com.uaeaa.sendmoneyapp.data.models.ConfigDto
import com.uaeaa.sendmoneyapp.data.datasource.local.historyrequest.RequestHistoryLocalDataSoruce
import com.uaeaa.sendmoneyapp.data.models.RequestHistoryEntity
import com.uaeaa.sendmoneyapp.data.models.toDomain
import com.uaeaa.sendmoneyapp.domain.models.Failure
import com.uaeaa.sendmoneyapp.domain.models.Field
import com.uaeaa.sendmoneyapp.domain.models.Provider
import com.uaeaa.sendmoneyapp.domain.models.Service
import kotlinx.serialization.json.Json

class SendMoneyReposiotryImpl(
    val context: Context,
    val requestHistoryLocalDataSoruce: RequestHistoryLocalDataSoruce
) : ISendMoneyReposiotry {
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

    override suspend fun loadConfig(): Either<Failure, ConfigDto> {
        return try {
            Either.Right(getConfig())
        } catch (e: Exception) {
            Either.Left(Failure("Failed to load config"))
        }

    }

    override suspend fun getServices(lang: String): Either<Failure, List<Service>> {
        return try {
            val serives = loadConfig().fold(
                ifLeft = { return Either.Left(it) },
                ifRight = { it.toDomain(lang).services }
            )
            Either.Right(serives)
        } catch (e: Exception) {
            Either.Left(Failure("Faluied to load "))
        }

    }

    override suspend fun getProviders(
        lan: String,
        serviceName: String
    ): Either<Failure, List<Provider>> {
        return try {
            val providers = loadConfig().fold(
                ifLeft = { return Either.Left(it) },
                ifRight = {
                    it.services
                        .firstOrNull { it.name == serviceName }
                        ?.providers
                        ?.map { it.toDomain(lan) }
                        ?: emptyList()
                }
            )
            Either.Right(providers)

        } catch (e: Exception) {
            Either.Left(Failure("Failed to get providers"))
        }


    }

    override suspend fun getFormFields(
        lang: String,
        serviceName: String,
        providerId: String
    ): Either<Failure, List<Field>> {

        return try {
            val fields = loadConfig().fold(
                ifLeft = { return Either.Left(it) },
                ifRight = { config ->
                    val provider = config.services
                        .firstOrNull { it.name == serviceName }
                        ?.providers
                        ?.firstOrNull { it.id == providerId } ?: return Either.Right(emptyList())
                    provider.required_fields.map { it.toDomain(lang) }
                }
            )
            Either.Right(fields)
        } catch (e: Exception) {
            Either.Left(Failure("Failed to get form fields"))
        }
    }

    override suspend fun sendRequest(requestHistoryEntity: RequestHistoryEntity): Either<Failure, Unit> {
        return try {
            requestHistoryLocalDataSoruce.insertRequest(requestHistoryEntity)
            Either.Right(Unit)
        } catch (e: Exception) {
            Either.Left(Failure("Failed to send request"))
        }

    }
}