package ru.krivenchukartem.phonebook.data

import kotlinx.coroutines.flow.Flow

class OfflineSubscribersRepository(private val subscriberDao: SubscriberDao): SubscribersRepository {
    override fun getSubscriberStream(id: Int): Flow<Subscriber?> = subscriberDao.getSubscriber(id)

    override fun getAllSubscribersStream(): Flow<List<Subscriber>> = subscriberDao.getAllSubscribers()

    override suspend fun deleteSubscriber(subscriber: Subscriber) = subscriberDao.delete(subscriber)

    override suspend fun insertSubscriber(subscriber: Subscriber) = subscriberDao.insert(subscriber)

    override suspend fun updateSubscriber(subscriber: Subscriber) = subscriberDao.update(subscriber)

    override fun getSubscribersByNameStream(query: String): Flow<List<Subscriber>> = subscriberDao.getSubscribersByName(query)
}