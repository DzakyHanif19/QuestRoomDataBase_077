package com.example.project9.Data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.project9.Data.dao.MahasiswaDao
import com.example.project9.Data.entity.Mahasiswa
import kotlin.concurrent.Volatile

@Database(entities = [Mahasiswa::class], version = 1, exportSchema = false)
abstract class  KrsDatabase : RoomDatabase(){

    abstract fun mahasiswaDao(): MahasiswaDao

    companion object{
        @Volatile
        private var instance: KrsDatabase? = null

        fun getDatabase(context: Context):KrsDatabase{
            return (instance ?: synchronized(this){
                Room.databaseBuilder(
                    context,
                    KrsDatabase:: class.java,
                    "KrsDatabase"
                )
                    .build().also { instance = it }
            })
        }
    }
}