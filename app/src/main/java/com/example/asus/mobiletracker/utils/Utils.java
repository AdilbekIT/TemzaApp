package com.example.asus.mobiletracker.utils;

import com.example.asus.mobiletracker.entities.ApiValidation;
import com.example.asus.mobiletracker.network.RetrofitBuilder;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class Utils {
    public static ApiValidation convertErrors(ResponseBody response){

        Converter<ResponseBody,ApiValidation> converter = RetrofitBuilder.getRetrofit()
                .responseBodyConverter(ApiValidation.class, new Annotation[0]);

        ApiValidation apiError = null;

        try {
            apiError = converter.convert(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  apiError;
    }
}
