package com.smw.contract.ui.contract.create;


import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.smw.contract.R;
import com.smw.contract.data.model.TemplateFile;

public class TemplateAddAdapter extends BaseQuickAdapter<TemplateFile, BaseViewHolder> {

    public TemplateAddAdapter() {
        super(R.layout.contract_layout_template_add);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, TemplateFile file) {
        baseViewHolder.setText(R.id.tv_file_name,file.getFile_name());
    }
}
