package com.example.mercadoabierto2_mvvm.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.example.mercadoabierto2_mvvm.R;

import com.example.mercadoabierto2_mvvm.model.pojo.Comment;
import com.example.mercadoabierto2_mvvm.model.pojo.Description;
import com.example.mercadoabierto2_mvvm.model.pojo.Picture;
import com.example.mercadoabierto2_mvvm.model.pojo.Product;
import com.example.mercadoabierto2_mvvm.model.pojo.ProductDetails;
import com.example.mercadoabierto2_mvvm.model.pojo.User;
import com.example.mercadoabierto2_mvvm.view.adapter.CommentsAdapter;
import com.example.mercadoabierto2_mvvm.view.adapter.ViewPagerAdapter;
import com.example.mercadoabierto2_mvvm.viewModel.ProductViewModel;
import com.example.mercadoabierto2_mvvm.viewModel.UserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailsFragment extends Fragment {

    public static final String KEY_PRODUCT = "product";
    public static final String KEY_DETAILS = "details";
    private Product product;
    private ProductDetails productDetails;
    private String id;
    private String userName;


    private ProductViewModel productViewModel;
    private UserViewModel userViewModel;
    private CommentsAdapter commentsAdapter;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private locationListener locationListener;
    private PagerAdapter pagerAdapter;


    @BindView(R.id.lottieCarga)
    LottieAnimationView lottieCarga;
    @BindView(R.id.imageViewPager)
    ViewPager imageViewPager;
    @BindView(R.id.textViewTituloDetails)
    TextView textViewTitleDetails;
    @BindView(R.id.textViewDescripcionDetails)
    TextView textViewDescriptionDetails;
    @BindView(R.id.textViewPrecioDetails)
    TextView textViewPriceDetails;
    @BindView(R.id.buttonMap)
    FloatingActionButton buttonMap;
    @BindView(R.id.constraintDetails)
    ConstraintLayout constraintDetails;

    @BindView(R.id.textViewPreguntaComentarios)
    TextView textViewPreguntaComentarios;
    @BindView(R.id.editTextComentarios)
    EditText editTextComentarios;
    @BindView(R.id.buttonEnviarComentarios)
    TextView buttonEnviarComentarios;
    @BindView(R.id.imageViewLikeDetails)
    ImageView imageViewLikeDetails;
    @BindView(R.id.recyclerComentarios)
    RecyclerView recyclerComentarios;


    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment getInstance(Product product) {
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_PRODUCT, product);
        detailsFragment.setArguments(bundle);
        return detailsFragment;
    }

    public static DetailsFragment getInstance(ProductDetails productDetails) {
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_DETAILS, productDetails);
        detailsFragment.setArguments(bundle);
        return detailsFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, view);

        constraintDetails.setVisibility(View.GONE);
        lottieCarga.setVisibility(View.VISIBLE);
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);


        Bundle bundle = getArguments();
        if (bundle.getSerializable(KEY_PRODUCT) != null) {
            product = (Product) bundle.getSerializable(KEY_PRODUCT);
            id = product.getId();
            getDetails();
        } else {
            productDetails = (ProductDetails) bundle.getSerializable(KEY_DETAILS);
            id = productDetails.getId();
            bindDetails(productDetails);
        }

        getComments();


        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.locationListener = (locationListener) context;
    }

    private void getComments() {

        productViewModel.getComments(id).observe(this, new Observer<List<Comment>>() {
            @Override
            public void onChanged(List<Comment> commentList) {
                commentsAdapter = new CommentsAdapter(commentList);
                initCommentRecycler(commentsAdapter);
                leaveComment(commentList);
            }
        });
        setFavHeartClick();
    }

    private void initCommentRecycler(CommentsAdapter adapter) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerComentarios.setLayoutManager(layoutManager);
        recyclerComentarios.setAdapter(adapter);
        recyclerComentarios.setHasFixedSize(true);
        recyclerComentarios.setItemViewCacheSize(10);
    }

    private void leaveComment(List<Comment> commentList) {
        buttonEnviarComentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentText = editTextComentarios.getText().toString();
                if (commentText.isEmpty()) {
                    Toast.makeText(getContext(), "El comentario esta vacio!", Toast.LENGTH_LONG).show();
                } else {
                    Comment comment = new Comment();
                    comment.setUsername(userName);
                    comment.setComment(commentText);
                    commentList.add(comment);
                    Product product = new Product();
                    product.setCommentList(commentList);
                    productViewModel.addComment(id, product).observe(DetailsFragment.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            if (aBoolean) {
                                Toast.makeText(getContext(), "Comentario agregado!", Toast.LENGTH_LONG).show();
                                initCommentRecycler(new CommentsAdapter(commentList));
                                textViewPreguntaComentarios.setVisibility(View.VISIBLE);
                                editTextComentarios.setVisibility(View.GONE);
                                buttonEnviarComentarios.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(getContext(), "Se produjo un error", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });

    }

    private void getDetails() {
        productViewModel.getProductDetails(id).observe(this, new Observer<ProductDetails>() {
            @Override
            public void onChanged(ProductDetails result) {
                bindDetails(result);
            }
        });


    }

    private void bindDetails(ProductDetails result) {

        this.imageViewPager.setAdapter(getImageAdapter(result));
        this.imageViewPager.setCurrentItem(0);

        textViewTitleDetails.setText(result.getTitle());
        String price = getPriceFormat(result.getPrice());
        textViewPriceDetails.setText(price);
        setButtonMap(result);
        checkFav(id);

        productViewModel.getDescription(id).observe(this, new Observer<Description>() {
            @Override
            public void onChanged(Description description) {
                if (description != null) {
                    textViewDescriptionDetails.setText(description.getPlainText());
                    lottieCarga.setVisibility(View.GONE);
                    constraintDetails.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private String getPriceFormat(String stringPrice) {
        NumberFormat formatoImporte = NumberFormat.getCurrencyInstance();
        Double c = Double.parseDouble(stringPrice);
        String price = formatoImporte.format(c);
        if (price.endsWith(".00")) {
            int centsIndex = price.lastIndexOf(".00");
            if (centsIndex != -1) {
                price = "$" + price.substring(1, centsIndex);
            }
        }
        return price;
    }

    private ViewPagerAdapter getImageAdapter(ProductDetails details) {
        List<Fragment> imageFragmentList = new ArrayList<>();
        List<Picture> pictureList = details.getPictures();
        int i = 0;
        for (Picture picture : pictureList) {
            if (i <= 10) {
                ImageFragment imageFragment = ImageFragment.newInstance(picture.getUrl());
                imageFragmentList.add(imageFragment);
                i++;
            }
        }
        return new ViewPagerAdapter(getChildFragmentManager(), imageFragmentList);

    }

    public void setButtonMap(ProductDetails result) {
        buttonMap.setOnClickListener(v -> {
            Double lat = result.getGeolocation().getLatitude();
            Double lon = result.getGeolocation().getLongitude();
            locationListener.sendLocation(lat, lon);
        });
    }

    public LiveData<Boolean> checkFav(String id) {
        MutableLiveData<Boolean> mutableInFavs = new MutableLiveData<>();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            setQuestionClick(true);
            userViewModel.getUser().observe(this, new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    List<String> favList = user.getFavoritos();
                    userName = user.getUserName();
                    if (favList.contains(id)) {
                        setFavHeart(true);
                        mutableInFavs.setValue(true);
                    } else {
                        setFavHeart(false);
                        mutableInFavs.setValue(false);
                    }
                }
            });
        } else {
            setQuestionClick(false);
        }
        return mutableInFavs;
    }

    private void setQuestionClick(Boolean logged) {
        if (logged) {
            textViewPreguntaComentarios.setOnClickListener(v -> {
                textViewPreguntaComentarios.setVisibility(View.GONE);
                editTextComentarios.setVisibility(View.VISIBLE);
                buttonEnviarComentarios.setVisibility(View.VISIBLE);
                editTextComentarios.requestFocus();
            });
        } else
            Toast.makeText(getContext(), "Debes iniciar sesion primero", Toast.LENGTH_SHORT).show();
    }

    public void setFavHeart(Boolean fav) {
        if (fav) {
            imageViewLikeDetails.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else imageViewLikeDetails.setImageResource(R.drawable.unlike24dp);

    }

    private void setFavHeartClick() {
        imageViewLikeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                currentUser = mAuth.getCurrentUser();
                if (currentUser == null) {
                    Snackbar.make(constraintDetails, "Debes iniciar sesion primero", Snackbar.LENGTH_SHORT).show();
                } else {
                    checkFav(id).observe(DetailsFragment.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            if (aBoolean) {
                                setFavHeart(false);
                                userViewModel.removeFav(id).observe(DetailsFragment.this, new Observer<Boolean>() {
                                    @Override
                                    public void onChanged(Boolean aBoolean) {
                                        if (aBoolean) {
                                            Snackbar.make(constraintDetails, "Eliminado de Favoritos", Snackbar.LENGTH_SHORT).show();
                                        } else
                                            Snackbar.make(constraintDetails, "Ocurrio un error", Snackbar.LENGTH_SHORT).show();
                                    }
                                });

                            } else {
                                setFavHeart(true);
                                userViewModel.addToFavs(id).observe(DetailsFragment.this, new Observer<Boolean>() {
                                    @Override
                                    public void onChanged(Boolean aBoolean) {
                                        if (aBoolean) {
                                            Snackbar.make(constraintDetails, "Agregado a Favoritos", Snackbar.LENGTH_LONG).show();
                                        } else
                                            Snackbar.make(constraintDetails, "Debes loguearte primero", Snackbar.LENGTH_LONG).show();
                                    }

                                });
                            }
                        }

                    });

                }
            }
        });


    }


    public interface locationListener {
        void sendLocation(Double lat, Double lon);
    }


}