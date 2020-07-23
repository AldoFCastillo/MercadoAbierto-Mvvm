package com.example.mercadoabierto2_mvvm.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Geolocation implements Serializable {


    @SerializedName("latitude")
    private Double latitude;
    @SerializedName("longitude")
    private Double longitude;

    public Geolocation() {
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
