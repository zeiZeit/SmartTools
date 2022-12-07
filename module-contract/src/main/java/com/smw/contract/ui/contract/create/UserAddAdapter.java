package com.smw.contract.ui.contract.create;

import android.graphics.Color;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.smw.common.adapter.CommonBindingAdapters;
import com.smw.common.views.GlideCircleWithBorder;
import com.smw.contract.R;
import com.smw.common.contract.SearchUserBean;

public class UserAddAdapter extends BaseQuickAdapter<SearchUserBean, BaseViewHolder> {

    public UserAddAdapter() {
        super(R.layout.contract_layout_user_add);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, SearchUserBean userBean) {
        if (userBean.isAdd()){
            baseViewHolder.setGone(R.id.rl_add,false);
            baseViewHolder.setGone(R.id.rl_user,true);
            baseViewHolder.setGone(R.id.remove,true);
        }else {
            baseViewHolder.setGone(R.id.rl_add,true);
            baseViewHolder.setGone(R.id.rl_user,false);
            baseViewHolder.setText(R.id.tv_name,userBean.getUser_name());
            ImageView avatar = baseViewHolder.getView(R.id.head);

            CommonBindingAdapters.loadHeadImage(avatar,userBean.getHead_image());

            baseViewHolder.setGone(R.id.remove,!userBean.isCanDelete());
        }
    }
}
