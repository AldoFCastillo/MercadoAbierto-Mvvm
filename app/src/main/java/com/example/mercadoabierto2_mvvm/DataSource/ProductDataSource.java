package com.example.mercadoabierto2_mvvm.DataSource;


import androidx.annotation.NonNull;

import androidx.paging.PageKeyedDataSource;

import com.example.mercadoabierto2_mvvm.pojo.pojo.Product;
import com.example.mercadoabierto2_mvvm.pojo.pojo.Result;
import com.example.mercadoabierto2_mvvm.service.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDataSource extends PageKeyedDataSource<Integer, Product> {

    public static final int LIMIT = 10;
    private static final int FIRST_PAGE = 0;
    String query;

    public ProductDataSource() {

    }


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Product> callback) {

        RetrofitInstance.getInstance()
                .getMercadoApiService()
                .getSearch(query, FIRST_PAGE, LIMIT)
                .enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                if(response.body() != null){

                    callback.onResult(response.body().getResults(), null, FIRST_PAGE + 10);

                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                String message = t.getMessage();
                System.out.println("ERROR" + message);
                t.printStackTrace();
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Product> callback) {

        RetrofitInstance.getInstance().getMercadoApiService().getSearch(query, params.key, LIMIT).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                if(response.body() != null){
                    Integer key = (params.key > 1) ? params.key - 10 : null;
                    callback.onResult(response.body().getResults(), key);
                }
            }




            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                String message = t.getMessage();
                System.out.println("ERROR" + message);
                t.printStackTrace();}

        });

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Product> callback) {

        RetrofitInstance.getInstance().getMercadoApiService().getSearch(query, params.key, LIMIT).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                if(response.body() != null){
                    Result result = response.body();
                    Integer key = result.getResults().size()==LIMIT?params.key + 10 : null;
                    callback.onResult(response.body().getResults(), key);
                }
            }




            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                String message = t.getMessage();
                System.out.println("ERROR" + message);
                t.printStackTrace();}

        });

    }



}
