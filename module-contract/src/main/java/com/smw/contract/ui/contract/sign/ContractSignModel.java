package com.smw.contract.ui.contract.sign;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.smw.base.bean.BaseBean;
import com.smw.base.bean.DownloadResult;
import com.smw.base.model.BaseModel;
import com.smw.common.utils.FileStorageUtil;
import com.smw.common.views.PPWPDFView;
import com.smw.contract.data.model.UploadSuccessBean;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.body.UIProgressResponseCallBack;
import com.zhouyou.http.cache.model.CacheMode;
import com.zhouyou.http.callback.DownloadProgressCallBack;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.config.ApiServices;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.IProgressDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * 应用模块: daily
 * <p>
 * 类描述: 数据处理层
 * <p>
 *
 * @author zeit
 * @since 2020-02-14
 */
public class ContractSignModel<T> extends BaseModel<T>
{

    private List<Disposable> disposableList = new ArrayList<>();



    @Override
    protected void load() {

    }

    public void downloadFile(String fileDir,String fileName,String url){
        try {
            String destFileDir = fileDir;
            String destFileName = fileName;
            Disposable disposable = EasyHttp.downLoad(url)
                    .savePath(destFileDir)
                    .saveName(destFileName)
                    .cacheKey(url)
                    .execute(new DownloadProgressCallBack<String>() {
                        @Override
                        public void update(long bytesRead, long contentLength, boolean done) {

                        }

                        @Override
                        public void onStart() {
                            //开始下载
                        }

                        @Override
                        public void onComplete(String path) {
                            //下载完成，path：下载文件保存的完整路径
                            DownloadResult downloadResult = new DownloadResult(path,100f,true);
                            loadSuccess((T) downloadResult);
                        }

                        @Override
                        public void onError(ApiException e) {
                            //下载失败
                            loadFail(e.getMessage());
                        }
                    });
            disposableList.add(disposable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void uploadFile(IProgressDialog dialog,UIProgressResponseCallBack listener,String contract_id,String file_id,String filePath){
        //方式一：文件上传
        File file = new File(filePath);
        //方式二：InputStream上传
        try {
            Disposable disposable = EasyHttp.post(ApiServices.Contract.CONTRACT_SIGN)
                    .cacheKey(ApiServices.Contract.CONTRACT_SIGN)
                    .cacheMode(CacheMode.NO_CACHE)
                    .params("contract_id",contract_id)
                    .params("file_id",file_id)
                    .params("file", file, listener)
                    .sign(true)
                    .accessToken(true)
                    .execute(new ProgressDialogCallBack<UploadSuccessBean>(dialog, true, true) {
                        @Override
                        public void onError(ApiException e) {
                            super.onError(e);
                            loadFail(e.getMessage());
                        }

                        @Override
                        public void onSuccess(UploadSuccessBean response) {
                            loadSuccess((T) response);
                        }
                    });
            disposableList.add(disposable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void cancel() {
        super.cancel();
        for (Disposable item:disposableList){
            EasyHttp.cancelSubscription(item);
        }
    }

}
