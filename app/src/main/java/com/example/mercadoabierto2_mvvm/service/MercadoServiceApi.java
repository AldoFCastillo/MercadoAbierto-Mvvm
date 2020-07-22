package com.example.mercadoabierto2_mvvm.service;

import com.example.mercadoabierto2_mvvm.pojo.pojo.Description;
import com.example.mercadoabierto2_mvvm.pojo.pojo.ProductDetails;
import com.example.mercadoabierto2_mvvm.pojo.pojo.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MercadoServiceApi {

    @GET("/items/{path}")
    Call<ProductDetails> getProduct(@Path("path") String path);

    @GET("/sites/MLA/search")
    Call<Result> getSearch(@Query("q") String query, @Query("offset") Integer offset, @Query("limit") Integer limit);

    @GET ("/items/{path}/description")
    Call<Description> getDescription(@Path("path") String path);
}
