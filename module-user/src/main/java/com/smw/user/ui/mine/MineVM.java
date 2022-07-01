package com.smw.user.ui.mine;

import com.smw.base.model.BaseModel;
import com.smw.base.model.IModelListener;
import com.smw.base.viewmodel.MvmBaseViewModel;
import com.smw.common.contract.BaseBean;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author zeit
 * @since 2020-02-15
 */
public class MineVM extends MvmBaseViewModel<IMineView, MineModel>
    implements IModelListener<BaseBean> {

    @Override
    public void onLoadFinish(BaseModel model,BaseBean data) {
        if (getPageView() != null){
            getPageView().onUserInfoLoadFinish(data);
        }
    }

    @Override
    public void onLoadFail(BaseModel model, String prompt)
    {
        if (getPageView() != null)
        {
            getPageView().showFailure(prompt);
        }
    }

    public void tryToRefresh()
    {
        model.load();
    }

    @Override
    protected void initModel()
    {
        model = new MineModel();
        model.register(this);
        model.getCacheDataAndLoad();
    }

    @Override
    public void detachUi() {
        super.detachUi();
        if (model != null) {
            model.unRegister(this);
        }
    }
}
