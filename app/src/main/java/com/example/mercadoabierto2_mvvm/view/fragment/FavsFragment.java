package com.example.mercadoabierto2_mvvm.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mercadoabierto2_mvvm.R;

import com.example.mercadoabierto2_mvvm.model.pojo.ProductDetails;
import com.example.mercadoabierto2_mvvm.model.pojo.User;
import com.example.mercadoabierto2_mvvm.view.adapter.ProductDetailsAdapter;
import com.example.mercadoabierto2_mvvm.viewModel.ProductViewModel;
import com.example.mercadoabierto2_mvvm.viewModel.UserViewModel;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavsFragment extends Fragment implements ProductDetailsAdapter.FavAdapterListener {

    @BindView(R.id.recyclerFavs)
    RecyclerView recyclerFavs;

    private List<ProductDetails> productList = new ArrayList<>();
    private UserViewModel userViewModel;
    private ProductViewModel productViewModel;
    private Integer i = 0;
    private ProductDetailsAdapter productDetailsAdapter;
    private Listener listener;


    public FavsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favs, container, false);

        ButterKnife.bind(this, view);
        initRecycler();
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        userViewModel.getProfile().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                List<String> favList = user.getFavoritos();
                for (String id : favList) {
                    productViewModel.getProductDetails(id).observe(FavsFragment.this, new Observer<ProductDetails>() {
                        @Override
                        public void onChanged(ProductDetails productDetails) {
                            i++;
                            productList.add(productDetails);
                            if (i.equals(favList.size())) {
                                productDetailsAdapter = new ProductDetailsAdapter(productList, FavsFragment.this);
                                recyclerFavs.setAdapter(productDetailsAdapter);
                                //TODO revisar

                            }
                        }
                    });

                }

            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (FavsFragment.Listener) context;

    }

    private void initRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerFavs.setLayoutManager(layoutManager);
        recyclerFavs.setHasFixedSize(true);
        recyclerFavs.setItemViewCacheSize(10);

    }




    @Override
    public void favsAdapterListener(Integer adapterPosition, List<ProductDetails> favList) {
        listener.favsFragmentListener(adapterPosition, favList);
    }

    public interface Listener {
        void favsFragmentListener(Integer adapterPosition, List<ProductDetails> favList);
    }
}
