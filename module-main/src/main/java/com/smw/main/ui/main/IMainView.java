package com.smw.main.ui.main;

import com.smw.base.activity.IBaseView;
import com.smw.base.bean.BaseBean;


/**
 * 应用模块: daily
 * <p>
 * 类描述: UI 更新
 * <p>
 *
 * @author zeit
 * @since 2020-02-14
 */
public interface IMainView extends IBaseView {

    /**
     * 数据加载完成
     *
     * @param viewModel data
     */
    void onAppConfigFinish(BaseBean viewModel);

}
