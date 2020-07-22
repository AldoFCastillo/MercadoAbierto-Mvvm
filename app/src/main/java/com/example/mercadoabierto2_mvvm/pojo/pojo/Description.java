package com.example.mercadoabierto2_mvvm.pojo.pojo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mercadoabierto2_mvvm.service.MercadoServiceApi;
import com.example.mercadoabierto2_mvvm.service.RetrofitInstance;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Description implements Serializable {

    private MercadoServiceApi mercadoServiceApi;
    private MutableLiveData<Description> descriptionMutable;

    @SerializedName("text")
    private String text;
    @SerializedName("plain_text")
    private String plainText;
    @SerializedName("last_updated")
    private String lastUpdated;
    @SerializedName("date_created")
    private String dateCreated;
    /*@SerializedName("snapshot")
    private Snapshot snapshot;*/

    public Description() {
        descriptionMutable = new MutableLiveData<>();
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

   /* public LiveData<Description> getDescription(String path) {

        mercadoServiceApi = RetrofitInstance.getMercadoApiService();

        Call<Description> call = mercadoServiceApi.getDescripcion(path);

        call.enqueue(new Callback<Description>() {
            @Override
            public void onResponse(Call<Description> call, Response<Description> response) {
                Description descriptionResult = response.body();

                descriptionMutable.setValue(descriptionResult);

            }

            @Override
            public void onFailure(Call<Description> call, Throwable t) {
                String message = t.getMessage();
                System.out.println("ha ocurrido un error" + message);
                t.printStackTrace();

            }
        });
        return descriptionMutable;

    }*/
}



