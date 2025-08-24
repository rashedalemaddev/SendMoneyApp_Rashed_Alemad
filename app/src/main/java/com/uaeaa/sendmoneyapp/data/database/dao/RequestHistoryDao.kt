package com.uaeaa.sendmoneyapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uaeaa.sendmoneyapp.data.models.RequestHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RequestHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRequest(request: RequestHistoryEntity)

    @Query("SELECT * FROM request_history ORDER BY timestamp DESC")
    fun getAllRequests(): Flow<List<RequestHistoryEntity>>
}