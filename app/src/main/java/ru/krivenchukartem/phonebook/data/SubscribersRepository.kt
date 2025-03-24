package ru.krivenchukartem.phonebook.data

import kotlinx.coroutines.flow.Flow

interface SubscribersRepository {
    fun getAllSubscribersStream(): Flow<List<Subscriber>>

    fun getSubscriberStream(id: Int): Flow<Subscriber?>

    suspend fun insertSubscriber(subscriber: Subscriber)

    suspend fun updateSubscriber(subscriber: Subscriber)

    suspend fun deleteSubscriber(subscriber: Subscriber)
}