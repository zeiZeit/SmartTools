package com.smw.contract.ui.template;

import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.smw.common.views.SmoothCheckBox;
import com.smw.contract.R;
import com.smw.contract.data.model.TemplateFile;

import java.util.ArrayList;

public class TemplateAdapter extends BaseQuickAdapter<TemplateFile, BaseViewHolder> {

    private boolean isChooseType;

    public TemplateAdapter() {
        super(R.layout.contract_layout_template);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, TemplateFile templateFile) {
        baseViewHolder.setText(R.id.tv_file_name, templateFile.getFile_name());
        LinearLayout llRoot = baseViewHolder.getView(R.id.ll_root);
        if (isChooseType){
            baseViewHolder.setGone(R.id.iv_arrow_right,true);
            baseViewHolder.setGone(R.id.scb_check,false);
            SmoothCheckBox smoothCheckBox = baseViewHolder.getView(R.id.scb_check);
            smoothCheckBox.setChecked(templateFile.isCheck());
        }else {
            baseViewHolder.setGone(R.id.iv_arrow_right,false);
            baseViewHolder.setGone(R.id.scb_check,true);
        }

    }

    public boolean isChooseType() {
        return isChooseType;
    }

    public void setChooseType(boolean chooseType) {
        isChooseType = chooseType;
    }
}
