package com.smw.contract.ui.pdf;

import com.smw.base.bean.DownloadResult;
import com.smw.base.model.BaseModel;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.DownloadProgressCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.utils.HttpLog;

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
public class PdfViewModel<T> extends BaseModel<T>
{

    private List<Disposable> disposableList = new ArrayList<>();


    @Override
    protected void load() {

    }

    public void downloadPdf(String url,String path,String fileName){
        Disposable disposable =  EasyHttp.downLoad(url)
                .savePath(path)
                .saveName(fileName)
                .cacheKey(url)
                .execute(new DownloadProgressCallBack<String>() {
                    @Override
                    public void update(long bytesRead, long contentLength, boolean done) {
                        int progress = (int) (bytesRead * 100 / contentLength);
                        HttpLog.e(progress + "% ");
                        if (done) {
                            //下载完成
                        }else {
                            DownloadResult result = new DownloadResult("-",progress,false);
                            loadSuccess((T)result);
                        }

                    }

                    @Override
                    public void onStart() {
                        //开始下载
                    }

                    @Override
                    public void onComplete(String path) {
                        //下载完成，path：下载文件保存的完整路径
                        DownloadResult result = new DownloadResult(path,100f,true);
                        loadSuccess((T)result);
                    }

                    @Override
                    public void onError(ApiException e) {
                        //下载失败
                        loadFail(e.getMessage());
                    }
                });
        disposableList.add(disposable);
    }


    @Override
    public void cancel() {
        super.cancel();
        for (Disposable item:disposableList){
            EasyHttp.cancelSubscription(item);
        }
    }

}
