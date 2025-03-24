package ru.krivenchukartem.phonebook.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subscribers")
data class Subscriber(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fullName: String = "",
    val phoneNumber: String = ""
)
