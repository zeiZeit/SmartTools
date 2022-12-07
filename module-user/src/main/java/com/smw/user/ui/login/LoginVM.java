package com.smw.user.ui.login;

import com.smw.base.model.BaseModel;
import com.smw.base.model.IModelListener;
import com.smw.base.viewmodel.MvmBaseViewModel;
import com.smw.base.bean.BaseBean;
import com.smw.user.data.model.UserInfo;

import java.util.HashMap;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author zeit
 * @since 2020-02-20
 */
public class LoginVM
    extends MvmBaseViewModel<ILoginView, LoginModel>
    implements IModelListener<BaseBean>

{

    @Override
    protected void initModel() {
        model = new LoginModel();
        model.register(this);
    }

    public void load(){
        model.load();
    }

    public void register(String phone,String pass1,String pass2) {
        HashMap jb = new HashMap();
        jb.put("telephone",phone);
        jb.put("password",pass1);
        jb.put("password2",pass2);
        model.register(jb);
    }


    public void login(String phone, String pass) {
        HashMap jb = new HashMap();
        jb.put("telephone",phone);
        jb.put("password",pass);
        model.login(jb);
    }

    @Override
    public void onLoadFinish(BaseModel model, BaseBean data) {
        if (getPageView() != null) {
            if (data != null ){
                if (data instanceof UserInfo){
                    getPageView().onLoginResult(data);
                }else if (data instanceof BaseBean){
                    getPageView().onRegisterResult(data);
                }
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
