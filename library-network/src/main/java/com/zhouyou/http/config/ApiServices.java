package com.zhouyou.http.config;

public class ApiServices {
    public static final String BASE_URL = "http://172.16.0.69:8505";

    public interface User{
        String REGISTER = BASE_URL+"/user/login/signUp";
        String LOGIN = BASE_URL+"/user/login/signIn";
        String SEARCH = BASE_URL+"/user/login/search";
    }

}
