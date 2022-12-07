package com.smw.user.ui.search;

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
public interface IUserSearchView extends IBaseView {

    void onSearchResult(BaseBean viewModel);
    void onRequestFailed(String message);
}
