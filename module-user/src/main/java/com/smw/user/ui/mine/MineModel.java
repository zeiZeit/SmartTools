package com.smw.user.ui.mine;

import com.smw.base.model.BaseModel;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.config.ApiServices;
import com.zhouyou.http.exception.ApiException;

import io.reactivex.disposables.Disposable;

/**
 * 应用模块: user
 * <p>
 * 类描述: 数据处理层
 * <p>
 *
 * @author zeit
 * @since 2020-02-14
 */
public class MineModel<T> extends BaseModel<T> {

    private Disposable disposable;


    @Override
    protected void load() {
        EasyHttp.getInstance().setBaseUrl(ApiServices.BASE_URL);
//        disposable = EasyHttp.get(ApiServices.PATH_STRATEGY)
//                .cacheKey(getClass().getSimpleName())
//                .execute(new SimpleCallBack<String>(){
//                    @Override
//                    public void onError(ApiException e) {
//                      loadFail(e.getMessage());
//                    }
//
//                    @Override
//                    public void onSuccess(String s) {
//                    }
//                });
    }

    @Override
    public void cancel() {
        super.cancel();
        EasyHttp.cancelSubscription(disposable);
    }


    public void refresh() {
        load();
    }

}
