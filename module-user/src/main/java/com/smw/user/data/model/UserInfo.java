package com.smw.user.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.smw.base.bean.BaseBean;

import java.io.Serializable;

public class UserInfo extends BaseBean implements Serializable, Parcelable {
    private String uuid;
    private String user_name;
    private String telephone;
    private String head_image;
    private String self_words;
    private String notice_time;
    private int notice_read;
    private String notice_message;
    private float money_score;
    private String invite_code;
    private String token;
    private int vip_level;
    private String vip_start_time;
    private String vip_end_time;

    public UserInfo(){
    }

    protected UserInfo(Parcel in) {
        uuid = in.readString();
        user_name = in.readString();
        telephone = in.readString();
        head_image = in.readString();
        self_words = in.readString();
        notice_time = in.readString();
        notice_read = in.readInt();
        notice_message = in.readString();
        money_score = in.readFloat();
        invite_code = in.readString();
        token = in.readString();
        vip_level = in.readInt();
        vip_start_time = in.readString();
        vip_end_time = in.readString();
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getHead_image() {
        return head_image;
    }

    public void setHead_image(String head_image) {
        this.head_image = head_image;
    }

    public String getSelf_words() {
        return self_words;
    }

    public void setSelf_words(String self_words) {
        this.self_words = self_words;
    }

    public String getNotice_time() {
        return notice_time;
    }

    public void setNotice_time(String notice_time) {
        this.notice_time = notice_time;
    }

    public int getNotice_read() {
        return notice_read;
    }

    public void setNotice_read(int notice_read) {
        this.notice_read = notice_read;
    }

    public String getNotice_message() {
        return notice_message;
    }

    public void setNotice_message(String notice_message) {
        this.notice_message = notice_message;
    }

    public float getMoney_score() {
        return money_score;
    }

    public void setMoney_score(float money_score) {
        this.money_score = money_score;
    }

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getVip_level() {
        return vip_level;
    }

    public void setVip_level(int vip_level) {
        this.vip_level = vip_level;
    }

    public String getVip_start_time() {
        return vip_start_time;
    }

    public void setVip_start_time(String vip_start_time) {
        this.vip_start_time = vip_start_time;
    }

    public String getVip_end_time() {
        return vip_end_time;
    }

    public void setVip_end_time(String vip_end_time) {
        this.vip_end_time = vip_end_time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uuid);
        parcel.writeString(user_name);
        parcel.writeString(telephone);
        parcel.writeString(head_image);
        parcel.writeString(self_words);
        parcel.writeString(notice_time);
        parcel.writeInt(notice_read);
        parcel.writeString(notice_message);
        parcel.writeFloat(money_score);
        parcel.writeString(invite_code);
        parcel.writeString(token);
        parcel.writeInt(vip_level);
        parcel.writeString(vip_start_time);
        parcel.writeString(vip_end_time);
    }
}