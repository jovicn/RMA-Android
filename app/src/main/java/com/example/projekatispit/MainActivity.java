package com.example.projekatispit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;


import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements DrawerLocker, NavigationView.OnNavigationItemSelectedListener, LoginFragment.OnFragmentInteractionListener, BookListFragment.OnFragmentInteractionListener, RegistracijaFragment.OnFragmentInteractionListener, BasketListFragment.OnFragmentInteractionListener, OrderFinishFragment.OnFragmentInteractionListener{

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment()).commit();

    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    //Odavde



    @Override
    public void onLoginSuccess() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BookListFragment()).addToBackStack(null).commit();
    }

    @Override
    public void onRegisterOpen() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RegistracijaFragment()).addToBackStack(null).commit();
    }

    @Override
    public void onDeleteItemFromList() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BasketListFragment()).addToBackStack(null).commit();
    }

    @Override
    public void onCheckout() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OrderFinishFragment()).addToBackStack(null).commit();
    }


    @Override
    public void onBookSelect(String bookId) {
        Fragment bookDetailFragment = new BookDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("bookId", bookId);
        bookDetailFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, bookDetailFragment).addToBackStack(null).commit();
    }


    @Override
    public void onRegistrySuccess() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment()).addToBackStack(null).commit();
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                break;

            case R.id.nav_books:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BookListFragment()).commit();
                break;

            case R.id.nav_basket:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BasketListFragment()).commit();
                break;

            /*case R.id.nav_ordered:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                break;*/
        }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void lockDrawer() {
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void unlockDrawer() {
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
    }

    @Override
    public void onFinishOrder() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BookListFragment()).addToBackStack(null).commit();
    }
}