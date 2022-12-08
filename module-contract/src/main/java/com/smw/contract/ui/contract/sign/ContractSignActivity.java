package com.smw.contract.ui.contract.sign;

import static com.smw.contract.ui.contract.sign.ContractSignActivity.TopBarMode.Delete;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.view.View;
import android.widget.SeekBar;

import androidx.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.artifex.mupdfdemo.FilePicker;
import com.artifex.mupdfdemo.Hit;
import com.artifex.mupdfdemo.MuPDFCore;
import com.artifex.mupdfdemo.MuPDFPageAdapter;
import com.artifex.mupdfdemo.MuPDFReaderView;
import com.artifex.mupdfdemo.MuPDFView;
import com.gyf.immersionbar.ImmersionBar;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.smw.base.activity.MvvmBaseActivity;
import com.smw.base.bean.BaseBean;
import com.smw.base.bean.DownloadResult;
import com.smw.base.utils.ToastUtil;
import com.smw.common.global.GlobalKey;
import com.smw.common.router.RouterActivityPath;
import com.smw.common.utils.FileStorageUtil;
import com.smw.contract.R;
import com.smw.contract.data.model.TemplateFile;
import com.smw.contract.databinding.ContractActivitySignBinding;
import com.smw.contract.ui.pdf.PdfViewActivity;
import com.smw.contract.ui.template.upload.TemplateUploadActivity;
import com.zhouyou.http.body.UIProgressResponseCallBack;
import com.zhouyou.http.subsciber.IProgressDialog;


import java.util.ArrayList;

@Route(path = RouterActivityPath.Contract.PAGER_CONTRACT_SIGN)
public class ContractSignActivity extends MvvmBaseActivity <ContractActivitySignBinding, ContractSignVM>
        implements IContractSignView, FilePicker.FilePickerSupport {
    @Autowired(name ="CONTRACT_ID")
    String mContractId;
    @Autowired(name ="FILE_URL")
    String mFileUrl;
    @Autowired(name ="FILE_ID")
    String mFileId;

    String mFilePath;


    enum TopBarMode {
        Main, Search, Annot, Delete, More, Accept
    }
    private TopBarMode mTopBarMode = TopBarMode.Main;
    private MuPDFCore core;
    private String mFileName;
    private MuPDFReaderView mDocView;

    public static void open(String contractId,String fileUrl,String fileId){
        ARouter.getInstance()
                .build(RouterActivityPath.Contract.PAGER_CONTRACT_SIGN)
                .withString("CONTRACT_ID",contractId)
                .withString("FILE_URL",fileUrl)
                .withString("FILE_ID",fileId)
                .navigation();
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
    @Override
    protected ContractSignVM getViewModel() {
        return ViewModelProviders.of(this).get(ContractSignVM.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.contract_activity_sign;
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
        viewDataBinding.ivBack.setOnClickListener(t->{
            onBackPressed();
        });
        setLoadSir(viewDataBinding.rlRoot);
        viewModel.initModel();
        showLoading();
        dialog  = new ProgressDialog(ContractSignActivity.this);
        viewModel.downLoadFile(FileStorageUtil.getFileDir(this).toString(),FileStorageUtil.getFileNameOnUrl(mFileUrl), mFileUrl);

    }

    private MuPDFCore openFile(String path) {
        int lastSlashPos = path.lastIndexOf('/');
        mFileName = new String(lastSlashPos == -1
                ? path
                : path.substring(lastSlashPos + 1));
        System.out.println("Trying to open " + path);
        try {
            core = new MuPDFCore(this, path);
            // New file: drop the old outline data
//            OutlineActivityData.set(null);
        } catch (Exception e) {
            return null;
        } catch (java.lang.OutOfMemoryError e) {
            //  out of memory is not an Exception, so we catch it separately.
            return null;
        }
        return core;
    }

    private void initData(String localPath) {
        openFile(localPath);
    }


    private void initView() {
        mDocView = new MuPDFReaderView(this) {
            @Override
            protected void onMoveToChild(int i) {
                if (core == null)
                    return;
                viewDataBinding.pageNumber.setText((i+1)+"/"+ core.countPages());
                viewDataBinding.pageSlider.setMax(core.countPages());
                viewDataBinding.pageSlider.setProgress(i+1);
                super.onMoveToChild(i);
            }

            @Override
            protected void onTapMainDocArea() {
            }

            @Override
            protected void onDocMotion() {

            }

            @Override
            protected void onHit(Hit item) {
                switch (mTopBarMode) {
                    case Annot:
                        if (item == Hit.Annotation) {
                            mTopBarMode = Delete;
                        }
                        break;
                    case Delete:
                        mTopBarMode = TopBarMode.Annot;
                    default:
                        MuPDFView pageView = (MuPDFView) mDocView.getDisplayedView();
                        if (pageView != null)
                            pageView.deselectAnnotation();
                        break;
                }
            }
        };
        mDocView.setAdapter(new MuPDFPageAdapter(ContractSignActivity.this, (FilePicker.FilePickerSupport) this,core));
        // Activate the seekbar
        viewDataBinding.pageSlider.setProgress(1);
        viewDataBinding.pageNumber.setText("1/"+ core.countPages());
        viewDataBinding.pageSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {
                mDocView.setDisplayedViewIndex(seekBar.getProgress());
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
            }
        });
        viewDataBinding.flPdf.addView(mDocView);

        viewDataBinding.btnSign.setOnClickListener(t->{
            //去签名 或者 保存签名
            if (isSignModel){
                //保存签名

                MuPDFView pageView = (MuPDFView) mDocView.getDisplayedView();
                boolean success = false;
                if (pageView != null)
                    success = pageView.saveDraw();
                mTopBarMode = TopBarMode.Annot;
                if (!success)
                    ToastUtil.show(this,"保存失败");
                switchSignView(false);
            }else {
                //去签名
                switchSignView(true);

            }
        });
        viewDataBinding.btnSignCancel.setOnClickListener(t->{
            //取消签名
            MuPDFView pageView = (MuPDFView) mDocView.getDisplayedView();
            if (pageView != null) {
                pageView.cancelDraw();
            }
            switchSignView(false);
        });
        viewDataBinding.btnSubmit.setOnClickListener(t->{
            core.save();
            viewModel.uploadFile(mProgressDialog,listener,mContractId,mFileId,mFilePath);
        });
    }

    private void switchSignView(boolean sign){
        if (sign){
            isSignModel=true;
            viewDataBinding.btnSignCancel.setVisibility(View.VISIBLE);
            viewDataBinding.btnSign.setText("保存");
            mDocView.setMode(MuPDFReaderView.Mode.Drawing);
        }else {
            viewDataBinding.btnSignCancel.setVisibility(View.GONE);
            viewDataBinding.btnSign.setText("签名");
            isSignModel=false;
            mDocView.setMode(MuPDFReaderView.Mode.Viewing);
        }
    }

    private boolean isSignModel;//是否时签名模式

    @Override
    public void onFileDownloadFinish(DownloadResult viewModel) {
        showContent();
        mFilePath = viewModel.getPath();
        initData(mFilePath);
        initView();
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
        ToastUtil.show(this,message);
    }
    @Override
    public void performPickFor(FilePicker picker) {

    }
}