package com.ivanmorgillo.fromgodactivitytomvp.api

import com.google.gson.Gson
import com.ivanmorgillo.fromgodactivitytomvp.api.models.SearchResponse
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.observers.TestSubscriber
import java.io.File
import java.util.Scanner
import kotlin.test.assertEquals

class StackOverflowApiManagerTest {

    @Test
    @Throws(Exception::class)
    fun `sync request questions list for android term`() {
        val server = MockWebServer()
        val file = File(this.javaClass.classLoader.getResource("questions.json").file)
        val json = Scanner(file).useDelimiter("\\Z").next()
        server.enqueue(MockResponse().setBody(json))
        server.start()
        val baseUrl = server.url("/")

        val apiClient = StackOverflowApiManager(Gson(), File("/"), baseUrl.toString())

        val request = apiClient.doSearchForTitle("android")
        val response = request.execute()
        val actual = response.body()

        val expected = Gson().fromJson(json, SearchResponse::class.java)

        assertEquals(expected.questions, actual.questions)

        server.shutdown()
    }

    @Test
    fun `rx request questions list for android term`() {
        val server = MockWebServer()
        val file = File(this.javaClass.classLoader.getResource("questions.json").file)
        val json = Scanner(file).useDelimiter("\\Z").next()
        server.enqueue(MockResponse().setBody(json))
        server.start()
        val baseUrl = server.url("/")

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(OkHttpClient())
            .build();
        val apiClient = StackOverflowApiManager(retrofit)

        val subscriber = TestSubscriber<SearchResponse>()
        apiClient.getSearchResponse("android").subscribe(subscriber)
        subscriber.awaitTerminalEvent()
        val response = subscriber.onNextEvents
        val actual = response[0]

        val expected = Gson().fromJson(json, SearchResponse::class.java)

        assertEquals(expected.questions, actual.questions)

        server.shutdown()
    }
}
