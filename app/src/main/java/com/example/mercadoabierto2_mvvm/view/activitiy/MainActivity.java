package com.example.mercadoabierto2_mvvm.view.activitiy;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mercadoabierto2_mvvm.view.fragment.IntroFragment;
import com.example.mercadoabierto2_mvvm.R;

import com.example.mercadoabierto2_mvvm.model.pojo.Product;

import com.example.mercadoabierto2_mvvm.model.pojo.ProductDetails;
import com.example.mercadoabierto2_mvvm.model.pojo.User;
import com.example.mercadoabierto2_mvvm.view.fragment.AboutUsFragment;
import com.example.mercadoabierto2_mvvm.view.fragment.FavsFragment;
import com.example.mercadoabierto2_mvvm.view.fragment.LoginFragment;
import com.example.mercadoabierto2_mvvm.view.fragment.MercadoFragment;
import com.example.mercadoabierto2_mvvm.view.fragment.ProfileFragment;

import com.example.mercadoabierto2_mvvm.viewModel.UserViewModel;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MercadoFragment.Listener, FavsFragment.Listener, IntroFragment.listener {


    public static final String KEY_PLAY = "play";
    private MercadoFragment mercadoFragment;
    private AboutUsFragment aboutUSFragment;
    private FragmentManager fragmentManager;
    private long backPressedTime;
    private Toast backToast;
    private MenuItem menuLogin;
    private UserViewModel userViewModel;
    private Boolean playIntro = true;

    @BindView(R.id.navigationViewHome)
    NavigationView navigationView;
    @BindView(R.id.drawerHome)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.titleToolbar)
    TextView titleTool;
    @BindView(R.id.mainLayout)
    LinearLayout mainLayout;
    //Header
    TextView textViewWelcomeHeaderLogName;
    TextView textViewIngresarHeader;
    TextView textViewWelcomeHeaderNavigation;
    TextView textViewTextHeaderNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if(getIntent().getExtras()!=null){
        playIntro = getIntent().getExtras().getBoolean(KEY_PLAY);}

        aboutUSFragment = new AboutUsFragment();
        mercadoFragment = new MercadoFragment();

        setToolBar();
        setNavigationView();
        setHeaderLogin();
        setToolBar();
        if (playIntro){
        setFragment(new IntroFragment());
        playIntro = false;
        }else setFragment(mercadoFragment);

    }

    private void setNavigationView() {
        menuLogin = navigationView.getMenu().findItem(R.id.navigationViewLoginItem);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.navigationViewPerfil:
                        setFragment(new ProfileFragment());
                        Snackbar.make(mainLayout, "Mi Perfil", Snackbar.LENGTH_LONG).show();
                        break;

                    case R.id.navigationViewFavoritosItem:
                        setFragment(new FavsFragment());
                        Snackbar.make(mainLayout, "Mis Favoritos", Snackbar.LENGTH_LONG).show();
                        break;

                    case R.id.navigationViewCerrarSesionItem:
                        FirebaseAuth.getInstance().signOut();
                        drawerLayout.closeDrawers();
                        Toast.makeText(MainActivity.this, "Desconexion exitosa", Toast.LENGTH_SHORT).show();
                        setVisibilityNavigation(false, "");
                        break;

                    case R.id.aboutUsMenuNavigation:
                        setFragment(aboutUSFragment);
                        Snackbar.make(mainLayout, "About Us", Snackbar.LENGTH_LONG).show();
                        break;
                }

                drawerLayout.closeDrawers();

                return false;
            }
        });
    }

    public void setFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerFragmentMain, fragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            setFragment(mercadoFragment);
            backToast = Toast.makeText(getBaseContext(), "Presiona atras nuevamente para salir", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();

    }


    private void setToolBar() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_hamburguesa, R.string.close_hamburguesa);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.itemToolBarSearch).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                titleTool.setVisibility(View.VISIBLE);
                return false;
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleTool.setVisibility(View.GONE);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                titleTool.setVisibility(View.VISIBLE);
                if (!query.isEmpty()) {mercadoFragment.searchProducts(query);}
                searchView.onActionViewCollapsed();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemToolbarHome:
                goHome();
                setHeaderLogin();
                drawerLayout.closeDrawers();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void goHome() {
        mercadoFragment.goHome();
        setFragment(mercadoFragment);
    }

    private void setHeaderLogin() {

        View hView = navigationView.getHeaderView(0);
        textViewIngresarHeader = hView.findViewById(R.id.textViewIngresarHeader);
        textViewWelcomeHeaderLogName = hView.findViewById(R.id.textViewBienvenidaHeaderLogNombre);
        textViewWelcomeHeaderNavigation = hView.findViewById(R.id.textViewBienvenidaHeaderNavigation);
        textViewTextHeaderNavigation = hView.findViewById(R.id.textViewTextHeaderNavigation);

        getUser();

        textViewIngresarHeader.setOnClickListener(v -> {
            setFragment(new LoginFragment());
            drawerLayout.closeDrawers();
        });
    }


    private void getUser() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            userViewModel.getUser().observe(this, new Observer<User>() {
                @Override
                public void onChanged(User result) {
                    if (result != null) {
                        setVisibilityNavigation(true, result.getUserName());
                    } else {
                        setVisibilityNavigation(false, "");
                    }
                }
            });
        } else setVisibilityNavigation(false, "");


    }

    private void setVisibilityNavigation(Boolean connected, String user) {

        if (!connected) {
            textViewIngresarHeader.setVisibility(View.VISIBLE);
            textViewTextHeaderNavigation.setVisibility(View.VISIBLE);
            textViewWelcomeHeaderNavigation.setVisibility(View.VISIBLE);
            textViewWelcomeHeaderLogName.setVisibility(View.GONE);
            menuLogin.setVisible(false);

        } else {
            textViewWelcomeHeaderLogName.setText(user);
            textViewIngresarHeader.setVisibility(View.GONE);
            textViewTextHeaderNavigation.setVisibility(View.GONE);
            textViewWelcomeHeaderNavigation.setVisibility(View.GONE);
            textViewWelcomeHeaderLogName.setVisibility(View.VISIBLE);
            menuLogin.setVisible(true);
        }
    }



    @Override
    public void mercadoFragmentListener(Integer adapterPosition, List<Product> productList) {
        Bundle bundle = new Bundle();
        bundle.putInt(DetailsActivity.KEY_POSITION, adapterPosition);
        Map<String, List<Product>> productsMap = new HashMap<>();
        productsMap.put(DetailsActivity.KEY_PRODUCTS, productList);
        bundle.putSerializable(DetailsActivity.KEY_PRODUCTS, (Serializable) productsMap);
        startActivity(bundle);
    }


    @Override
    public void favsFragmentListener(Integer adapterPosition, List<ProductDetails> favList) {
        Bundle bundle = new Bundle();
        bundle.putInt(DetailsActivity.KEY_POSITION, adapterPosition);
        Map<String, List<ProductDetails>> productsMap = new HashMap<>();
        productsMap.put(DetailsActivity.KEY_DETAILS, favList);
        bundle.putSerializable(DetailsActivity.KEY_DETAILS, (Serializable) productsMap);
        startActivity(bundle);

    }

    private void startActivity(Bundle bundle) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void listenerIntro() {
        setFragment(mercadoFragment);
    }
}