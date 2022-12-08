package com.smw.contract.ui.contract.confirm;

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
public class ConfirmVM
    extends MvmBaseViewModel<IDataLoadView, ConfirmModel>
    implements IModelListener<BaseBean>

{

    @Override
    protected void initModel() {
        model = new ConfirmModel();
        model.register(this);

    }

    public void load(){
        model.load();
    }


    public void confirmFile( String contactId, String fileId){
        model.confirmFile(contactId,fileId);
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
