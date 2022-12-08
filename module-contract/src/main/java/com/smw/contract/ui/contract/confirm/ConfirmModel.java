package com.smw.contract.ui.contract.confirm;

import com.smw.base.bean.DownloadResult;
import com.smw.base.model.BaseModel;
import com.smw.contract.data.model.ContractListRes;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.model.CacheMode;
import com.zhouyou.http.callback.DownloadProgressCallBack;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.config.ApiServices;
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
public class ConfirmModel<T> extends BaseModel<T>
{

    private List<Disposable> disposableList = new ArrayList<>();


    @Override
    protected void load() {

    }
    public void confirmFile( String contactId, String fileId){
        Disposable disposable = EasyHttp.post(ApiServices.Contract.CONTRACT_CONFIRM)
                .cacheKey(ApiServices.Contract.CONTRACT_CONFIRM)
                .cacheMode(CacheMode.NO_CACHE)
                .params("contract_id",contactId)
                .params("file_id",fileId)
                .sign(true)
                .accessToken(true)
                .execute(new SimpleCallBack<ContractListRes>()
                {
                    @Override
                    public void onError(ApiException e)
                    {
                        loadFail(e.getMessage());
                    }

                    @Override
                    public void onSuccess(ContractListRes s) {
                        if (s!=null) {
                            loadSuccess((T)s);
                        }
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
