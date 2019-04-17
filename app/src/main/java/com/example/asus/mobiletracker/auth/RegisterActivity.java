package com.example.asus.mobiletracker.auth;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.asus.mobiletracker.R;
import com.example.asus.mobiletracker.entities.AccessToken;
import com.example.asus.mobiletracker.entities.ApiValidation;
import com.example.asus.mobiletracker.entities.TokenManager;
import com.example.asus.mobiletracker.network.ApiService;
import com.example.asus.mobiletracker.network.RetrofitBuilder;

import com.example.asus.mobiletracker.ui.TemzaActivity;
import com.example.asus.mobiletracker.utils.Utils;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";


    @BindView(R.id.til_name) TextInputLayout tilName;

    @BindView(R.id.til_email) TextInputLayout tilEmail;

    @BindView(R.id.til_password) TextInputLayout tilPassword;

    @BindView(R.id.loader1)
    ProgressBar loader;


    @BindView(R.id.container1)
    LinearLayout container;

    @BindView(R.id.form_container1)
    LinearLayout formContainer;

    ApiService apiService;
    TokenManager tokenManager;
    Call<AccessToken> call;
    AwesomeValidation awesomeValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

        tokenManager = TokenManager.getInstance(getSharedPreferences("preferences",MODE_PRIVATE));

        awesomeValidation = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);

//        tokenManager.deleteToken();
        Log.d(TAG,"ACCESTOKEN" + tokenManager.getToken().getAccessToken());
        Log.d(TAG,"TIIIIIIIIIIIIIME: " + tokenManager.getToken().getExpiresIn());

        apiService = RetrofitBuilder.createService(ApiService.class);

        setupRules();

    }


    @OnClick(R.id.btn_register)
    void register()
    {
        String name = tilName.getEditText().getText().toString();
        String email = tilEmail.getEditText().getText().toString();
        String password = tilPassword.getEditText().getText().toString();

        tilName.setError(null);
        tilEmail.setError(null);
        tilPassword.setError(null);

        awesomeValidation.clear();

        if (awesomeValidation.validate()) {

            showLoading();

            call = apiService.register(name, email, password);

            call.enqueue(new Callback<AccessToken>() {

                @Override
                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                    Log.w(TAG, "onResponse " + response);

                    if (response.isSuccessful()) {

                        Log.w(TAG, "onResponse " + response.body());

                        tokenManager.saveToken(response.body());

                        startActivity(new Intent(RegisterActivity.this,TemzaActivity.class));

                        finish();

                    } else {
                        handleErrors(response.errorBody());
                    }
                    showForm();
                }

                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    Log.w(TAG, "onFailure: " + t.getMessage());
                }
            });
        }
    }

    public void setupRules(){
        awesomeValidation.addValidation(this,R.id.til_name,RegexTemplate.NOT_EMPTY,R.string.error_field_required);
        awesomeValidation.addValidation(this,R.id.til_email, Patterns.EMAIL_ADDRESS,R.string.error_invalid_email);
        awesomeValidation.addValidation(this,R.id.til_password,"[a-zA-Z0-9]{6,}",R.string.error_invalid_password);


    }

    private void handleErrors(ResponseBody responseBody)
    {
        ApiValidation apiError = Utils.convertErrors(responseBody);


        for (Map.Entry<String,List<String>> error : apiError.getErrors().entrySet()){
            if (error.getKey().equals("name"))
            {
                tilName.setError(error.getValue().get(0));
            }
            if (error.getKey().equals("email"))
            {
                tilEmail.setError(error.getValue().get(0));
            } if (error.getKey().equals("password"))
            {
                tilPassword.setError(error.getValue().get(0));
            }

        }
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
    void goToLogin(){
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));//replace with LoginActivity
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(call != null) {
            call.cancel();
            call = null;
        }
    }


}
