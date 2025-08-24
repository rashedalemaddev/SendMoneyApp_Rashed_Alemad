package com.uaeaa.sendmoneyapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "request_history")
data class RequestHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val requestId: Long = 0,

    val serviceName: String,
    val providerId: String,
    val providerName: String,
    val amount: String, // keep as String, easy to display & store
    val dataJson: String, // serialized JSON of values map
    val timestamp: Long = System.currentTimeMillis()
)