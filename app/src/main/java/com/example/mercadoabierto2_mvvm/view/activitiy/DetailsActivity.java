package com.example.mercadoabierto2_mvvm.view.activitiy;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.mercadoabierto2_mvvm.R;
import com.example.mercadoabierto2_mvvm.pojo.pojo.Picture;
import com.example.mercadoabierto2_mvvm.pojo.pojo.Product;
import com.example.mercadoabierto2_mvvm.pojo.pojo.ProductDetails;
import com.example.mercadoabierto2_mvvm.view.adapter.ViewPagerAdapter;
import com.example.mercadoabierto2_mvvm.view.fragment.DetailsFragment;
import com.example.mercadoabierto2_mvvm.view.fragment.ImageFragment;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements DetailsFragment.locationListener {

    public static final String KEY_POSITION = "position";
    public static final String KEY_PRODUCTS = "products";
    public static final String KEY_DETAILS = "details";
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<Fragment> imageFragmentList = new ArrayList<>();
    private List<Product> productList = new ArrayList<>();
    private List<ProductDetails> detailsList = new ArrayList<>();
    private Integer adapterPosition;


    @BindView(R.id.viewPagerDetails)
    ViewPager viewPager;
    @BindView(R.id.toolBarDetails)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        adapterPosition = bundle.getInt(KEY_POSITION);

        if (bundle.getSerializable(KEY_PRODUCTS) != null) {
            HashMap hashMap = (HashMap) bundle.getSerializable(KEY_PRODUCTS);
            productList = (List<Product>) hashMap.get(KEY_PRODUCTS);
            setProductFragmentList(productList, adapterPosition);
        } else {
            HashMap hashMap = (HashMap) bundle.getSerializable(KEY_DETAILS);
            detailsList = (List<ProductDetails>) hashMap.get(KEY_DETAILS);
            setDetailsFragmentList(detailsList, adapterPosition);
        }

    }


    private void setProductFragmentList(List<Product> productList, Integer adapterPosition) {
        for (Product product : productList) {
            DetailsFragment detailsFragment = DetailsFragment.getInstance(product);
            fragmentList.add(detailsFragment);
        }
        setViewPager(adapterPosition, fragmentList);
    }

    private void setDetailsFragmentList(List<ProductDetails> detailsList, Integer adapterPosition) {
        for (ProductDetails details : detailsList) {
            DetailsFragment detailsFragment = DetailsFragment.getInstance(details);
            fragmentList.add(detailsFragment);
        }
        setViewPager(adapterPosition, fragmentList);
    }

    private void setViewPager(Integer adapterPosition, List<Fragment> fragmentList) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(adapterPosition);
    }



    @Override
    public void sendLocation(Double lat, Double lon) {
        Intent intent = new Intent(DetailsActivity.this, MapActivity.class);
        Bundle mapBundle = new Bundle();
        mapBundle.putDouble(MapActivity.LATITUDE, lat);
        mapBundle.putDouble(MapActivity.LONGITUDE, lon);
        intent.putExtras(mapBundle);
        startActivity(intent);
    }
}