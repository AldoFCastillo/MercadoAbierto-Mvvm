package com.example.mercadoabierto2_mvvm.model.pojo;

import com.example.mercadoabierto2_mvvm.service.MercadoServiceApi;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ProductDetails implements Serializable {

    private MercadoServiceApi mercadoServiceApi;


    @SerializedName("id")
    private String id;
    @SerializedName("site_id")
    private String siteId;
    @SerializedName("title")
    private String title;
    /*@SerializedName("subtitle")
    private Object subtitle;*/
    @SerializedName("seller_id")
    private String sellerId;
    @SerializedName("category_id")
    private String categoryId;
    /*@SerializedName("official_store_id")
    private Object officialStoreId;*/
    @SerializedName("price")
    private String price;
    @SerializedName("base_price")
    private String basePrice;
    @SerializedName("original_price")
    private String originalPrice;
    @SerializedName("currency_id")
    private String currencyId;
    @SerializedName("initial_quantity")
    private String initialQuantity;
    @SerializedName("available_quantity")
    private String availableQuantity;
    @SerializedName("sold_quantity")
    private String soldQuantity;
    /*@SerializedName("sale_terms")
    private List<Object> saleTerms = null;*/
    @SerializedName("buying_mode")
    private String buyingMode;
    @SerializedName("listing_type_id")
    private String listingTypeId;
    @SerializedName("start_time")
    private String startTime;
    @SerializedName("stop_time")
    private String stopTime;
    @SerializedName("condition")
    private String condition;
    @SerializedName("permalink")
    private String permalink;
    @SerializedName("thumbnail")
    private String thumbnail;
    @SerializedName("secure_thumbnail")
    private String secureThumbnail;
    @SerializedName("pictures")
    private List<Picture> pictures = null;
    /* @SerializedName("video_id")
     private Object videoId;
    @SerializedName("descriptions")
    private List<DescriptionId> descriptions = null;*/
    @SerializedName("accepts_mercadopago")
    private Boolean acceptsMercadopago;
    @SerializedName("non_mercado_pago_payment_methods")
    private List<Object> nonMercadoPagoPaymentMethods = null;
    /*@SerializedName("shipping")
    private Shipping shipping;*/
    @SerializedName("international_delivery_mode")
    private String internationalDeliveryMode;
    @SerializedName("geolocation")
    private Geolocation geolocation;
    /* @SerializedName("seller_address")
     private SellerAddress sellerAddress;
     @SerializedName("seller_contact")
     private Object sellerContact;
     @SerializedName("location")
     private Location location;
     @SerializedName("coverage_areas")
     private List<Object> coverageAreas = null;
     @SerializedName("attributes")
     private List<Object> attributes = null;
     @SerializedName("warnings")
     private List<Object> warnings = null;*/
    @SerializedName("listing_source")
    private String listingSource;
    @SerializedName("variations")
    private List<Object> variations = null;
    @SerializedName("status")
    private String status;
    /*@SerializedName("sub_status")
    private List<Object> subStatus = null;
    @SerializedName("tags")
    private List<Object> tags = null;
    @SerializedName("warranty")
    private Object warranty;
    @SerializedName("catalog_product_id")
    private Object catalogProductId;*/
    @SerializedName("domain_id")
    private String domainId;
    @SerializedName("parent_item_id")
    private String parentItemId;
    /*@SerializedName("differential_pricing")
    private Object differentialPricing;
    @SerializedName("deal_ids")
    private List<Object> dealIds = null;
    @SerializedName("automatic_relist")
    private Boolean automaticRelist;*/
    @SerializedName("date_created")
    private String dateCreated;
    @SerializedName("last_updated")
    private String lastUpdated;
    @SerializedName("health")
    private String health;
    @SerializedName("catalog_listing")
    private Boolean catalogListing;

    public ProductDetails() {

    }

    public Geolocation getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(Geolocation geolocation) {
        this.geolocation = geolocation;
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

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(String basePrice) {
        this.basePrice = basePrice;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getInitialQuantity() {
        return initialQuantity;
    }

    public void setInitialQuantity(String initialQuantity) {
        this.initialQuantity = initialQuantity;
    }

    public String getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(String availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public String getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(String soldQuantity) {
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
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

    public String getSecureThumbnail() {
        return secureThumbnail;
    }

    public void setSecureThumbnail(String secureThumbnail) {
        this.secureThumbnail = secureThumbnail;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }


    public Boolean getAcceptsMercadopago() {
        return acceptsMercadopago;
    }

    public void setAcceptsMercadopago(Boolean acceptsMercadopago) {
        this.acceptsMercadopago = acceptsMercadopago;
    }

    public List<Object> getNonMercadoPagoPaymentMethods() {
        return nonMercadoPagoPaymentMethods;
    }

    public void setNonMercadoPagoPaymentMethods(List<Object> nonMercadoPagoPaymentMethods) {
        this.nonMercadoPagoPaymentMethods = nonMercadoPagoPaymentMethods;
    }

    public String getInternationalDeliveryMode() {
        return internationalDeliveryMode;
    }

    public void setInternationalDeliveryMode(String internationalDeliveryMode) {
        this.internationalDeliveryMode = internationalDeliveryMode;
    }

    public String getListingSource() {
        return listingSource;
    }

    public void setListingSource(String listingSource) {
        this.listingSource = listingSource;
    }

    public List<Object> getVariations() {
        return variations;
    }

    public void setVariations(List<Object> variations) {
        this.variations = variations;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    public String getParentItemId() {
        return parentItemId;
    }

    public void setParentItemId(String parentItemId) {
        this.parentItemId = parentItemId;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public Boolean getCatalogListing() {
        return catalogListing;
    }

    public void setCatalogListing(Boolean catalogListing) {
        this.catalogListing = catalogListing;
    }




}

