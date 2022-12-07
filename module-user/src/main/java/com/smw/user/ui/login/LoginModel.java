package com.smw.user.ui.login;

import com.smw.base.model.BaseModel;
import com.smw.base.bean.BaseBean;
import com.smw.common.utils.StringUtils;
import com.smw.user.data.model.UserInfo;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.model.CacheMode;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.config.ApiServices;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
public class LoginModel<T> extends BaseModel<T>
{

    private List<Disposable> disposableList = new ArrayList<>();


    @Override
    protected void load() {

    }

    //注册账号
    public void register(Map<String,String> data) {
        Disposable disposable = EasyHttp.post(ApiServices.User.REGISTER)
                .cacheKey(ApiServices.User.REGISTER)
                .cacheMode(CacheMode.NO_CACHE)
                .params(data)
                .sign(true)
                .execute(new SimpleCallBack<BaseBean>()
                {
                    @Override
                    public void onError(ApiException e)
                    {
                        loadFail(e.getMessage());
                    }

                    @Override
                    public void onSuccess(BaseBean s) {
                        if (s!=null) {
                            loadSuccess((T)s);
                        }
                    }
                });
        disposableList.add(disposable);
    }

    //登录
    public void login(Map<String,String> data) {
        Disposable disposable = EasyHttp.post(ApiServices.User.LOGIN)
                .cacheKey(ApiServices.User.LOGIN) //不能用相同的key，不然可能造成解析错误。
                .cacheMode(CacheMode.NO_CACHE)
                .params(data)
                .sign(true)
                .execute(new SimpleCallBack<UserInfo>()
                {
                    @Override
                    public void onError(ApiException e)
                    {
                        loadFail(e.getMessage());
                    }

                    @Override
                    public void onSuccess(UserInfo s) {
                        if (s!=null&&!StringUtils.isEmpty(s.getToken())) {
                            loadSuccess((T)s);
                        }else {
                            loadFail("登录失败");
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
