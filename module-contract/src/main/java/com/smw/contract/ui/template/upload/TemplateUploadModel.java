package com.smw.contract.ui.template.upload;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.net.Uri;
import android.util.Log;

import com.smw.base.model.BaseModel;
import com.smw.contract.data.model.TemplateRes;
import com.smw.contract.data.model.UploadSuccessBean;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.body.UIProgressResponseCallBack;
import com.zhouyou.http.cache.model.CacheMode;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.callback.SimpleCallBack;
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
public class TemplateUploadModel<T> extends BaseModel<T>
{

    private List<Disposable> disposableList = new ArrayList<>();



    @Override
    protected void load() {

    }

    public void loadTemplate(IProgressDialog dialog,UIProgressResponseCallBack listener,String fileName,String readTime,String filePath, Uri uri){


        //方式一：文件上传
//        File file = new File(filePath);
        //方式二：InputStream上传
        try {
            final InputStream inputStream = dialog.getDialog().getContext().getContentResolver().openInputStream(uri);
            Disposable disposable = EasyHttp.post(ApiServices.Contract.TEMPLATE_UPLOAD)
                    .cacheKey(ApiServices.Contract.TEMPLATE_UPLOAD)
                    .cacheMode(CacheMode.NO_CACHE)
//                  .params("file", file, file.getName(), listener)
                    .params("file", inputStream, fileName+".pdf", listener)
                    .params("file_name",fileName)
                    .params("read_time",readTime)
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
        } catch (FileNotFoundException e) {
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
