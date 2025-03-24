package ru.krivenchukartem.phonebook.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Subscriber::class], version = 1, exportSchema = false)
abstract class PhoneBookDatabase: RoomDatabase() {
    abstract fun subscriberDao(): SubscriberDao

    companion object{
        @Volatile
        private var Instance: PhoneBookDatabase? = null

        fun getDatabase(context: Context): PhoneBookDatabase{
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context, PhoneBookDatabase::class.java, "subscriber_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also{ Instance = it}
            }
        }
    }
}