package com.smw.user.ui.search;

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
public class UserSearchVM
    extends MvmBaseViewModel<IUserSearchView, UserSearchModel>
    implements IModelListener<BaseBean>

{

    @Override
    protected void initModel() {
        model = new UserSearchModel();
        model.register(this);

    }

    public void load(){
        model.load();
    }


    public void search(String s){
        model.search(s);
    }

    @Override
    public void onLoadFinish(BaseModel model, BaseBean data) {
        if (getPageView() != null) {
            if (data != null ){
                getPageView().onSearchResult(data);
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
