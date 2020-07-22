package com.example.mercadoabierto2_mvvm.DataSource;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.example.mercadoabierto2_mvvm.pojo.pojo.Product;

public class ProductDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer, Product>> productLiveDataSource = new MutableLiveData<>();
    private String query;


    public ProductDataSourceFactory(){

    }


    @Override
    public DataSource create() {
        ProductDataSource productDataSource = new ProductDataSource();
        productDataSource.query=query;
        productLiveDataSource.postValue(productDataSource);
        return productDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Product>> getProductLiveDataSource() {

        return productLiveDataSource;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
