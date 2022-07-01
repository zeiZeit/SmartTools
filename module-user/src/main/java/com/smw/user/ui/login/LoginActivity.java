package com.smw.user.ui.login;

import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.gyf.immersionbar.ImmersionBar;
import com.smw.base.activity.MvvmBaseActivity;
import com.smw.base.utils.ToastUtil;
import com.smw.base.viewmodel.IMvvmBaseViewModel;
import com.smw.common.contract.BaseBean;
import com.smw.common.router.RouterFragmentPath;
import com.smw.common.utils.StringUtils;
import com.smw.common.utils.Validator;
import com.smw.user.R;
import com.smw.user.data.UserInfoUtil;
import com.smw.user.data.model.UserInfo;
import com.smw.user.databinding.UserActivityLoginBinding;


@Route(path = RouterFragmentPath.User.PAGER_LOGIN)
public class LoginActivity extends MvvmBaseActivity <UserActivityLoginBinding, LoginVM>
        implements ILoginView {

    public static void open(){
        ARouter.getInstance().build(RouterFragmentPath.User.PAGER_LOGIN).navigation();
    }

    @Override
    protected LoginVM getViewModel() {
        return ViewModelProviders.of(this).get(LoginVM.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.user_activity_login;
    }

    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    protected void doBusiness() {
        ImmersionBar.with(this)
                .titleBar(viewDataBinding.rlRoot)
                .fitsSystemWindows(true)
                .autoDarkModeEnable(true)
                .init();
        setLoadSir(viewDataBinding.rlRoot);
        showContent();
        viewModel.initModel();

    }

    public void onClick(View view){
        if (view.getId() == R.id.tv_forget_pass) {

        }else if(view.getId() == R.id.tv_to_register){
            viewDataBinding.llLoginArea.setVisibility(View.GONE);
            viewDataBinding.llRegisterArea.setVisibility(View.VISIBLE);
        }else if(view.getId() == R.id.btn_login){
            checkToLogin();
        }else if(view.getId() == R.id.tv_to_login){
            viewDataBinding.llLoginArea.setVisibility(View.VISIBLE);
            viewDataBinding.llRegisterArea.setVisibility(View.GONE);
        }else if(view.getId() == R.id.btn_register){
            checkToRegister();
        }
    }

    private void checkToRegister() {
        String phone = viewDataBinding.etRegisterPhone.getText().toString().trim();
        String pass1 = viewDataBinding.etRegisterPass1.getText().toString();
        String pass2 = viewDataBinding.etRegisterPass2.getText().toString();
        if (!Validator.isMobile(phone)){
            ToastUtil.show(this,"手机号格式不对");
            return;
        }
        if (StringUtils.isEmpty(pass1)){
            ToastUtil.show(this,"请输入密码");
            return;
        }
        if (StringUtils.isEmpty(pass2)){
            ToastUtil.show(this,"请输入确认密码");
            return;
        }
        if (!pass1.equals(pass2)){
            ToastUtil.show(this,"两次输入密码不一致");
            return;
        }
        showLoading();
        viewModel.register(phone,pass1,pass2);
    }

    private void checkToLogin() {
        String phone = viewDataBinding.etLoginPhone.getText().toString().trim();
        String pass = viewDataBinding.etLoginPass.getText().toString();
        if (!Validator.isMobile(phone)){
            ToastUtil.show(this,"手机号格式不对");
            return;
        }
        if (StringUtils.isEmpty(pass)){
            ToastUtil.show(this,"请输入密码");
            return;
        }
        showLoading();
        viewModel.login(phone,pass);
    }


    @Override
    public void onLoginResult(BaseBean viewModel) {
        showContent();
        ToastUtil.show(this,"登录成功");
        UserInfoUtil.getInstance().updateUserInfo((UserInfo) viewModel);
        finish();
    }

    @Override
    public void onRegisterResult(BaseBean viewModel) {
        showContent();
        ToastUtil.show(this,"注册成功，请登录");
        viewDataBinding.llLoginArea.setVisibility(View.VISIBLE);
        viewDataBinding.llRegisterArea.setVisibility(View.GONE);
        viewDataBinding.etLoginPhone.setText(viewDataBinding.etRegisterPhone.getText().toString());
    }

    @Override
    public void onRequestFailed(String message) {
        showContent();
        ToastUtil.show(this,message);
    }
}