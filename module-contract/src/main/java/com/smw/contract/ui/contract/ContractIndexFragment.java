package com.smw.contract.ui.contract;

import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gyf.immersionbar.ImmersionBar;
import com.limpoxe.support.servicemanager.ServiceManager;
import com.smw.base.fragment.MvvmLazyImmersionFragment;
import com.smw.base.utils.ToastUtil;
import com.smw.base.bean.BaseBean;
import com.smw.common.router.RouterFragmentPath;
import com.smw.common.services.ILoginService;
import com.smw.contract.R;

import com.smw.contract.databinding.HomeFragmentHomeBinding;
import java.util.ArrayList;

/**
 * 应用模块: 电子合同
 * <p>
 * 类描述: 电子合同-index fragment
 * <p>
 *
 * @author zeit
 * @since 2020-02-27
 */
@Route(path = RouterFragmentPath.Contract.PAGER_CONTRACT_INDEX)
public class ContractIndexFragment extends MvvmLazyImmersionFragment<HomeFragmentHomeBinding, ContractIndexVM>
        implements
        View.OnClickListener,
        IContractIndexView {

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        initView();
        initData();
    }

    private void initData(){
        viewModel.initModel();
    }

    private void initView() {
        viewDataBinding.tvTest.setOnClickListener(t->{
            Object objc = ServiceManager.getService(ILoginService.LOGIN_SERVICE_NAME);
//            ILoginService loginService =   (ILoginService) ARouter.getInstance().build(ServicesConfig.User.LONGING_SERVICE).navigation();
            if (objc!=null){
                ILoginService loginService = (ILoginService)objc;
                ToastUtil.show(getContext(),loginService.getUserName());
            }
        });
    }


    @Override
    public int getLayoutId()
    {
        return R.layout.home_fragment_home;
    }

    @Override
    public int getBindingVariable()
    {
        return 0;
    }

    @Override
    protected ContractIndexVM getViewModel() {
        return ViewModelProviders.of(this).get(ContractIndexVM.class);
    }

    @Override
    protected void onRetryBtnClick() {

    }


    @Override
    public void onClick(View v) {

    }


    @Override
    public void onDataLoadFinish(ArrayList<? extends BaseBean> viewModels, boolean isEmpty) {


    }

    @Override
    protected void fitsLayoutOverlap() {
        super.fitsLayoutOverlap();
        if (viewDataBinding != null) {
            ImmersionBar.setStatusBarView(this, viewDataBinding.topView);
        } else {
        }
    }

}
