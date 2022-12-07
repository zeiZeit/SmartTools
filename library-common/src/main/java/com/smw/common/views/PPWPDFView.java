package com.smw.common.views;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.cy.necessaryview.shapeview.RecShapeTextView;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.smw.base.loadsir.ErrorCallback;
import com.smw.base.loadsir.LoadingCallback;
import com.smw.base.utils.ToastUtil;
import com.smw.common.R;
import com.smw.common.utils.FileStorageUtil;
import com.smw.common.utils.StringUtils;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.DownloadProgressCallBack;
import com.zhouyou.http.exception.ApiException;

import java.io.File;

import io.reactivex.disposables.Disposable;

public class PPWPDFView extends FrameLayout {
    protected final String TAG = this.getClass().getSimpleName();
    protected String DOWNLOAD_TAG = "";
    ProgressBar mPbLoading;
    PDFView mPDFView;
    RecShapeTextView mRtvPage;
    String mPageUrl;
    private boolean isBeginDownload = false;
    private boolean isCompleteFileDownLoad = false;
    int totalPage = -1;
    LoadService loadService;
    public PPWPDFView(Context context) {
        super(context);
        initView(context, null);
    }

    public PPWPDFView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public PPWPDFView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    /**
     * 初始化UI
     */
    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_ppw_pdf, this, true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mPbLoading = findViewById(R.id.pb_loading);
        mPDFView = findViewById(R.id.pdfView);
        mRtvPage = findViewById(R.id.tv_page);
        loadService = LoadSir.getDefault().register(findViewById(R.id.ppw_pdf_vew_fl_root), new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {

            }
        });

    }

    /**
     * @param path 设置path
     */
    public void setPath(String path) {
        if (StringUtils.isEmpty(path)) {
            if (mClickListener != null) mClickListener.stateErrorLogic();
            stateError();
            ToastUtil.show(getContext(),"文件不存在");
            return;
        }
        if (mClickListener != null) mClickListener.stateLoadingLogic();
        stateLoading();
        mPageUrl = path;
        if (path.contains("http")) {
            downLoadFileAndOpen();
        } else {
            displayLocalPdfFile(FileUtils.isFile(path)?false:true,mPageUrl);
        }
    }


    private void displayLocalPdfFile(boolean isAsset,String localPath) {
        if (mPDFView == null) return;
        if (mClickListener != null)  mClickListener.stateNormalLogic();
        stateNormal();
        PDFView.Configurator configurator = isAsset?mPDFView.fromAsset(localPath):mPDFView.fromFile(new File(localPath));
        configurator
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .onPageChange(new OnPageChangeListener() {
                    @Override
                    public void onPageChanged(int page, int pageCount) {
                        totalPage = pageCount;
                        mRtvPage.setText(String.valueOf(page + 1) + "/" + String.valueOf(pageCount));
                        if (page + 2 >= pageCount) {
                            if (mClickListener != null) mClickListener.slideToBottom(true);
                        } else {
                            if (mClickListener != null) mClickListener.slideToBottom(false);
                        }
                    }
                })
                .onPageScroll(new OnPageScrollListener() {
                    @Override
                    public void onPageScrolled(int page, float positionOffset) {
                    }
                })
                .enableAnnotationRendering(true) // render annotations (such as comments, colors or forms)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                .spacing(0)
                .onError(new OnErrorListener() {
                    @Override
                    public void onError(Throwable t) {
                        ToastUtil.show(getContext(),"缓存文件出错，请重新打开界面。");
                        boolean deleteFile = FileStorageUtil.deleteFile(getPicLocalPath(mPageUrl));
//                        LogUtils.d(TAG, "deleteFile:" + deleteFile);
                        if (mClickListener != null) mClickListener.pdfOpenError(t);
                    }
                })
                .load();
    }
    Disposable disposable;
    /**
     * 下载网络文件
     */
    private void downLoadFileAndOpen() {
        //检查本地是否有该文件
        if (FileStorageUtil.judeFileExists(getPicLocalPath(mPageUrl))) {
//            LogUtils.d(TAG, "judeFileExists:true");
            isCompleteFileDownLoad = true;
            mPbLoading.setVisibility(View.GONE);
            File file = new File(getFileDir(), getFileName(mPageUrl));
            displayLocalPdfFile(false,file.getAbsolutePath());
            return;
        }
//        LogUtils.d(TAG, "judeFileExists:false");
        String destFileDir = getFileDir();
        String destFileName = getFileName(mPageUrl);
        String filePath = destFileDir + File.separator + destFileName;
        disposable = EasyHttp.downLoad(mPageUrl)
                .savePath(destFileDir)
                .saveName(destFileName)
                .cacheKey(mPageUrl)
                .execute(new DownloadProgressCallBack<String>() {
                    @Override
                    public void update(long bytesRead, long contentLength, boolean done) {
                        Context context = PPWPDFView.this.getContext();
                        if(context==null)return;
                        if(context!=null&&context instanceof Activity&&(((Activity) context).isFinishing()||((Activity) context).isDestroyed()))return;
                        float floatPercent = (int) (bytesRead / contentLength);
                        int percent = (int) (floatPercent * 100);
                        if (mPbLoading == null) return;
                        if (done) {
                            mPbLoading.setVisibility(View.GONE);
                            return;
                        }
                        mPbLoading.setVisibility(View.VISIBLE);
                        mPbLoading.setProgress(percent);
                    }

                    @Override
                    public void onStart() {
                        //开始下载
                        isBeginDownload = true;
                    }

                    @Override
                    public void onComplete(String path) {
                        //下载完成，path：下载文件保存的完整路径
                        Context context = PPWPDFView.this.getContext();
                        if(context==null)return;
                        if(context!=null&&context instanceof Activity&&(((Activity) context).isFinishing()||((Activity) context).isDestroyed()))return;
                        isCompleteFileDownLoad = true;
                        if (mPbLoading != null) {
                            mPbLoading.setVisibility(View.GONE);
                        }
                        displayLocalPdfFile(false,path);
                    }

                    @Override
                    public void onError(ApiException e) {
                        //下载失败
                        Context context = PPWPDFView.this.getContext();
                        if(context==null)return;
                        if(context!=null&&context instanceof Activity&&(((Activity) context).isFinishing()||((Activity) context).isDestroyed()))return;
                        stateError();
                        if (mClickListener != null) mClickListener.stateErrorLogic();
                        isCompleteFileDownLoad = false;
                    }
                });
    }

    private String getPicLocalPath(String url) {
//        LogUtils.d(TAG, " getPicLocalPath:" + getFileDir() + File.separator + getFileName(url));
        return getFileDir() + File.separator + getFileName(url);
    }

    private String getFileDir() {
        return FileStorageUtil.getFileDir(getContext()).toString();
//         UIUtil.getContext().getCacheDir().toString();
    }

    private String getFileName(String url) {
        return "smc_"+url.hashCode() + ".pdf";
    }

    private ViewListener mClickListener;

    public void setOnViewClickListener(ViewListener listener) {
        this.mClickListener = listener;
    }

    public interface ViewListener {

        void slideToBottom(boolean isSlideToBottom);

        void pdfOpenError(Throwable t);

        default void stateErrorLogic() {
        }

        default void stateNormalLogic() {
        }

        default void stateLoadingLogic() {
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        LogUtils.d(TAG, "DOWNLOAD_TAG:" + DOWNLOAD_TAG);
        if(!TextUtils.isEmpty(DOWNLOAD_TAG)&&disposable!=null){
            EasyHttp.cancelSubscription(disposable);
        }
        super.onDetachedFromWindow();
        if (isBeginDownload && !isCompleteFileDownLoad) {
            boolean deleteFile = FileStorageUtil.deleteFile(getPicLocalPath(mPageUrl));
            LogUtils.d(TAG, "deleteFile:" + deleteFile);
        }
    }

    private void stateError() {
        if (loadService != null) {
            loadService.showCallback(ErrorCallback.class);
        }
    }

    private void stateLoading() {
        if (loadService != null) {
            loadService.showCallback(LoadingCallback.class);

        }
    }

    private void stateNormal() {
        if (null != loadService) {
            loadService.showSuccess();
        }
    }
}
