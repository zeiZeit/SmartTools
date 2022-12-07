package com.smw.contract.data.model;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

public class TemplateFile implements Parcelable {
    private String create_time;
    private String enable;
    private String file_code;
    private String file_name;
    private int file_type;
    private String file_url;
    private int need_sign;
    private int read_time;
    private String template_id;
    private String update_time;

    private boolean isCheck;
    private boolean isChooseType;

    public TemplateFile() {
    }


    protected TemplateFile(Parcel in) {
        create_time = in.readString();
        enable = in.readString();
        file_code = in.readString();
        file_name = in.readString();
        file_type = in.readInt();
        file_url = in.readString();
        need_sign = in.readInt();
        read_time = in.readInt();
        template_id = in.readString();
        update_time = in.readString();
        isCheck = in.readByte() != 0;
        isChooseType = in.readByte() != 0;
    }

    public static final Creator<TemplateFile> CREATOR = new Creator<TemplateFile>() {
        @Override
        public TemplateFile createFromParcel(Parcel in) {
            return new TemplateFile(in);
        }

        @Override
        public TemplateFile[] newArray(int size) {
            return new TemplateFile[size];
        }
    };

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getFile_code() {
        return file_code;
    }

    public void setFile_code(String file_code) {
        this.file_code = file_code;
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

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public int getNeed_sign() {
        return need_sign;
    }

    public void setNeed_sign(int need_sign) {
        this.need_sign = need_sign;
    }

    public int getRead_time() {
        return read_time;
    }

    public void setRead_time(int read_time) {
        this.read_time = read_time;
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

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(create_time);
        dest.writeString(enable);
        dest.writeString(file_code);
        dest.writeString(file_name);
        dest.writeInt(file_type);
        dest.writeString(file_url);
        dest.writeInt(need_sign);
        dest.writeInt(read_time);
        dest.writeString(template_id);
        dest.writeString(update_time);
        dest.writeByte((byte) (isCheck ? 1 : 0));
        dest.writeByte((byte) (isChooseType ? 1 : 0));
    }

    public boolean isChooseType() {
        return isChooseType;
    }

    public void setChooseType(boolean chooseType) {
        isChooseType = chooseType;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return this.template_id.equals(((TemplateFile)obj).template_id);
    }
}
