package com.example.mercadoabierto2_mvvm.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mercadoabierto2_mvvm.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageFragment extends Fragment {

    private static final String ARG_URL = "url";
    private String imageUrl;

    @BindView(R.id.imageViewDetails)
    ImageView imageViewDetails;


    public ImageFragment() {
        // Required empty public constructor
    }


    public static ImageFragment newInstance(String param1) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_URL, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageUrl = getArguments().getString(ARG_URL);
            imageUrl = imageUrl.substring(4);
            imageUrl = "https" + imageUrl;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        ButterKnife.bind(this, view);

        Glide.with(view).load(imageUrl).into(imageViewDetails);

        return view;
    }
}