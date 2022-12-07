package com.smw.contract.ui.contract.list;

import com.smw.base.model.BaseModel;
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
public class ContractLsModel<T> extends BaseModel<T>
{

    private List<Disposable> disposableList = new ArrayList<>();


    @Override
    protected void load() {

    }

    public void loadContractLs(int page, int size){
        Disposable disposable = EasyHttp.post(ApiServices.Contract.CONTRACT_LS)
                .cacheKey(ApiServices.Contract.CONTRACT_LS)
                .cacheMode(CacheMode.NO_CACHE)
                .params("page",String.valueOf(page))
                .params("size",String.valueOf(size))
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
