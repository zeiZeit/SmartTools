package com.smw.contract.ui.contract.create;

import com.smw.base.model.BaseModel;
import com.smw.base.model.IModelListener;
import com.smw.base.viewmodel.MvmBaseViewModel;
import com.smw.base.bean.BaseBean;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author zeit
 * @since 2020-02-20
 */
public class ContractCreateVM
    extends MvmBaseViewModel<IContractCreateView, ContractCreateModel>
    implements IModelListener<BaseBean>

{

    @Override
    protected void initModel() {
        model = new ContractCreateModel();
        model.register(this);

    }

    public void load(){
        model.load();
    }


    public void createContract(String contractName,String users, String files){
        model.createContract(contractName,users,files);
    }

    @Override
    public void onLoadFinish(BaseModel model, BaseBean data) {
        if (getPageView() != null) {
            if (data != null ){
                getPageView().onCreateContractResult(data);
            }
        }
    }

    @Override
    public void onLoadFail(BaseModel model, String prompt) {
        if (getPageView() != null) {
            getPageView().onRequestFailed(prompt);
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
