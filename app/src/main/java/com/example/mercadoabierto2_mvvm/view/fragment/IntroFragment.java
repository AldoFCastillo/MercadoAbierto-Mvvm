package com.example.mercadoabierto2_mvvm.view.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mercadoabierto2_mvvm.R;
import com.example.mercadoabierto2_mvvm.view.activitiy.MainActivity;
import com.example.mercadoabierto2_mvvm.view.fragment.MercadoFragment;

public class IntroFragment extends Fragment {

    private static int timeout = 3500;
    private listener listener;


    public IntroFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro, container, false);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.listenerIntro();
            }
        }, timeout);


        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (listener) context;

    }

    public interface listener {
        void listenerIntro();
    }


}