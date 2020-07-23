package com.example.mercadoabierto2_mvvm.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mercadoabierto2_mvvm.R;
import com.example.mercadoabierto2_mvvm.model.pojo.Product;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductAdapter extends PagedListAdapter<Product, ProductAdapter.ProductViewHolder> {

    private Context context;
    private AdapterListener listener;

    public ProductAdapter(Context context, AdapterListener listener) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_cell, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = getItem(position);
        ProductViewHolder productViewHolder = (ProductViewHolder) holder;
        productViewHolder.bind(product);

    }


    private static DiffUtil.ItemCallback<Product> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Product>() {
                @Override
                public boolean areItemsTheSame(Product oldItem, Product newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(Product oldItem, Product newItem) {
                    return oldItem.equals(newItem);
                }
            };


    public class ProductViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageViewCell)
        ImageView imageViewCell;
        @BindView(R.id.textViewTitleCell)
        TextView textViewTitle;
        @BindView(R.id.textViewPriceCell)
        TextView textViewPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Product product = getItem(getAdapterPosition());
                    listener.selection(getAdapterPosition());
                }
            });
        }

        public void bind(Product product) {
            textViewTitle.setText(product.getTitle());
            String stringPrice = product.getPrice();
            NumberFormat formatoImporte = NumberFormat.getCurrencyInstance();
            Double c = Double.parseDouble(stringPrice);
            String price = formatoImporte.format(c);
            if (price.endsWith(".00")) {
                int centsIndex = price.lastIndexOf(".00");
                if (centsIndex != -1) {
                    price ="$"+ price.substring(1, centsIndex);
                }
            }

            textViewPrice.setText(price);
            String url = product.getThumbnail();
            String https = "https";
            url = url.substring(4);
            url = https + url;
            Glide.with(itemView).load(url).into(imageViewCell);
        }
    }

    public interface AdapterListener {
        void selection(Integer adapterPosition);
    }
}
