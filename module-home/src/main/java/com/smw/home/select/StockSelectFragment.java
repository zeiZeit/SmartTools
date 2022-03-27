package com.smw.home.select;

import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gyf.immersionbar.ImmersionBar;
import com.smw.base.fragment.MvvmLazyImmersionFragment;
import com.smw.common.contract.BaseCustomViewModel;
import com.smw.common.router.RouterFragmentPath;
import com.smw.home.R;

import com.smw.home.databinding.HomeFragmentHomeBinding;
import java.util.ArrayList;

/**
 * 应用模块: 选股
 * <p>
 * 类描述: 首页-fragment
 * <p>
 *
 * @author zeit
 * @since 2020-02-27
 */
@Route(path = RouterFragmentPath.Select.PAGER_SELECT)
public class StockSelectFragment extends MvvmLazyImmersionFragment<HomeFragmentHomeBinding, StockSelectViewModel>
        implements
        View.OnClickListener,
        IStockSelectView {
    private int mYear;
    public String mDate;
    public ArrayList<String> mSelectDateLs;


    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        initView();
        initData();
    }

    private void initData(){
        showLoading();
        viewModel.initModel();
    }


    private void initView()
    {

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
    protected StockSelectViewModel getViewModel()
    {

        return ViewModelProviders.of(this).get(StockSelectViewModel.class);
    }

    @Override
    protected void onRetryBtnClick()
    {

    }


    @Override
    public void onClick(View v) {

    }


    @Override
    public void onDataLoadFinish(ArrayList<? extends BaseCustomViewModel> viewModels, boolean isEmpty) {


    }

    @Override
    protected void fitsLayoutOverlap() {
        super.fitsLayoutOverlap();
        if (viewDataBinding != null) {
            ImmersionBar.setStatusBarView(this, viewDataBinding.topView);
        } else {
        }
    }

    @Override
    public void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .navigationBarDarkIcon(true)
                .init();
    }
}
