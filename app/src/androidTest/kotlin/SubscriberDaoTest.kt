import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.krivenchukartem.phonebook.data.PhoneBookDatabase
import ru.krivenchukartem.phonebook.data.Subscriber
import ru.krivenchukartem.phonebook.data.SubscriberDao
import java.io.IOException
import kotlin.jvm.Throws
import org.junit.Assert.assertEquals

@RunWith(AndroidJUnit4::class)
class SubscriberDaoTest {
    private lateinit var subscriberDao: SubscriberDao
    private lateinit var phoneBookDatabase: PhoneBookDatabase

    @Before
    fun createDb(){
        val context: Context = ApplicationProvider.getApplicationContext<Context>()
        phoneBookDatabase = Room.inMemoryDatabaseBuilder(context, PhoneBookDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        subscriberDao = phoneBookDatabase.subscriberDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDp(){
        phoneBookDatabase.close()
    }

    private var sub1 = Subscriber(1, "Найман Алексей Евгеньевич", "3495904305")
    private var sub2 = Subscriber(2, "Вилисов Илья Дмитриевич", "342909204")

    private suspend fun addOneSubToDb(){
        subscriberDao.insert(sub1)
    }

    private suspend fun addTwoSubToDb(){
        subscriberDao.insert(sub1)
        subscriberDao.insert(sub2)
    }

    @Test
    @Throws(IOException::class)
    fun daoInsert_insertSubIntoDB() = runBlocking {
        addOneSubToDb()
        val allSubs = subscriberDao.getAllSubscribers().first()
        assertEquals(allSubs[0], sub1)
    }

    @Test
    @Throws(IOException::class)
    fun daoInsert_insertTwoSubIntoDB() = runBlocking {
        addTwoSubToDb()
        val allSubs = subscriberDao.getAllSubscribers().first()
        assertEquals(allSubs[0], sub1)
        assertEquals(allSubs[1], sub2)
    }

    @Test
    @Throws(IOException::class)
    fun daoUpdate_updateSubAtDB() = runBlocking {
        addTwoSubToDb()
        subscriberDao.update(Subscriber(1, "абоба 1", "23428349"))
        subscriberDao.update(Subscriber(2, "боба 2", "43594003"))
        val allSubs = subscriberDao.getAllSubscribers().first()

        assertEquals(allSubs[0], Subscriber(1, "абоба 1", "23428349"))
        assertEquals(allSubs[1], Subscriber(2, "боба 2", "43594003"))
    }
}