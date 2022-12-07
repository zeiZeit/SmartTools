package com.smw.user.data;

import android.content.Context;

import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.limpoxe.support.servicemanager.ServiceManager;
import com.smw.base.utils.GsonUtils;
import com.smw.common.global.GlobalKey;
import com.smw.common.services.ILoginService;
import com.smw.common.utils.StringUtils;
import com.smw.user.config.UserConfig;
import com.smw.user.data.model.UserInfo;
import com.tencent.mmkv.MMKV;
import com.smw.common.services.config.ServicesConfig;

//@Route(path = ServicesConfig.User.LONGING_SERVICE, name =ILoginService.LOGIN_SERVICE_NAME)
public class UserInfoUtil implements ILoginService {

    private UserInfo mUserInfo;

    public UserInfoUtil(){
        String userStr = MMKV.mmkvWithID(UserConfig.Keys.KEY_USER_INFO).decodeString(UserConfig.Keys.KEY_USER_INFO_STR);
        mUserInfo = GsonUtils.fromLocalJson(userStr,UserInfo.class);
        if (mUserInfo == null){
            mUserInfo = new UserInfo();
        }
    }

    private static UserInfoUtil instance;

    public static UserInfoUtil getInstance(){
        if (instance==null){
            instance = new UserInfoUtil();
        }
        return instance;
    }

    public UserInfo getUserInfo(){
        return mUserInfo;
    }

    public void updateUserInfo(UserInfo userInfo){
        mUserInfo = userInfo;
        String str = GsonUtils.toJson(userInfo);
        MMKV.mmkvWithID(UserConfig.Keys.KEY_USER_INFO).encode(UserConfig.Keys.KEY_USER_INFO_STR,str);
        LiveEventBus
                .get(GlobalKey.Event.USER_INFO_UPDATE, String.class)
                .post("info_change");



        if (mUserInfo==null||StringUtils.isEmpty(mUserInfo.getToken())){
            //未登录状态 注销服务
            ServiceManager.unPublishService(ILoginService.LOGIN_SERVICE_NAME);
        }else {
            ServiceManager.publishService(ILoginService.LOGIN_SERVICE_NAME,UserInfoUtil.class.getName());
        }
    }

    @Override
    public boolean saveStatus(boolean status) {
        return false;
    }

    @Override
    public boolean isLogin() {
        return !StringUtils.isNullOrEmpty(mUserInfo.getToken());
    }

    @Override
    public String getToken() {
        return mUserInfo.getToken();
    }

    @Override
    public String getUUID() {
        return mUserInfo.getUuid();
    }

    @Override
    public String getUserName() {
        return mUserInfo.getUser_name();
    }

    public String getHeadImage(){
        return mUserInfo.getHead_image();
    }

    @Override
    public void refreshUserDetailInfo() {

    }

    @Override
    public void login() {

    }

    @Override
    public void login(boolean isMainAccountLogin) {

    }

    @Override
    public void logout() {
        mUserInfo.setUuid("");
        mUserInfo.setInvite_code("");
        mUserInfo.setTelephone("");
        mUserInfo.setToken("");
        mUserInfo.setUser_name("");
        mUserInfo.setHead_image("");
        mUserInfo.setMoney_score(0);
        mUserInfo.setNotice_read(1);
        mUserInfo.setNotice_message("");
        mUserInfo.setNotice_time("");
        mUserInfo.setSelf_words("");
        mUserInfo.setVip_level(0);
        updateUserInfo(mUserInfo);
    }

    @Override
    public void onLoginCancel() {

    }

    @Override
    public void onTokenExpire() {
        //todo token过期
    }

    @Override
    public void init(Context context) {

    }
}
