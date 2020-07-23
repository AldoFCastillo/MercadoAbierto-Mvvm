package com.example.mercadoabierto2_mvvm.viewModel;


import androidx.lifecycle.LiveData;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.example.mercadoabierto2_mvvm.DataSource.DetailsDataSource;
import com.example.mercadoabierto2_mvvm.DataSource.ProductDataSource;
import com.example.mercadoabierto2_mvvm.DataSource.ProductDataSourceFactory;

import com.example.mercadoabierto2_mvvm.model.pojo.Comment;
import com.example.mercadoabierto2_mvvm.model.pojo.Description;
import com.example.mercadoabierto2_mvvm.model.pojo.Product;
import com.example.mercadoabierto2_mvvm.model.pojo.ProductDetails;

import java.util.List;


public class ProductViewModel extends ViewModel {


    public MutableLiveData<String> searchQuery= new MutableLiveData<>();
    private LiveData<PagedList<Product>> productPagedList;
    LiveData<PageKeyedDataSource<Integer, Product>> liveDataSource;
    DetailsDataSource detailsDataSource = new DetailsDataSource();

    public ProductViewModel() {

        ProductDataSourceFactory productDataSourceFactory = new ProductDataSourceFactory();
        liveDataSource = productDataSourceFactory.getProductLiveDataSource();

        PagedList.Config config =
                (new PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setPageSize(ProductDataSource.LIMIT)
                        .build());

        productPagedList = Transformations.switchMap(searchQuery, input -> {
            if (input == null || input.equals("") || input.equals("%%")) {
                return new LivePagedListBuilder<>(productDataSourceFactory,config).build();
            }else{
                productDataSourceFactory.setQuery(searchQuery.getValue());
                return new LivePagedListBuilder<>(productDataSourceFactory,config).build();
            }
        });
    }


    public LiveData<PagedList<Product>> getProductPagedList() {
        return productPagedList;
    }

    public LiveData<ProductDetails> getProductDetails(String path){
        return detailsDataSource.getDetails(path);
    }

    public LiveData<Description> getDescription (String path){
        return detailsDataSource.getDescription(path);
    }

    public LiveData<List<Comment>> getComments(String id){
        return detailsDataSource.getComments(id);
    }

    public LiveData<Boolean> addComment(String id, Product product){
        return detailsDataSource.addComment(id, product);
    }




}
