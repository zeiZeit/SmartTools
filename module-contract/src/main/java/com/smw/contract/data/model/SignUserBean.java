package com.smw.contract.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SignUserBean implements Parcelable {
    private String head_image;
    private String user_name;
    private String uuid;
    private int status;

    public SignUserBean() {
    }

    protected SignUserBean(Parcel in) {
        head_image = in.readString();
        user_name = in.readString();
        uuid = in.readString();
        status = in.readInt();
    }

    public static final Creator<SignUserBean> CREATOR = new Creator<SignUserBean>() {
        @Override
        public SignUserBean createFromParcel(Parcel in) {
            return new SignUserBean(in);
        }

        @Override
        public SignUserBean[] newArray(int size) {
            return new SignUserBean[size];
        }
    };

    public String getHead_image() {
        return head_image;
    }

    public void setHead_image(String head_image) {
        this.head_image = head_image;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(head_image);
        parcel.writeString(user_name);
        parcel.writeString(uuid);
        parcel.writeInt(status);
    }
}
