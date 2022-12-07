package com.smw.contract.ui.template;

import android.content.Intent;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.gyf.immersionbar.ImmersionBar;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.smw.base.activity.MvvmBaseActivity;
import com.smw.base.utils.ToastUtil;
import com.smw.base.bean.BaseBean;
import com.smw.common.global.GlobalKey;
import com.smw.common.router.RouterActivityPath;
import com.smw.contract.R;
import com.smw.contract.data.model.TemplateRes;
import com.smw.contract.data.model.TemplateFile;
import com.smw.contract.databinding.ContractActivityMyTemplateBinding;
import com.smw.contract.ui.template.upload.TemplateUploadActivity;

import java.util.ArrayList;

@Route(path = RouterActivityPath.Contract.PAGER_TEMPLATE_MINE)
public class TemplateActivity extends MvvmBaseActivity <ContractActivityMyTemplateBinding, TemplateVM>
        implements ITemplateView {
    @Autowired(name ="IS_CHOOSE")
    boolean isChoose;
    @Autowired(name ="SELECTED_FILES")
    ArrayList<TemplateFile> selectedFiles;

    public static void open(){
        ARouter.getInstance().build(RouterActivityPath.Contract.PAGER_TEMPLATE_MINE).navigation();
    }

    @Override
    protected TemplateVM getViewModel() {
        return ViewModelProviders.of(this).get(TemplateVM.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.contract_activity_my_template;
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


    TemplateAdapter mAdapter;
    private void initRecycleView() {
        mAdapter = new TemplateAdapter();
        mAdapter.setChooseType(isChoose);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (!isChoose){
                ARouter.getInstance()
                        .build(RouterActivityPath.Contract.PAGER_PDF_VIEW)
                        .withString("pdf_url",((TemplateFile)adapter.getItem(position)).getFile_url())
                        .navigation();
                return;
            }
            TemplateFile file = mAdapter.getItem(position);
            if (selectedFiles.contains(file)){
                selectedFiles.remove(file);
                file.setCheck(false);
            }else {
                file.setCheck(true);
                selectedFiles.add(file);
            }
            mAdapter.notifyDataSetChanged();
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
            TemplateUploadActivity.open();
        });
        LiveEventBus
                .get(GlobalKey.Event.TEMPLATE_UPLOAD, String.class)
                .observeForever(s -> {
                    mCurrentPage = 1;
                    if (mAdapter!=null){
                        mAdapter.getData().clear();
                    }
                    viewModel.loadTemplate(mCurrentPage,20);
                });
    }


    @Override
    public void onLoadTemplateResult(BaseBean viewModel) {
        viewDataBinding.smRefresh.finishLoadMore();
        viewDataBinding.smRefresh.finishRefresh();
        ArrayList<TemplateFile> data = ((TemplateRes)viewModel).getItems();
        if (data!=null){
            if (isChoose){
                for (TemplateFile file:data){
                    if (selectedFiles.contains(file)){
                        file.setCheck(true);
                    }
                }
            }
            mAdapter.addData(data);
            mCurrentPage = ((TemplateRes)viewModel).getCurrent_page();
        }
        if (mAdapter.getData().size()==0){
            showEmpty();
        }else {
            mAdapter.notifyDataSetChanged();
            showContent();
        }
    }


    @Override
    public void onRequestFailed(String message) {
        showContent();
        viewDataBinding.smRefresh.finishLoadMore();
        viewDataBinding.smRefresh.finishRefresh();
        ToastUtil.show(this,message);
    }
}