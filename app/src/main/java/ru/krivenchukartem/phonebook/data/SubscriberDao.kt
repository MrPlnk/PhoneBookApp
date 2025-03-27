package ru.krivenchukartem.phonebook.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SubscriberDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(subscriber: Subscriber)

    @Update
    suspend fun update(subscriber: Subscriber)

    @Delete
    suspend fun delete(subscriber: Subscriber)

    @Query("SELECT * FROM subscribers WHERE id = :id")
    fun getSubscriber(id: Int): Flow<Subscriber>

    @Query("SELECT * FROM subscribers ORDER BY fullName ASC")
    fun getAllSubscribers(): Flow<List<Subscriber>>

    @Query("SELECT * FROM subscribers" +
            " WHERE fullName LIKE '%' || :query || '%'" +
            " ORDER BY fullName ASC")
    fun getSubscribersByName(query: String): Flow<List<Subscriber>>
}