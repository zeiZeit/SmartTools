package com.smw.user.ui.mine;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.gyf.immersionbar.ImmersionBar;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.smw.base.fragment.MvvmLazyImmersionFragment;
import com.smw.common.contract.BaseBean;
import com.smw.common.global.GlobalKey;
import com.smw.common.router.RouterFragmentPath;
import com.smw.user.R;
import com.smw.user.data.UserInfoUtil;
import com.smw.user.data.model.UserInfo;
import com.smw.user.databinding.UserFragmentMineBinding;
import com.smw.user.ui.login.LoginActivity;


/**
 * 应用模块: 电子合同
 * <p>
 * 类描述: 电子合同-index fragment
 * <p>
 *
 * @author zeit
 * @since 2020-02-27
 */
@Route(path = RouterFragmentPath.User.PAGER_USER)
public class MineFragment extends MvvmLazyImmersionFragment<UserFragmentMineBinding, MineVM>
        implements IMineView {

    UserInfo mUserInfo;

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        initData();
        initView();

    }

    private void initData(){
        viewModel.initModel();
        mUserInfo = UserInfoUtil.getInstance().getUserInfo();
        viewDataBinding.setUser(mUserInfo);
    }

    private void initView() {
        viewDataBinding.csUser.setOnClickListener(t->{
            if (!UserInfoUtil.getInstance().isLogin()){
                LoginActivity.open();
            }
        });
        LiveEventBus
                .get(GlobalKey.USER_INFO_UPDATE, String.class)
                .observeForever(s -> {
                    mUserInfo = UserInfoUtil.getInstance().getUserInfo();
                    viewDataBinding.setUser(mUserInfo);
                    viewDataBinding.executePendingBindings();
                });
    }

    @Override
    public int getLayoutId()
    {
        return R.layout.user_fragment_mine;
    }

    @Override
    public int getBindingVariable()
    {
        return 0;
    }

    @Override
    protected MineVM getViewModel() {
        return ViewModelProviders.of(this).get(MineVM.class);
    }

    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    public void onUserInfoLoadFinish(BaseBean viewModel) {

    }

    @Override
    protected void fitsLayoutOverlap() {
        super.fitsLayoutOverlap();
        if (viewDataBinding != null) {
            ImmersionBar.setTitleBar(this, viewDataBinding.llTop);
        }
    }

}
