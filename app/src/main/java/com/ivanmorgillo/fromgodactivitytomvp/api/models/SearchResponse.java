package com.ivanmorgillo.fromgodactivitytomvp.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.Getter;

@Data
public class SearchResponse {

    @Expose
    @Getter
    @SerializedName("items")
    private List<Question> questions;
}
