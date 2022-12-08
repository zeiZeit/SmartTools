package com.smw.contract.ui.contract.confirm;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.gyf.immersionbar.ImmersionBar;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.smw.base.activity.IDataLoadView;
import com.smw.base.activity.MvvmBaseActivity;
import com.smw.base.bean.BaseBean;
import com.smw.base.bean.DownloadResult;
import com.smw.base.utils.ToastUtil;
import com.smw.common.global.GlobalKey;
import com.smw.common.router.RouterActivityPath;
import com.smw.common.utils.StringUtils;
import com.smw.contract.R;
import com.smw.contract.databinding.ActivityPdfViewBinding;
import com.smw.contract.ui.pdf.IPdfShowView;
import com.smw.contract.ui.pdf.PdfViewVM;

import java.util.ArrayList;


@Route(path = RouterActivityPath.Contract.PAGER_CONTRACT_CONFIRM)
public class ConfirmActivity extends MvvmBaseActivity<ActivityPdfViewBinding, ConfirmVM> implements IDataLoadView {
    @Autowired(name ="CONTRACT_ID")
    String mContractId;
    @Autowired(name ="FILE_ID")
    String mFileId;
    @Autowired(name = "FILE_URL")
    String pdf_url;

    public static void open(String contractId,String fileUrl,String fileId){
        ARouter.getInstance()
                .build(RouterActivityPath.Contract.PAGER_CONTRACT_CONFIRM)
                .withString("CONTRACT_ID",contractId)
                .withString("FILE_URL",fileUrl)
                .withString("FILE_ID",fileId)
                .navigation();
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
        viewDataBinding.ivBack.setOnClickListener(t->{
            onBackPressed();
        });
        viewModel.initModel();
        if (StringUtils.isEmpty(pdf_url)){
            ToastUtil.show(this,"参数错误！");
            finish();
            return;
        }
        viewDataBinding.flSubmit.setVisibility(View.VISIBLE);
        viewDataBinding.pdfView.setPath(pdf_url);
        viewDataBinding.btnSubmit.setOnClickListener(t->{
            showLoading();
            viewModel.confirmFile(mContractId,mFileId);
        });
    }


    @Override
    protected ConfirmVM getViewModel() {
        return ViewModelProviders.of(this).get(ConfirmVM.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pdf_view;
    }

    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    public void onDataListLoadFinish(ArrayList<? extends BaseBean> viewModels, boolean isEmpty) {

    }

    @Override
    public void onDataLoadFinish(BaseBean viewModel) {
        LiveEventBus
                .get(GlobalKey.Event.CONTRACT_SIGN_FINISH, String.class)
                .post("info_change");
        finish();
    }

    @Override
    public void showFailure(String message) {
        super.showFailure(message);
        ToastUtil.show(this,message);
    }
}