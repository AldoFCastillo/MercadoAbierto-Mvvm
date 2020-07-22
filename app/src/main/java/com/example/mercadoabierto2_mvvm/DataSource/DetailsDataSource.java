package com.example.mercadoabierto2_mvvm.DataSource;

import android.text.BoringLayout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mercadoabierto2_mvvm.pojo.pojo.Comment;
import com.example.mercadoabierto2_mvvm.pojo.pojo.Description;
import com.example.mercadoabierto2_mvvm.pojo.pojo.Product;
import com.example.mercadoabierto2_mvvm.pojo.pojo.ProductDetails;

import com.example.mercadoabierto2_mvvm.service.RetrofitInstance;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsDataSource {
    MutableLiveData<ProductDetails> mutableProductDetails;
    MutableLiveData<Description> mutableDescription;
    List<Comment> commentList = new ArrayList<>();

    public DetailsDataSource() {
    }

    public LiveData<ProductDetails> getDetails(String path) {

        mutableProductDetails = new MutableLiveData<>();

        RetrofitInstance.getInstance()
                .getMercadoApiService()
                .getProduct(path)
                .enqueue(new Callback<ProductDetails>() {
                    @Override
                    public void onResponse(Call<ProductDetails> call, Response<ProductDetails> response) {
                        ProductDetails productResult = response.body();
                        mutableProductDetails.setValue(productResult);
                    }

                    @Override
                    public void onFailure(Call<ProductDetails> call, Throwable t) {
                        String message = t.getMessage();
                        System.out.println("ha ocurrido un error" + message);
                        t.printStackTrace();
                    }

                });

        return mutableProductDetails;

    }

    public LiveData<Description> getDescription(String path) {

        mutableDescription = new MutableLiveData<>();

        RetrofitInstance.getInstance()
                .getMercadoApiService()
                .getDescription(path)
                .enqueue(new Callback<Description>() {
            @Override
            public void onResponse(Call<Description> call, Response<Description> response) {
                Description descriptionResult = response.body();
                mutableDescription.setValue(descriptionResult);
            }

            @Override
            public void onFailure(Call<Description> call, Throwable t) {
                String message = t.getMessage();
                System.out.println("ha ocurrido un error" + message);
                t.printStackTrace();
            }
        });
        return mutableDescription;
    }

    public LiveData<List<Comment>> getComments(String id){
        MutableLiveData<List<Comment>> mutableList = new MutableLiveData<>();
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("productos").document(id);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Product product = documentSnapshot.toObject(Product.class);
                if (product !=null) {
                    commentList = product.getCommentList();
                    mutableList.setValue(commentList);
                }else mutableList.setValue(commentList);
            }
        });
        return mutableList;

    }

    public LiveData<Boolean> addComment (String id, Product product){
        MutableLiveData<Boolean> ok = new MutableLiveData<>();
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        db.collection("productos").document(id).set(product).addOnSuccessListener(aVoid -> {
            ok.setValue(true);
        }).addOnFailureListener(e -> ok.setValue(false));
        return ok;
    }
}
