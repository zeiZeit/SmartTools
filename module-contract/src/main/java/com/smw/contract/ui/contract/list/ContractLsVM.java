package com.smw.contract.ui.contract.list;

import com.smw.base.activity.IDataLoadView;
import com.smw.base.bean.BaseBean;
import com.smw.base.model.BaseModel;
import com.smw.base.model.IModelListener;
import com.smw.base.viewmodel.MvmBaseViewModel;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author zeit
 * @since 2020-02-20
 */
public class ContractLsVM
    extends MvmBaseViewModel<IDataLoadView, ContractLsModel>
    implements IModelListener<BaseBean>
{

    @Override
    protected void initModel() {
        model = new ContractLsModel();
        model.register(this);

    }

    public void load(){
        model.load();
    }


    public void loadTemplate(int page,int size){
        model.loadContractLs(page,size);
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
