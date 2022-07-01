package com.smw.user.ui.login;

import com.smw.base.activity.IBaseView;
import com.smw.common.contract.BaseBean;


/**
 * 应用模块: daily
 * <p>
 * 类描述: UI 更新
 * <p>
 *
 * @author zeit
 * @since 2020-02-14
 */
public interface ILoginView extends IBaseView {

    void onLoginResult(BaseBean viewModel);
    void onRegisterResult(BaseBean viewModel);
    void onRequestFailed(String message);
}
