package com.smw.contract.select;

import com.smw.base.model.BaseModel;

import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.config.ApiServices;
import com.zhouyou.http.exception.ApiException;

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
public class ContractIndexModel<T> extends BaseModel<T>
{

    private Disposable disposable;

    @Override
    protected void load()
    {
        EasyHttp.getInstance().setBaseUrl(ApiServices.BASE_URL);
//        disposable = EasyHttp.get(ApiServices.)
//                .cacheKey(getClass().getSimpleName())
//                .execute(new SimpleCallBack<String>()
//                {
//                    @Override
//                    public void onError(ApiException e)
//                  {
//                      loadFail(e.getMessage());
//                  }
//
//                    @Override
//                    public void onSuccess(String s)
//                  {
//
//                  }
//                });
    }

    @Override
    public void cancel() {
        super.cancel();
        EasyHttp.cancelSubscription(disposable);
    }


    public void refresh()
    {
        load();
    }




    
}
