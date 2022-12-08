package com.zhouyou.http.config;

public class ApiServices {
    public static final String BASE_URL = "http://172.16.0.69:8505";

    public interface User{
        String REGISTER     = BASE_URL+"/user/login/signUp";
        String LOGIN        = BASE_URL+"/user/login/signIn";
        String SEARCH       = BASE_URL+"/user/login/search";
    }

    public interface Contract{
        String TEMPLATE_GET     = BASE_URL+"/contract/manage/getFile";
        String TEMPLATE_DELETE  = BASE_URL+"/contract/manage/delete";
        String TEMPLATE_UPLOAD  = BASE_URL+"/contract/manage/upload";
        String TEMPLATE_CREATE  = BASE_URL+"/contract/manage/create";

        String CONTRACT_LS  = BASE_URL+"/contract/manage/getContract";
        String CONTRACT_DETAIL  = BASE_URL+"/contract/manage/detail";
        String CONTRACT_SIGN  = BASE_URL+"/contract/manage/sign";
        String CONTRACT_CONFIRM  = BASE_URL+"/contract/manage/confirm";
    }

}
