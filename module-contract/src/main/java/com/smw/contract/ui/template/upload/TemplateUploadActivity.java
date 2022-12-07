package com.smw.contract.ui.template.upload;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;

import androidx.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import com.gyf.immersionbar.ImmersionBar;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.smw.base.activity.MvvmBaseActivity;
import com.smw.base.utils.ToastUtil;
import com.smw.base.bean.BaseBean;
import com.smw.common.global.GlobalKey;
import com.smw.common.router.RouterActivityPath;
import com.smw.common.utils.StringUtils;
import com.smw.contract.R;
import com.smw.contract.databinding.ContractActivityTemplateUploadBinding;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhouyou.http.body.UIProgressResponseCallBack;
import com.zhouyou.http.subsciber.IProgressDialog;

@Route(path = RouterActivityPath.Contract.PAGER_TEMPLATE_CREATE)
public class TemplateUploadActivity extends MvvmBaseActivity <ContractActivityTemplateUploadBinding, TemplateUploadVM>
        implements ITemplateUploadView {

    public static void open(){
        ARouter.getInstance().build(RouterActivityPath.Contract.PAGER_TEMPLATE_CREATE).navigation();
    }

    @Override
    protected TemplateUploadVM getViewModel() {
        return ViewModelProviders.of(this).get(TemplateUploadVM.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.contract_activity_template_upload;
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
        setLoadSir(viewDataBinding.rlRoot);
        viewModel.initModel();
        initView();
    }

    private void initView() {
        viewDataBinding.tvFileSelect.setOnClickListener(t->{
            final RxPermissions rxPermissions = new RxPermissions(this); // where this is an Activity or Fragment instance
            rxPermissions
                    .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(granted -> {
                        if (granted) {
                            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                            //允许多选 长按多选
                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            //不限制选取类型
                            intent.setType(FileUtils.MimeType.PDF);
                            startActivityForResult(intent, 1);
                        } else {
                        }
                    });

        });
        viewDataBinding.btnSubmit.setOnClickListener(t->{
            checkToSubmit();
        });
        dialog  = new ProgressDialog(TemplateUploadActivity.this);
    }
    ProgressDialog dialog;

    IProgressDialog mProgressDialog = new IProgressDialog() {
        @Override
        public Dialog getDialog() {
            dialog.setMessage("上传中");
            return dialog;
        }
    };

    final UIProgressResponseCallBack listener = new UIProgressResponseCallBack() {
        @Override
        public void onUIResponseProgress(long bytesRead, long contentLength, boolean done) {
            int progress = (int) (bytesRead * 100 / contentLength);
            if (dialog!=null){
                if (done) {//完成
                    dialog.dismiss();
                }else {
                    dialog.setMessage("上传中"+progress+"%");
                }
            }
        }
    };

    private void checkToSubmit() {
        String fileName = viewDataBinding.etFileName.getText().toString();
        if (StringUtils.isNull(fileName.trim())){
            ToastUtil.show(this,"请输入文件名");
            return;
        }
        String filePath = viewDataBinding.tvFileSelect.getText().toString();
        if (StringUtils.isNull(filePath.trim())){
            ToastUtil.show(this,"请选择文件");
            return;
        }
        viewModel.loadTemplate(mProgressDialog,listener,fileName,viewDataBinding.etFileReadTime.getText().toString(),filePath,selectUri);
    }

    Uri selectUri;

    //接收返回值
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    //当单选选了一个文件后返回
                    if (data.getData() != null) {
                        Uri uri = data.getData();
                        selectUri = uri;
                        String filePath = FileUtils.getRealPath(this, uri);
                        viewDataBinding.tvFileSelect.setText(filePath);
                    } else {
                        //多选
                        ClipData clipData = data.getClipData();
                        if (clipData != null) {
                            Uri[] uris = new Uri[clipData.getItemCount()];
                            for (int i = 0; i < clipData.getItemCount(); i++) {
                                uris[i] = clipData.getItemAt(i).getUri();
                            }
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onLoadTemplateResult(BaseBean viewModel) {
        LiveEventBus
                .get(GlobalKey.Event.TEMPLATE_UPLOAD, String.class)
                .post("info_change");
        finish();
    }

    @Override
    public void onUploadFileFailed(String message) {
        showContent();
        ToastUtil.show(this,message);
    }


}