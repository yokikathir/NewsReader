package com.kathir.newsreader.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.kathir.newsreader.dao.CalDao
import com.kathir.newsreader.db.NewsRoomDatabasee
import com.kathir.newsreader.entity.Newsdb

/**
 * Created by KATHIR on 12-01-2022.
 */
class NewsRepository(application: Application?) {

    private val mQuestionsDao: CalDao
    private val mAllQuestions: LiveData<MutableList<Newsdb>>

    fun getmAllQuestions(): LiveData<MutableList<Newsdb>> {
        return mAllQuestions
    }

    fun insert(word: Newsdb?) {
        insertAsyncTask(mQuestionsDao).execute(word)
    }

    fun deleteAll() {
        deleteAllWordsAsyncTask(mQuestionsDao).execute()
    }

    private class insertAsyncTask(private val mAsyncTaskDao: CalDao) :
        AsyncTask<Newsdb?, Void?, Void?>() {

        override fun doInBackground(vararg params: Newsdb?): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }

    }

    private class deleteAllWordsAsyncTask(private val mAsyncTaskDao: CalDao) :
        AsyncTask<Void?, Void?, Void?>() {

        override fun doInBackground(vararg params: Void?): Void? {
            mAsyncTaskDao.delete()
            return null
        }

    }

    init {
        val db = application?.let { NewsRoomDatabasee.getNoteRoomDatabase(it) }
        mQuestionsDao = db?.noteDao()!!
        mAllQuestions = mQuestionsDao.allNotes
    }

}