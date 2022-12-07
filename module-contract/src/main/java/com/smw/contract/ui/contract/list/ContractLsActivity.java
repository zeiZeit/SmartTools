package com.smw.contract.ui.contract.list;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cy.necessaryview.shapeview.RecShapeTextView;
import com.gyf.immersionbar.ImmersionBar;
import com.smw.base.activity.IDataLoadView;
import com.smw.base.activity.MvvmBaseActivity;
import com.smw.base.bean.BaseBean;
import com.smw.base.utils.ToastUtil;
import com.smw.common.adapter.CommonBindingAdapters;
import com.smw.common.global.GlobalKey;
import com.smw.common.router.RouterActivityPath;
import com.smw.common.views.SmoothCheckBox;
import com.smw.contract.R;
import com.smw.contract.data.model.ContractListItem;
import com.smw.contract.data.model.ContractListRes;
import com.smw.contract.data.model.TemplateFile;
import com.smw.contract.databinding.ContractActivityLsBinding;
import com.smw.contract.ui.contract.create.ContractCreateActivity;
import com.smw.contract.ui.contract.detail.ContractDetailActivity;

import java.util.ArrayList;

@Route(path = RouterActivityPath.Contract.PAGER_CONTRACT_LIST)
public class ContractLsActivity extends MvvmBaseActivity <ContractActivityLsBinding, ContractLsVM>
        implements IDataLoadView {
    @Autowired(name ="IS_CHOOSE")
    boolean isChoose;
    @Autowired(name ="SELECTED_FILES")
    ArrayList<TemplateFile> selectedFiles;

    public static void open(){
        ARouter.getInstance().build(RouterActivityPath.Contract.PAGER_CONTRACT_LIST).navigation();
    }

    @Override
    protected ContractLsVM getViewModel() {
        return ViewModelProviders.of(this).get(ContractLsVM.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.contract_activity_ls;
    }

    @Override
    protected void onRetryBtnClick() {

    }

    private int mCurrentPage = 1;
    private int mTotal;

    @Override
    protected void doBusiness() {
        ImmersionBar.with(this)
                .titleBar(viewDataBinding.llTop)
                .fitsSystemWindows(true)
                .statusBarDarkFont(true)
                .init();
        ARouter.getInstance().inject(this);
        initRecycleView();
        setLoadSir(viewDataBinding.rlRoot);
        viewModel.initModel();
        showLoading();
        viewDataBinding.flSubmit.setVisibility(isChoose?View.VISIBLE:View.GONE);
        viewDataBinding.btnSubmit.setOnClickListener(t->{
            if (!isChoose){
                return;
            }
            Intent intent = new Intent();
            intent.putExtra(GlobalKey.Key.TEMPLATE_SELECTED_RESULT,selectedFiles);
            setResult(555,intent);
            finish();
        });

        viewModel.loadTemplate(mCurrentPage,20);
    }


    ContractLsAdapter mAdapter;
    private void initRecycleView() {
        mAdapter = new ContractLsAdapter();
        mAdapter.setChooseType(isChoose);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            ContractListItem file = mAdapter.getItem(position);
            ContractDetailActivity.open(file.getContract_id());
        });
        viewDataBinding.rvTemplate.setAdapter(mAdapter);
        viewDataBinding.rvTemplate.setLayoutManager(new LinearLayoutManager(this));
        viewDataBinding.rvTemplate.setNestedScrollingEnabled(false);
        viewDataBinding.smRefresh.setEnableLoadMore(true);
        viewDataBinding.smRefresh.setEnableRefresh(true);
        viewDataBinding.smRefresh.setOnRefreshListener(refreshLayout -> {
            mCurrentPage = 1;
            if (mAdapter!=null){
                mAdapter.getData().clear();
            }
            viewModel.loadTemplate(mCurrentPage,20);
        });
        viewDataBinding.smRefresh.setOnLoadMoreListener(refreshLayout -> {
            viewModel.loadTemplate(mCurrentPage+1,20);
        });
        viewDataBinding.tvUpload.setOnClickListener(t->{
            ContractCreateActivity.open();
        });
    }

    @Override
    public void onDataListLoadFinish(ArrayList<? extends BaseBean> viewModels, boolean isEmpty) {

    }

    @Override
    public void onDataLoadFinish(BaseBean viewModel) {
        viewDataBinding.smRefresh.finishLoadMore();
        viewDataBinding.smRefresh.finishRefresh();
        ArrayList<ContractListItem> data = ((ContractListRes)viewModel).getItems();
        if (data!=null){
            mAdapter.addData(data);
            mCurrentPage = ((ContractListRes)viewModel).getCurrent_page();
        }
        if (mAdapter.getData().size()==0){
            showEmpty();
        }else {
            mAdapter.notifyDataSetChanged();
            showContent();
        }
    }

    @Override
    public void showFailure(String message) {
        super.showFailure(message);
        showContent();
        viewDataBinding.smRefresh.finishLoadMore();
        viewDataBinding.smRefresh.finishRefresh();
        ToastUtil.show(this,message);
    }

    public class ContractLsAdapter extends BaseQuickAdapter<ContractListItem, BaseViewHolder> {

        private boolean isChooseType;

        public ContractLsAdapter() {
            super(R.layout.contract_layout_ls_contract);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder baseViewHolder, ContractListItem item) {
            CommonBindingAdapters.loadHeadImage(baseViewHolder.getView(R.id.iv_head),item.getCreate_user_head());
            baseViewHolder.setText(R.id.tv_contract_name,item.getContract_name());
            baseViewHolder.setText(R.id.tv_creator,item.getCreate_user());
            baseViewHolder.setText(R.id.tv_create_time,item.getCreate_time());
            baseViewHolder.setText(R.id.tv_update_time,item.getUpdate_time());
            RecShapeTextView tvStatus = baseViewHolder.getView(R.id.tv_status);

        }

        public boolean isChooseType() {
            return isChooseType;
        }

        public void setChooseType(boolean chooseType) {
            isChooseType = chooseType;
        }
    }
}