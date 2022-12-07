package com.smw.contract.ui.pdf;

import android.util.Log;

import androidx.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.gyf.immersionbar.ImmersionBar;
import com.smw.base.activity.MvvmBaseActivity;
import com.smw.base.utils.ToastUtil;
import com.smw.base.bean.BaseBean;
import com.smw.common.router.RouterActivityPath;
import com.smw.common.utils.StringUtils;
import com.smw.contract.R;
import com.smw.contract.databinding.ActivityPdfViewBinding;


@Route(path = RouterActivityPath.Contract.PAGER_PDF_VIEW)
public class PdfViewActivity extends MvvmBaseActivity<ActivityPdfViewBinding,PdfViewVM> implements IPdfShowView {

    @Autowired(name = "pdf_url")
    String pdf_url;

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
//        showLoading();
        if (StringUtils.isEmpty(pdf_url)){
            ToastUtil.show(this,"参数错误！");
            finish();
            return;
        }
        viewDataBinding.pdfView.setPath(pdf_url);
//        String path = FileStorageUtil.getFileDir(this).getPath();
//        viewModel.downloadPdf(pdf_url,path,"temp"+System.currentTimeMillis()+".pdf");
    }


    @Override
    protected PdfViewVM getViewModel() {
        return ViewModelProviders.of(this).get(PdfViewVM.class);
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
    public void onLoadResult(BaseBean viewModel) {
        PdfDownloadResult result = (PdfDownloadResult) viewModel;
        if (result.isHasDone()){
            Log.i("zeiZeit", "onLoadResult: "+result.getPath());
        }
    }

    @Override
    public void onRequestFailed(String message) {
        ToastUtil.show(this,message);
    }
}