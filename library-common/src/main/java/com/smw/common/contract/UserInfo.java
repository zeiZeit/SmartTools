package com.smw.common.contract;

import java.io.Serializable;
import java.util.ArrayList;

import androidx.annotation.NonNull;

/**
 * 应用模块:
 * <p>
 * 类描述: 用户信息
 * <p>
 *
 * @author zeit
 * @since 2020-01-27
 */
public class UserInfo implements Serializable
{
    @NonNull
    private String uuid;
    private String name;
    private String signature;
    private String email;
    private String avatarRemoteUrl;
    private String phone;
    private int vipLevel;
    private String vipStartTime;
    private String vipEndTime;
    private int score;
    private String inviteCode;
    private String noticeMessage;
    private int noticeRead;
    private String noticeTime;
    private ArrayList<ProductsBean> buyProducts;
    private String accessToken;

    private String birthday;
    private String sex;
    private String refreshToken;
    // 到期时间
    private String tokenExpireTime;
    private int type;



    public static void reset(){
        getInstance().setUuid(null);
        getInstance().setName(null);
        getInstance().setSignature(null);
        getInstance().setEmail(null);
        getInstance().setAvatarRemoteUrl(null);
        getInstance().setPhone(null);
        getInstance().setVipLevel(0);
        getInstance().setVipStartTime("");
        getInstance().setVipEndTime("");
        getInstance().setScore(0);
        getInstance().setInviteCode(null);
        getInstance().setNoticeRead(1);
        getInstance().setNoticeMessage(null);
        getInstance().setAccessToken(null);
        getInstance().setBuyProducts(null);
    }


    private ThirdAccount[] thirdAccounts;
    
    private String regionId;

    public class ThirdAccount implements Serializable {
        public String avatarUrl;
        public String nickName;
        public String openId;
        public int thirdType;
        public String token;
        public String unionId;
        public String email;
    }

    private UserInfo(){
//        throw new UnsupportedOperationException("Do not instantiate");
    }

    public static UserInfo getInstance(){
        return UserHolder.INSTANCE;
    }

    public static void init(UserInfo obj){
        UserHolder.INSTANCE = obj;
    }

    static class UserHolder{
        static  UserInfo INSTANCE =  new UserInfo();
    }




    @NonNull
    public String getUuid() {
        return uuid;
    }

    public void setUuid(@NonNull String uuid) {
        this.uuid = uuid;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getTokenExpireTime() {
        return tokenExpireTime;
    }

    public void setTokenExpireTime(String tokenExpireTime) {
        this.tokenExpireTime = tokenExpireTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAvatarRemoteUrl() {
        return avatarRemoteUrl;
    }

    public void setAvatarRemoteUrl(String avatarRemoteUrl) {
        this.avatarRemoteUrl = avatarRemoteUrl;
    }

    public ThirdAccount[] getThirdAccounts() {
        return thirdAccounts;
    }

    public void setThirdAccounts(ThirdAccount[] thirdAccounts) {
        this.thirdAccounts = thirdAccounts;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    public String getVipStartTime() {
        return vipStartTime;
    }

    public void setVipStartTime(String vipStartTime) {
        this.vipStartTime = vipStartTime;
    }

    public String getVipEndTime() {
        return vipEndTime;
    }

    public void setVipEndTime(String vipEndTime) {
        this.vipEndTime = vipEndTime;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getNoticeMessage() {
        return noticeMessage;
    }

    public void setNoticeMessage(String noticeMessage) {
        this.noticeMessage = noticeMessage;
    }

    public int getNoticeRead() {
        return noticeRead;
    }

    public void setNoticeRead(int noticeRead) {
        this.noticeRead = noticeRead;
    }

    public String getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(String noticeTime) {
        this.noticeTime = noticeTime;
    }

    public ArrayList<ProductsBean> getBuyProducts() {
        return buyProducts;
    }

    public void setBuyProducts(ArrayList<ProductsBean> buyProducts) {
        this.buyProducts = buyProducts;
    }
}
