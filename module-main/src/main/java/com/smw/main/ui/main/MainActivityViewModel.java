package com.smw.main.ui.main;

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
 * @since 2020-02-20
 */
public class MainActivityViewModel
    extends MvmBaseViewModel<IMainView, AppConfigModel>
    implements IModelListener<BaseBean>

{

    @Override
    protected void initModel() {
        model = new AppConfigModel();
        model.register(this);
    }

    public void load(){
        model.load();
    }


    @Override
    public void onLoadFinish(BaseModel model, BaseBean data) {
        if (getPageView() != null) {
            if (data != null ){
                if (data instanceof AppConfigResult){
                    getPageView().onAppConfigFinish(data);
                }

            }
        }
    }

    @Override
    public void onLoadFail(BaseModel model, String prompt) {

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
