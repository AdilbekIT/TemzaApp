package com.example.asus.mobiletracker.auth;

import android.support.v7.app.AppCompatActivity;

import android.content.Intent;

import android.support.design.widget.TextInputLayout;
import android.support.transition.TransitionManager;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import com.example.asus.mobiletracker.R;
import com.example.asus.mobiletracker.entities.AccessToken;
import com.example.asus.mobiletracker.entities.ApiValidation;
import com.example.asus.mobiletracker.entities.TokenManager;
import com.example.asus.mobiletracker.network.ApiService;
import com.example.asus.mobiletracker.network.RetrofitBuilder;
import com.example.asus.mobiletracker.ui.TemzaActivity;


import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.asus.mobiletracker.utils.Utils.convertErrors;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";



    @BindView(R.id.til_email)
    TextInputLayout tilEmail;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;

    @BindView(R.id.form_container)
    LinearLayout formContainer;

    @BindView(R.id.container)
    LinearLayout container;

    @BindView(R.id.loader)
    ProgressBar loader;

    ApiService apiService;
    TokenManager tokenManager;
    AwesomeValidation validation;
    Call<AccessToken> call;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ButterKnife.bind(this);

        tokenManager = TokenManager.getInstance(getSharedPreferences("preferences",MODE_PRIVATE));

//        tokenManager.deleteToken();
        Log.d(TAG,"ACCESTOKEN" + tokenManager.getToken().getAccessToken());
        Log.d(TAG,"TIIIIIIIIIIIIIME: " + tokenManager.getToken().getExpiresIn());

        apiService = RetrofitBuilder.createService(ApiService.class);
        validation = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);


        if (tokenManager.getToken().getAccessToken() != null)
        {
            startActivity(new Intent(LoginActivity.this,TemzaActivity.class));
            finish();
        }

        setupRules();

    }


    private void showLoading(){
        TransitionManager.beginDelayedTransition(container);
        formContainer.setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);
    }
    private void showForm(){
        TransitionManager.beginDelayedTransition(container);
        formContainer.setVisibility(View.VISIBLE);
        loader.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_login)
    void login(){

        String email = tilEmail.getEditText().getText().toString();
        String password = tilPassword.getEditText().getText().toString();

        tilEmail.setError(null);
        tilPassword.setError(null);

        Log.d(TAG,"EMAIL ----------- " + email);
        Log.d(TAG,"PASSSWORD  ----------- " + password);

        validation.clear();

        if (validation.validate()) {

            showLoading();

            call = apiService.login(email, password);
            call.enqueue(new Callback<AccessToken>() {
                @Override
                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {

                    Log.w(TAG, "onResponse: " + response);

                    if (response.isSuccessful()) {
                        tokenManager.saveToken(response.body());
                        startActivity(new Intent(LoginActivity.this,TemzaActivity.class));
                        finish();

                    } else {
                        if (response.code() == 422) {
                            handleErrors(response.errorBody());
                        }
                        if (response.code() == 401) {
                            ApiValidation apiError = convertErrors(response.errorBody());
                            Toast.makeText(LoginActivity.this, apiError.getMessage(), Toast.LENGTH_LONG).show();

                        }
                        showForm();
                    }
                }
                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    Log.w(TAG, "onFailure: " + t.getMessage());
                    showForm();
                }
            });
        }
    }

    public void setupRules(){

        validation.addValidation(this,R.id.til_email, Patterns.EMAIL_ADDRESS,R.string.error_invalid_email);
        validation.addValidation(this,R.id.til_password,"[a-zA-Z0-9]{6,}",R.string.error_invalid_password);

    }
    private void handleErrors(ResponseBody responseBody)
    {
        ApiValidation apiError = convertErrors(responseBody);


        for (Map.Entry<String,List<String>> error : apiError.getErrors().entrySet()){
            if (error.getKey().equals("username"))
            {
                tilEmail.setError(error.getValue().get(0));
            } if (error.getKey().equals("password"))
            {
                tilPassword.setError(error.getValue().get(0));
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (call != null){
            call.cancel();
            call = null;
        }
    }


    @OnClick(R.id.go_to_register)
    void goToRegister(){
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

}
