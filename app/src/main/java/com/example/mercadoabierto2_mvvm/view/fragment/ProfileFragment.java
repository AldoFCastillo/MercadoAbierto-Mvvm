package com.example.mercadoabierto2_mvvm.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mercadoabierto2_mvvm.R;
import com.example.mercadoabierto2_mvvm.pojo.pojo.User;
import com.example.mercadoabierto2_mvvm.viewModel.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private LoginFragment loginFragment = new LoginFragment();
    private UserViewModel userViewModel;


    @BindView(R.id.textViewNombreProfile)
    TextView textViewNombreProfile;
    @BindView(R.id.textViewApellidoProfile)
    TextView textViewApellidoProfile;
    @BindView(R.id.textViewEdadProfile)
    TextView textViewEdadProfile;
    @BindView(R.id.textViewMailProfile)
    TextView textViewMailProfile;
    @BindView(R.id.textViewUserNameProfile)
    TextView textViewUserNameProfile;
    @BindView(R.id.constraintProfileFavsFragment)
    ConstraintLayout constraintProfileFavsFragment;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ButterKnife.bind(this, view);

        setTextsAndFavs();

        setFavoritos();


        return view;
    }


    private void setTextsAndFavs() {

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getProfile().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                textViewMailProfile.setText(user.getMail());
                textViewEdadProfile.setText(user.getEdad());
                textViewApellidoProfile.setText(user.getApellido());
                textViewNombreProfile.setText(user.getNombre());
                textViewUserNameProfile.setText(user.getUserName());
            }
        });

    }


    private void setFavoritos() {
        FavsFragment favsFragment = new FavsFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction unaTransaccion = fragmentManager.beginTransaction();
        unaTransaccion.replace(R.id.constraintProfileFavsFragment, favsFragment);
        unaTransaccion.commit();

    }


}
