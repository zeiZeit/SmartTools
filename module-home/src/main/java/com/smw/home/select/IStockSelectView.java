package com.smw.home.select;

import com.smw.base.activity.IBaseView;
import com.smw.common.contract.BaseCustomViewModel;

import java.util.ArrayList;


/**
 * 应用模块: daily
 * <p>
 * 类描述: UI 更新
 * <p>
 *
 * @author zeit
 * @since 2020-02-14
 */
public interface IStockSelectView extends IBaseView {

    /**
     * 数据加载完成
     *
     * @param viewModels data
     */
    void onDataLoadFinish(ArrayList<? extends BaseCustomViewModel> viewModels,
                          boolean isEmpty);

}
