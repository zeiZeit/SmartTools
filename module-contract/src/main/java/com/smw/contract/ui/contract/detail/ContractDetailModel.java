package com.smw.contract.ui.contract.detail;

import com.smw.base.model.BaseModel;
import com.smw.contract.data.model.ContractDetailRes;
import com.smw.contract.data.model.ContractListRes;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.model.CacheMode;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.config.ApiServices;
import com.zhouyou.http.exception.ApiException;

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
public class ContractDetailModel<T> extends BaseModel<T>
{

    private List<Disposable> disposableList = new ArrayList<>();


    protected void load(String contractId) {
        Disposable disposable = EasyHttp.post(ApiServices.Contract.CONTRACT_DETAIL)
                .cacheKey(ApiServices.Contract.CONTRACT_DETAIL)
                .cacheMode(CacheMode.NO_CACHE)
                .params("contract_id",contractId)
                .sign(true)
                .accessToken(true)
                .execute(new SimpleCallBack<ContractDetailRes>(){
                    @Override
                    public void onError(ApiException e){
                        loadFail(e.getMessage());
                    }

                    @Override
                    public void onSuccess(ContractDetailRes s) {
                        if (s!=null) {
                            loadSuccess((T)s);
                        }
                    }
                });
        disposableList.add(disposable);
    }


    @Override
    protected void load() {
    }

    @Override
    public void cancel() {
        super.cancel();
        for (Disposable item:disposableList){
            EasyHttp.cancelSubscription(item);
        }
    }

}
