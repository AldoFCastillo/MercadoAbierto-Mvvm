package com.example.mercadoabierto2_mvvm.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mercadoabierto2_mvvm.pojo.pojo.User;
import com.example.mercadoabierto2_mvvm.DataSource.UserDataSource;

import com.google.firebase.auth.FirebaseUser;


public class UserViewModel extends AndroidViewModel {

    private UserDataSource userDataSource;


    public UserViewModel(@NonNull Application application) {
        super(application);
        userDataSource = new UserDataSource();

    }

    public LiveData<User> getUser() {
        return userDataSource.getUSer();
    }

    public LiveData<FirebaseUser> login (String email, String password){
       return userDataSource.login(email, password);
    }

    public LiveData<FirebaseUser> registerUser(String email, String password, User user) {
        return userDataSource.registerUser(email, password, user);
    }

    public LiveData<User> getProfile(){
        return userDataSource.getUSer();
    }

    public  LiveData<Boolean> addToFavs(String productId){
        return userDataSource.addToFavs(productId);
    }

    public LiveData<Boolean> removeFav(String productId){
        return userDataSource.removeFav(productId);
    }

}
