package com.smw.user.ui.search;

import android.content.Intent;
import android.os.Parcelable;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.gyf.immersionbar.ImmersionBar;
import com.smw.base.activity.MvvmBaseActivity;
import com.smw.base.utils.ToastUtil;
import com.smw.common.adapter.CommonBindingAdapters;
import com.smw.base.bean.BaseBean;
import com.smw.common.contract.SearchUserBean;
import com.smw.common.global.GlobalKey;
import com.smw.common.router.RouterActivityPath;
import com.smw.common.utils.StringUtils;
import com.smw.user.R;
import com.smw.user.databinding.UserActivitySearchBinding;

import java.util.ArrayList;


@Route(path = RouterActivityPath.User.PAGER_USER_SEARCH)
public class UserSearchActivity extends MvvmBaseActivity <UserActivitySearchBinding, UserSearchVM>
        implements IUserSearchView {
    @Autowired(name = "SELECT_USERS")
    ArrayList<SearchUserBean> selectUserBeans;

    public static void open(){
        ARouter.getInstance().build(RouterActivityPath.Contract.PAGER_TEMPLATE_MINE).navigation();
    }

    @Override
    protected UserSearchVM getViewModel() {
        return ViewModelProviders.of(this).get(UserSearchVM.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.user_activity_search;
    }

    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    protected void doBusiness() {
        ImmersionBar.with(this)
                .titleBar(viewDataBinding.llTop)
                .fitsSystemWindows(true)
                .statusBarDarkFont(true)
                .init();
        ARouter.getInstance().inject(this);
        setLoadSir(viewDataBinding.rlRoot);
        viewModel.initModel();
        initView();

    }

    private void initView() {
        viewDataBinding.etSearch.setOnEditorActionListener((v, actionId, event) ->{
            checkSearchUser();
            return false;
        });
        viewDataBinding.ivSearch.setOnClickListener(t->{
            checkSearchUser();
        });
        viewDataBinding.ivBack.setOnClickListener(t->{
            finish();
        });
        viewDataBinding.btnChoose.setOnClickListener(t->{
            Intent intent = new Intent();
            intent.putExtra(GlobalKey.Key.USER_SEARCH_RESULT, (Parcelable) searchResultBean);
            setResult(RESULT_OK,intent);
            finish();
        });
    }

    private void checkSearchUser() {
        String str =  viewDataBinding.etSearch.getText().toString().trim();
        if (StringUtils.isNull(str)){
            ToastUtil.show(this,"请输入手机号");
            return;
        }
        showLoading();
        viewModel.search(str);
    }

    SearchUserBean searchResultBean;

    @Override
    public void onSearchResult(BaseBean viewModel) {
        searchResultBean = (SearchUserBean) viewModel;
        if (searchResultBean ==null||StringUtils.isNull(searchResultBean.getUuid())){
            showEmpty();
            viewDataBinding.rlSearchResult.setVisibility(View.GONE);
        }else {
            showContent();
            viewDataBinding.rlSearchResult.setVisibility(View.VISIBLE);
            viewDataBinding.tvName.setText(searchResultBean.getUser_name());
            CommonBindingAdapters.loadHeadImage(viewDataBinding.ivHead, searchResultBean.getHead_image());
            if (selectUserBeans.contains(searchResultBean)){
                viewDataBinding.btnChoose.setText("已选择");
                viewDataBinding.btnChoose.setEnabled(false);
            }else {
                viewDataBinding.btnChoose.setText("选择");
                viewDataBinding.btnChoose.setEnabled(true);
            }
        }
    }

    @Override
    public void onRequestFailed(String message) {
        showContent();
        ToastUtil.show(this,message);
    }
}