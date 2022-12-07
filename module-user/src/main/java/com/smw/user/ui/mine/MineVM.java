package com.smw.user.ui.mine;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.smw.base.model.BaseModel;
import com.smw.base.model.IModelListener;
import com.smw.base.viewmodel.MvmBaseViewModel;
import com.smw.base.bean.BaseBean;
import com.smw.common.router.RouterActivityPath;
import com.smw.user.R;
import com.smw.user.data.UserInfoUtil;
import com.smw.user.ui.login.LoginActivity;

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


    public void onClick(View view){
        if (!UserInfoUtil.getInstance().isLogin()){
            LoginActivity.open();
            return;
        }
        if(view.getId() == R.id.cs_user){
            LoginActivity.open();
        }else if (view.getId() == R.id.iv_setting) {
            UserInfoUtil.getInstance().logout();
        }else if (view.getId() == R.id.tv_my_template) {
            ARouter.getInstance().build(RouterActivityPath.Contract.PAGER_TEMPLATE_MINE).navigation();
        }else  if(view.getId() == R.id.tv_create_contract){
            goToCreateContractPage();
        }else if(view.getId() == R.id.tv_my_contract){
            ARouter.getInstance().build(RouterActivityPath.Contract.PAGER_CONTRACT_LIST).navigation();
        }else if(view.getId() == R.id.tv_my_template){

        }
    }

    private void goToCreateContractPage() {
        String uuid = UserInfoUtil.getInstance().getUUID();
        String user_name = UserInfoUtil.getInstance().getUserName();
        String head_image = UserInfoUtil.getInstance().getHeadImage();
        ARouter.getInstance()
                .build(RouterActivityPath.Contract.PAGER_CONTRACT_CREATE)
                .withString("uuid",uuid)
                .withString("user_name",user_name)
                .withString("head_image",head_image)
                .navigation();
    }


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
