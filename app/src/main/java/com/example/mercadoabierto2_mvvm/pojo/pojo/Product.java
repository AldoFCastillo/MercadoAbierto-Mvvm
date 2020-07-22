package com.example.mercadoabierto2_mvvm.pojo.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Product implements Serializable {

    private List<Comment> commentList = new ArrayList<>();

    @SerializedName("id")
    private String id;
    @SerializedName("site_id")
    private String siteId;
    @SerializedName("title")
    private String title;
    /*@SerializedName("seller")
    private Seller seller;*/
    @SerializedName("price")
    private String price;
    @SerializedName("currency_id")
    private String currencyId;
    @SerializedName("available_quantity")
    private Integer availableQuantity;
    @SerializedName("sold_quantity")
    private Integer soldQuantity;
    @SerializedName("buying_mode")
    private String buyingMode;
    @SerializedName("listing_type_id")
    private String listingTypeId;
    @SerializedName("stop_time")
    private String stopTime;
    @SerializedName("condition")
    private String condition;
    @SerializedName("permalink")
    private String permalink;
    @SerializedName("thumbnail")
    private String thumbnail;
    @SerializedName("accepts_mercadopago")
    private Boolean acceptsMercadopago;
    /* @SerializedName("installments")
     private Installments installments;
     @SerializedName("address")
     private Address address;
     @SerializedName("shipping")
     private Shipping shipping;
     @SerializedName("seller_address")
     private SellerAddress sellerAddress;
     @SerializedName("attributes")
     private List<Attribute> attributes = null;
    @SerializedName("original_price")
    private Object originalPrice;*/
    @SerializedName("category_id")
    private String categoryId;
    /*@SerializedName("official_store_id")
    private Object officialStoreId;
    @SerializedName("catalog_product_id")
    private Object catalogProductId;*/
    @SerializedName("tags")
    private List<String> tags = null;

    public Product() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public Integer getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(Integer soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public String getBuyingMode() {
        return buyingMode;
    }

    public void setBuyingMode(String buyingMode) {
        this.buyingMode = buyingMode;
    }

    public String getListingTypeId() {
        return listingTypeId;
    }

    public void setListingTypeId(String listingTypeId) {
        this.listingTypeId = listingTypeId;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Boolean getAcceptsMercadopago() {
        return acceptsMercadopago;
    }

    public void setAcceptsMercadopago(Boolean acceptsMercadopago) {
        this.acceptsMercadopago = acceptsMercadopago;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }


    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
