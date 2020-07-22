package com.example.mercadoabierto2_mvvm.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.mercadoabierto2_mvvm.R;
import com.example.mercadoabierto2_mvvm.pojo.pojo.Product;

import com.example.mercadoabierto2_mvvm.view.adapter.ProductAdapter;
import com.example.mercadoabierto2_mvvm.viewModel.ProductViewModel;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MercadoFragment extends Fragment implements ProductAdapter.AdapterListener {


    private Listener listener;
    private List<Product> productList = new ArrayList<>();
    private ProductViewModel productViewModel;
    private ProductAdapter productAdapter;



    @BindView(R.id.recyclerMercadoFragment)
    RecyclerView recyclerView;
    @BindView(R.id.textViewSearch)
    TextView textViewSearch;
    @BindView(R.id.lottieCargaMercado)
    LottieAnimationView lottieCargaMercado;
    @BindView(R.id.constraintMercado)
    ConstraintLayout constraintMercado;


    public MercadoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (Listener) context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mercado, container, false);

        ButterKnife.bind(this, view);

        constraintMercado.setVisibility(View.GONE);
        lottieCargaMercado.setVisibility(View.VISIBLE);

        productAdapter = new ProductAdapter(getContext(), MercadoFragment.this);
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        initRecycler();
        goHome();


        return view;
    }

    public void searchProducts(String query) {
        constraintMercado.setVisibility(View.GONE);
        lottieCargaMercado.setVisibility(View.VISIBLE);

        productViewModel.searchQuery.setValue(query);
        productViewModel.getProductPagedList().observe(this, new Observer<PagedList<Product>>() {
            @Override
            public void onChanged(PagedList<Product> products) {
                productList= products;
                productAdapter.submitList(products);
                productAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(productAdapter);
                textViewSearch.setText(query);
                int timeout = 800;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lottieCargaMercado.setVisibility(View.GONE);
                        constraintMercado.setVisibility(View.VISIBLE);
                    }
                }, timeout);

            }
        });


    }

    public void goHome() {
        searchProducts("oferta");
    }


    private void initRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(10);

    }

    @Override
    public void selection(Integer adapterPosition) {
        listener.mercadoFragmentListener(adapterPosition, productList);
    }


    public interface Listener {
        void mercadoFragmentListener(Integer adapterPosition, List<Product> productList);
    }
}