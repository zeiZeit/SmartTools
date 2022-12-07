package com.smw.user.ui.mine;

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
public interface IMineView extends IBaseView {

    /**
     * 数据加载完成
     *
     * @param baseBean data
     */
    void onUserInfoLoadFinish(BaseBean baseBean);

}
