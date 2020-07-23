package com.example.mercadoabierto2_mvvm.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mercadoabierto2_mvvm.R;

import com.example.mercadoabierto2_mvvm.model.pojo.User;
import com.example.mercadoabierto2_mvvm.view.activitiy.MainActivity;
import com.example.mercadoabierto2_mvvm.viewModel.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private FirebaseAuth mAuth;
    private String userId;
    private UserViewModel userViewModel;


    @BindView(R.id.editTextEmailFragmentLogin)
    EditText editTextEmailFragmentLogin;
    @BindView(R.id.editTextPasswordFragmentLogin)
    EditText editTextPasswordFragmentLogin;
    @BindView(R.id.buttonIngresarFragmentLogin)
    Button buttonIngresarFragmentLogin;
    @BindView(R.id.buttonRegistrarFragmentLogin)
    TextView buttonRegistrarFragmentLogin;
    @BindView(R.id.linearLayoutIniciarSesionFragmentLogin)
    LinearLayout linearLayoutIniciarSesionFragmentLogin;
    @BindView(R.id.editTextUserNameFragmentLogin)
    EditText editTextUserNameFragmentLogin;
    @BindView(R.id.editTextNombreFragmentLogin)
    EditText editTextNombreFragmentLogin;
    @BindView(R.id.editTextApellidoFragmentLogin)
    EditText editTextApellidoFragmentLogin;
    @BindView(R.id.editTextEdadFragmentLogin)
    EditText editTextEdadFragmentLogin;
    @BindView(R.id.buttonEnviarFragmentLogin)
    Button buttonEnviarFragmentLogin;
    @BindView(R.id.cardViewRegistroLogin)
    CardView cardViewRegistroLogin;
    @BindView(R.id.textViewNoLogueadoLogin)
    TextView textViewNoLogueadoLogin;



    public LoginFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);

        // mStorageRef = FirebaseStorage.getInstance().getReference();

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        setButtons();


        return view;
    }


    private void setButtons() {

        buttonIngresarFragmentLogin.setOnClickListener(view -> {
            boolean vacio = (editTextEmailFragmentLogin.getText().toString().equals("") || editTextPasswordFragmentLogin.getText().toString().equals(""));
            if (!vacio) {
                loginUser();
            } else
                Toast.makeText(getContext(), "Ambos campos deben estar completos", Toast.LENGTH_SHORT).show();
        });

        buttonRegistrarFragmentLogin.setOnClickListener(view -> {
            cardViewRegistroLogin.setVisibility(View.VISIBLE);
            editTextNombreFragmentLogin.requestFocus();
        });

        buttonEnviarFragmentLogin.setOnClickListener(v -> {
                    boolean regVacio = (editTextUserNameFragmentLogin.getText().toString().equals("") || editTextNombreFragmentLogin.getText().toString().equals("") || editTextApellidoFragmentLogin.getText().equals("") || editTextEdadFragmentLogin.getText().equals("") || editTextEmailFragmentLogin.getText().equals("") || editTextPasswordFragmentLogin.getText().equals(""));
                    if (!regVacio) {
                        registerUser();
                    } else
                        Toast.makeText(getContext(), "Todos los campos deben estar completos", Toast.LENGTH_SHORT).show();
                }
        );

    }


    private void goHome() {

        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.putExtra(MainActivity.KEY_PLAY, false);
        startActivity(intent);

    }

    private void registerUser() {
        mAuth = FirebaseAuth.getInstance();
        String email = editTextEmailFragmentLogin.getText().toString();
        String password = editTextPasswordFragmentLogin.getText().toString();
        User user = createUser(email, password);

        userViewModel.registerUser(email, password, user).observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser!= null) {
                    Toast.makeText(getContext(), "Se registró con exito!", Toast.LENGTH_SHORT).show();
                    goHome();
                    cardViewRegistroLogin.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getContext(), "Falló el registro!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private User createUser(String email, String pass) {

        String nombre = editTextNombreFragmentLogin.getText().toString();
        String apellido = editTextApellidoFragmentLogin.getText().toString();
        String edad = editTextEdadFragmentLogin.getText().toString();
        String userName = editTextUserNameFragmentLogin.getText().toString();
        User user = new User();
        user.setUserName(userName);
        user.setNombre(nombre);
        user.setApellido(apellido);
        user.setEdad(edad);
        user.setMail(email);
        user.setPassword(pass);
        return user;

    }


    private void loginUser() {
        String email = editTextEmailFragmentLogin.getText().toString();
        String password = editTextPasswordFragmentLogin.getText().toString();
        userViewModel.login(email, password).observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    userId = firebaseUser.getUid();
                    Toast.makeText(getContext(), "Exito!", Toast.LENGTH_SHORT).show();
                    goHome();
                } else {
                    Toast.makeText(getContext(), "fallo ingreso", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }



}