package com.smw.contract.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SignFileBean implements Parcelable {
    private String contract_file_url;
    private int enable;
    private String file_name;
    private int file_type;
    private int need_sign;
    private String now_sign_uuid;
    private String now_sign_name;
    private String now_sign_head;
    private int read_time;
    private int status;
    private String template_id;
    private String update_time;

    public SignFileBean() {
    }

    protected SignFileBean(Parcel in) {
        contract_file_url = in.readString();
        enable = in.readInt();
        file_name = in.readString();
        file_type = in.readInt();
        need_sign = in.readInt();
        now_sign_uuid = in.readString();
        read_time = in.readInt();
        status = in.readInt();
        template_id = in.readString();
        update_time = in.readString();
    }

    public static final Creator<SignFileBean> CREATOR = new Creator<SignFileBean>() {
        @Override
        public SignFileBean createFromParcel(Parcel in) {
            return new SignFileBean(in);
        }

        @Override
        public SignFileBean[] newArray(int size) {
            return new SignFileBean[size];
        }
    };

    public String getContract_file_url() {
        return contract_file_url;
    }

    public void setContract_file_url(String contract_file_url) {
        this.contract_file_url = contract_file_url;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public int getFile_type() {
        return file_type;
    }

    public void setFile_type(int file_type) {
        this.file_type = file_type;
    }

    public int getNeed_sign() {
        return need_sign;
    }

    public void setNeed_sign(int need_sign) {
        this.need_sign = need_sign;
    }

    public String getNow_sign_uuid() {
        return now_sign_uuid;
    }

    public void setNow_sign_uuid(String now_sign_uuid) {
        this.now_sign_uuid = now_sign_uuid;
    }

    public int getRead_time() {
        return read_time;
    }

    public void setRead_time(int read_time) {
        this.read_time = read_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(contract_file_url);
        parcel.writeInt(enable);
        parcel.writeString(file_name);
        parcel.writeInt(file_type);
        parcel.writeInt(need_sign);
        parcel.writeString(now_sign_uuid);
        parcel.writeInt(read_time);
        parcel.writeInt(status);
        parcel.writeString(template_id);
        parcel.writeString(update_time);
    }

    public String getNow_sign_name() {
        return now_sign_name;
    }

    public void setNow_sign_name(String now_sign_name) {
        this.now_sign_name = now_sign_name;
    }

    public String getNow_sign_head() {
        return now_sign_head;
    }

    public void setNow_sign_head(String now_sign_head) {
        this.now_sign_head = now_sign_head;
    }
}
