package com.example.mercadoabierto2_mvvm.DataSource;


import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;


import com.bumptech.glide.load.ImageHeaderParser;
import com.example.mercadoabierto2_mvvm.pojo.pojo.Product;
import com.example.mercadoabierto2_mvvm.pojo.pojo.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class UserDataSource {

    private String id="";
    private List<String> aFavList = new ArrayList<>();
    private FirebaseFirestore db;


    public UserDataSource() {
    }


    /*public LiveData<User> getUserr() {
        MutableLiveData<User> userMutable = new MutableLiveData<>();
        User emptyUser = null;
        userMutable.setValue(emptyUser);
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db;
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String id = currentUser.getUid();
            db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("usuarios").document(id);
            docRef.get().addOnSuccessListener(documentSnapshot -> {
                User user = documentSnapshot.toObject(User.class);
                userMutable.setValue(user);
            });
        }
        return userMutable;
    }*/


    public LiveData<FirebaseUser> login(String email, String password) {
        MutableLiveData<FirebaseUser> firebaseUserMutable = new MutableLiveData<>();
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    firebaseUserMutable.setValue(firebaseUser);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                FirebaseUser firebaseUser = null;
                firebaseUserMutable.setValue(firebaseUser);
            }
        });

        return firebaseUserMutable;
    }

    public LiveData<FirebaseUser> registerUser(String email, String password, User user) {
        MutableLiveData<FirebaseUser> firebaseUserMutable = new MutableLiveData<>();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        addProfile(user, firebaseUser.getUid());
                        firebaseUserMutable.setValue(firebaseUser);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                FirebaseUser firebaseUser = null;
                firebaseUserMutable.setValue(firebaseUser);
            }
        });

        return firebaseUserMutable;
    }


    private void addProfile(User user, String userId) {
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        db.collection("usuarios").document(userId).set(user).addOnSuccessListener(aVoid ->
                System.out.println("EXITO"));

    }

    public LiveData<User> getUSer() {
        MutableLiveData<User> userMutable = new MutableLiveData<>();
        DocumentReference docRef = getDocumentReference();
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            User user = documentSnapshot.toObject(User.class);
            userMutable.setValue(user);
        });
        return userMutable;
    }

    private DocumentReference getDocumentReference(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        id = currentUser.getUid();
        db = FirebaseFirestore.getInstance();
        return db.collection("usuarios").document(id);
    }


    public LiveData<Boolean> addToFavs(String productId){
        MutableLiveData<Boolean> mutableBool = new MutableLiveData<>();
        DocumentReference docRef = getDocumentReference();
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            User user = documentSnapshot.toObject(User.class);
            aFavList = user.getFavoritos();
            aFavList.add(productId);
            user.setFavoritos(aFavList);
            db.collection("usuarios").document(id).set(user).addOnSuccessListener(aVoid -> {
                mutableBool.setValue(true);
            }).addOnFailureListener(e -> mutableBool.setValue(false));

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mutableBool.setValue(false);
            }
        });

        return mutableBool;
    }

    public LiveData<Boolean> removeFav(String productId){
        MutableLiveData<Boolean> mutableBool = new MutableLiveData<>();
        getDocumentReference().get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                aFavList = user.getFavoritos();
                if(aFavList.contains(productId)){
                    aFavList.remove(productId);
                    user.setFavoritos(aFavList);
                    getDocumentReference().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mutableBool.setValue(true);
                        }
                    });
                }else mutableBool.setValue(false);
            }
        });

        return mutableBool;

    }


}
