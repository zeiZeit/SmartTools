package com.smw.common.contract;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import com.smw.base.bean.BaseBean;

public class SearchUserBean extends BaseBean implements Parcelable {
    private String user_name;
    private String head_image;
    private String uuid;
    private boolean isAdd; //是否是添加按钮
    private boolean canDelete; //是否可以被删除

    public SearchUserBean() {
    }

    public SearchUserBean(String user_name, String head_image, String uuid, boolean canDelete) {
        this.user_name = user_name;
        this.head_image = head_image;
        this.uuid = uuid;
        this.canDelete = canDelete;
    }

    protected SearchUserBean(Parcel in) {
        user_name = in.readString();
        head_image = in.readString();
        uuid = in.readString();
        isAdd = in.readByte() != 0;
        canDelete = in.readByte() != 0;
    }

    public static final Creator<SearchUserBean> CREATOR = new Creator<SearchUserBean>() {
        @Override
        public SearchUserBean createFromParcel(Parcel in) {
            return new SearchUserBean(in);
        }

        @Override
        public SearchUserBean[] newArray(int size) {
            return new SearchUserBean[size];
        }
    };

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getHead_image() {
        return head_image;
    }

    public void setHead_image(String head_image) {
        this.head_image = head_image;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_name);
        dest.writeString(head_image);
        dest.writeString(uuid);
        dest.writeByte((byte) (isAdd ? 1 : 0));
        dest.writeByte((byte) (canDelete ? 1 : 0));
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return this.uuid.equals(((SearchUserBean)obj).uuid);
    }
}
