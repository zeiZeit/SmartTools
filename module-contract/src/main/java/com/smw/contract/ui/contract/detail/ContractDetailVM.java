package com.smw.contract.ui.contract.detail;

import com.smw.base.activity.IDataLoadView;
import com.smw.base.bean.BaseBean;
import com.smw.base.model.BaseModel;
import com.smw.base.model.IModelListener;
import com.smw.base.viewmodel.MvmBaseViewModel;
import com.smw.contract.ui.contract.list.ContractLsModel;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author zeit
 * @since 2020-02-20
 */
public class ContractDetailVM
    extends MvmBaseViewModel<IDataLoadView, ContractDetailModel>
    implements IModelListener<BaseBean>
{

    @Override
    protected void initModel() {
        model = new ContractDetailModel();
        model.register(this);
    }

    public void load(String contractId){
        model.load(contractId);
    }


    @Override
    public void onLoadFinish(BaseModel model, BaseBean data) {
        if (getPageView() != null) {
            if (data != null ){
                getPageView().onDataLoadFinish(data);
            }
        }
    }

    @Override
    public void onLoadFail(BaseModel model, String prompt) {
        if (getPageView() != null) {
            getPageView().showFailure(prompt);
        }
    }

    @Override
    public void detachUi()
    {
        super.detachUi();
        if (model != null)
        {
            model.unRegister(this);
        }
    }


}
