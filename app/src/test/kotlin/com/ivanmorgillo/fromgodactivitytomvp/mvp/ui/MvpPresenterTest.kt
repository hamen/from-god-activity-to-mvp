package com.ivanmorgillo.fromgodactivitytomvp.mvp.ui

import com.google.gson.Gson
import com.ivanmorgillo.fromgodactivitytomvp.api.StackOverflowApiManager
import com.ivanmorgillo.fromgodactivitytomvp.api.models.SearchResponse
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.After
import org.junit.Before
import org.junit.Test
import rx.Observable
import rx.Scheduler
import rx.android.plugins.RxAndroidPlugins
import rx.android.plugins.RxAndroidSchedulersHook
import rx.plugins.RxJavaHooks
import rx.schedulers.Schedulers
import java.io.File
import java.util.Scanner

class MvpPresenterTest {

    val file = File(this.javaClass.classLoader.getResource("questions.json").file)
    val json = Scanner(file).useDelimiter("\\Z").next()
    val searchResponse = Gson().fromJson(json, SearchResponse::class.java)

    val apiClient: StackOverflowApiManager = mock()
    val presenter = MvpPresenter(apiClient)
    val view: IMvpView = mock()

    @Before
    fun setUp() {
        whenever(apiClient.getSearchResponse("android")).thenReturn(Observable.just(searchResponse))
        presenter.setView(view)

        RxJavaHooks.setOnIOScheduler { Schedulers.immediate() }
        RxAndroidPlugins.getInstance().registerSchedulersHook(object : RxAndroidSchedulersHook() {
            override fun getMainThreadScheduler(): Scheduler {
                return Schedulers.immediate()
            }
        })
    }

    @After
    fun tearDown() {
        RxJavaHooks.reset();
        RxAndroidPlugins.getInstance().reset()
    }

    @Test
    @Throws(Exception::class)
    fun loadQuestions() {
        presenter.loadQuestions()

        verify(view).showProgress()
        verify(view).updateList(searchResponse.questions)
        verify(view).hideProgress()
    }
}
