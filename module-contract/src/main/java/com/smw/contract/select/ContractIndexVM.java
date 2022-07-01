package com.smw.contract.select;

import com.smw.base.model.BaseModel;
import com.smw.base.model.IModelListener;
import com.smw.base.viewmodel.MvmBaseViewModel;
import com.smw.common.contract.BaseBean;

import java.util.ArrayList;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author zeit
 * @since 2020-02-15
 */
public class ContractIndexVM extends MvmBaseViewModel<IContractIndexView, ContractIndexModel>
    implements IModelListener<ArrayList<BaseBean>> {

    @Override
    public void onLoadFinish(BaseModel model,
                             ArrayList<BaseBean> data)
    {
        if (getPageView() != null)
        {
            if (data != null && data.size() > 0)
            {
                getPageView().onDataLoadFinish(data, false);
            }
            else
            {
                getPageView().showEmpty();
            }
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
        model = new ContractIndexModel();
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
