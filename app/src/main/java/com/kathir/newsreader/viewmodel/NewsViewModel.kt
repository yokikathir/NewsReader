package com.kathir.newsreader.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kathir.newsreader.repository.NewsRepository
import androidx.lifecycle.LiveData
import com.kathir.newsreader.entity.Newsdb

/**
 * Created by KATHIR on 12-01-2022.
 */
class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository: NewsRepository = NewsRepository(application)

    val allProduct: LiveData<MutableList<Newsdb>> = mRepository.getmAllQuestions()

    fun insert(word: Newsdb?) {
        mRepository.insert(word)
    }

    fun deleteAll() {
        mRepository.deleteAll()
    }

    override fun onCleared() {
        super.onCleared()
        allProduct.value!!.clear()
    }

}