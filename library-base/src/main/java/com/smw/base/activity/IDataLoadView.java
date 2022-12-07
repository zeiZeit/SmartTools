package com.smw.base.activity;

import com.smw.base.bean.BaseBean;

import java.util.ArrayList;

public interface IDataLoadView extends IBaseView{
    /**
     * 数据加载完成 列表
     * @param viewModels data
     */
    void onDataListLoadFinish(ArrayList<? extends BaseBean> viewModels, boolean isEmpty);

    /**
     * 数据加载完成 单个
     * @param viewModel data
     */
    void onDataLoadFinish(BaseBean viewModel);
}
