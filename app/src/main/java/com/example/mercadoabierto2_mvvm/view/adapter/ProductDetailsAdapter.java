package com.example.mercadoabierto2_mvvm.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import com.example.mercadoabierto2_mvvm.R;
import com.example.mercadoabierto2_mvvm.model.pojo.ProductDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetailsAdapter extends RecyclerView.Adapter {



    private List<ProductDetails> favList;
    private FavAdapterListener favAdapterListener;



    public ProductDetailsAdapter(List<ProductDetails> favList, FavAdapterListener productoFavAdapterListener) {
        this.favList = favList;
        this.favAdapterListener = productoFavAdapterListener;
    }

    @NonNull
    @Override
    public ProductDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.product_cell, parent, false);
        return new ProductDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductDetails productDetails = favList.get(position);
        ProductDetailsViewHolder productDetailsViewHolder = (ProductDetailsViewHolder) holder;
        productDetailsViewHolder.bind(productDetails);
    }

    @Override
    public int getItemCount() {
        return favList.size();
    }


    public class ProductDetailsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageViewCell)
        ImageView imageViewCell;
        @BindView(R.id.textViewTitleCell)
        TextView textViewTitle;
        @BindView(R.id.textViewPriceCell)
        TextView textViewPrice;

        private FirebaseAuth mAuth;

        public ProductDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            // rowView = itemView;

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {

                int adapterPosition = getAdapterPosition();
                favAdapterListener.favsAdapterListener(adapterPosition, favList);

            });

        }



        public void bind(ProductDetails producto) {

            mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            String id = currentUser.getUid();

            textViewTitle.setText(producto.getTitle());
            String precio = "$ " + producto.getPrice();
            textViewPrice.setText(precio);
            String url = producto.getPictures().get(0).getSecureUrl();
            Glide.with(itemView).load(url).into(imageViewCell);


        }
    }

    public interface FavAdapterListener {
        void favsAdapterListener(Integer adapterPosition, List<ProductDetails> favList);
    }



}
