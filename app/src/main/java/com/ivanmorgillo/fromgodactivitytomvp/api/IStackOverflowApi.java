package com.ivanmorgillo.fromgodactivitytomvp.api;

import com.ivanmorgillo.fromgodactivitytomvp.api.models.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * This is the API for StackOverflow.
 */
public interface IStackOverflowApi {

    //http://api.stackexchange.com/2.2/search?order=desc&sort=activity&intitle=android&site=stackoverflow
    @GET("search?order=desc&sort=activity&site=stackoverflow&filter=!3yXvh9)gd0IKKXn31")
    Call<SearchResponse> getSearchResults(@Query("intitle") String titleSearchTerms);

    @GET("search?order=desc&sort=activity&site=stackoverflow&filter=!3yXvh9)gd0IKKXn31")
    Observable<SearchResponse> rxGetSeachResult(@Query("intitle") String titleSearchTerms);
}
