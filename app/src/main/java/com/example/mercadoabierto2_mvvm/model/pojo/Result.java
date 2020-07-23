package com.example.mercadoabierto2_mvvm.model.pojo;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class Result implements Serializable {


    @SerializedName("results")
    private List<Product> results;


    public Result() {

    }

    public List<Product> getResults() {
        return results;
    }

    public void setResults(List<Product> results) {
        this.results = results;
    }
}
