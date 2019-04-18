package com.example.asus.mobiletracker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.asus.mobiletracker.R;
import com.example.asus.mobiletracker.auth.LoginActivity;
import com.example.asus.mobiletracker.entities.AccessToken;
import com.example.asus.mobiletracker.entities.ApiValidation;
import com.example.asus.mobiletracker.entities.TokenManager;
import com.example.asus.mobiletracker.network.ApiService;
import com.example.asus.mobiletracker.network.RetrofitBuilder;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.prefs.PreferencesFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.asus.mobiletracker.utils.Utils.convertErrors;

public class TemzaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "TemzaActivity";

    ApiService apiService;
    TokenManager tokenManager;
    AwesomeValidation validation;
    Call<AccessToken> call;


    private ShimmerFrameLayout mShimmerViewContainer;
    private RecyclerView recyclerView;

    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temza);

        //toolbar elements
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);



        //floating button to filter data
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        //navigation bars
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //init shimmer animation
//        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);


        //Retrofit with laravel passport api tokens and check if it exists
        tokenManager = TokenManager.getInstance(getSharedPreferences("preferences",MODE_PRIVATE));

//        tokenManager.deleteToken();
        Log.d(TAG,"ACCESS_TOKEN" + tokenManager.getToken().getAccessToken());

        apiService = RetrofitBuilder.createService(ApiService.class);
        validation = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);


        if (tokenManager.getToken().getAccessToken() == null)
        {
            startActivity(new Intent(TemzaActivity.this,LoginActivity.class));
            finish();
        }

        // recycler view to do list
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);



        //swipe refresh action
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);


        //getting list of products from a server
        getProductList();


    }



    @Override
    public void onRefresh() {

        Toast.makeText(this, "Обновление...", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                getProductList();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1300);
    }

    public void getProductList(){


        //TODO: write code to receive data from sever callback method by Adilbek
//
//        call = apiService.freeServices();
//        call.enqueue(new Callback<FreeServicesResponse>() {
//
//            @Override
//            public void onResponse(Call<FreeServicesResponse> call, Response<FreeServicesResponse> response) {
//
//                Log.w(TAG,"onResponse: " + response);
//                Log.d(TAG,"onTokenManager: -------------" + tokenManager.getToken().toString());
//
//                if (response.isSuccessful())
//                {
//                    lister = response.body().getData();
//
//                    mAdapter = new PostListAdapter(lister, CertainActivity.this, new PostListAdapter.OnNoteListener() {
//                        @Override
//                        public void onNoteClick(int position) {
//                            int postId = lister.get(position).getId();
//
//
//                            Intent intent = new Intent(CertainActivity.this,SingleActivity.class);
//                            intent.putExtra("id_organization",postId);
//
//                            startActivity(intent);
//                        }
//                    });
//
//                    mAdapter.notifyDataSetChanged();
//                    mShimmerViewContainer.stopShimmer();
//                    mShimmerViewContainer.setVisibility(View.GONE);
//
//                    recyclerView.setVisibility(View.VISIBLE);
//                    recyclerView.setAdapter(mAdapter);
//
//
//                    Toast.makeText(getApplicationContext(),"Все успешно загружено",Toast.LENGTH_SHORT).show();
//
//                }else {
//                    Toast.makeText(getApplicationContext(),"Не могу загрузить данные" + tokenManager.getToken(),Toast.LENGTH_LONG).show();
//                    tokenManager.deleteToken();
//                    startActivity(new Intent(CertainActivity.this, LoginActivity.class));
//                    finish();
//                }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.temza, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_orders) {
            // Handle the camera action
        } else if (id == R.id.nav_history) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_info) {
            info();


        } else if (id == R.id.nav_exit) {
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //logout by Adilbek
    public void logout(){

            call = apiService.logout(tokenManager.getToken().getAccessToken());
            call.enqueue(new Callback<AccessToken>() {
                @Override
                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {

                    Log.w(TAG, "onResponse: " + response.body());

                    if (response.isSuccessful()) {
                        tokenManager.saveToken(response.body());
                        startActivity(new Intent(TemzaActivity.this,LoginActivity.class));
                        finish();
                    }
                }
                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    Log.w(TAG, "onFailure: " + t.getMessage());

                }
            });
        }


        //by Yerassyl
    void info(){
        Intent intent = new Intent(this,ScrollingActivity.class);
        startActivity(intent);
    }

}
