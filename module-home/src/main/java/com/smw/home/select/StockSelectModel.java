package com.smw.home.select;

import com.smw.base.model.BaseModel;
import com.smw.common.contract.BaseCustomViewModel;

import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.config.ApiServices;
import com.zhouyou.http.exception.ApiException;

import org.json.JSONException;
import org.json.JSONObject;

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
public class StockSelectModel<T> extends BaseModel<T>
{

    private Disposable disposable;


    @Override
    protected void load()
    {
        EasyHttp.getInstance().setBaseUrl(ApiServices.BASE_URL);
        disposable = EasyHttp.get(ApiServices.PATH_STRATEGY)
                .cacheKey(getClass().getSimpleName())
                .execute(new SimpleCallBack<String>()
                {
                    @Override
                    public void onError(ApiException e)
                  {
                      loadFail(e.getMessage());
                  }

                    @Override
                    public void onSuccess(String s)
                  {
                      parseJson(s);
                  }
                });
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


    /**
     * 解析json 数据
     *
     * @param s json字符串
     */
    private void parseJson(String s)
    {
        List<BaseCustomViewModel> viewModels = new ArrayList<>();
        JSONObject jsonObject = null;
        try
        {
            jsonObject = new JSONObject(s);
            JSONObject jb = jsonObject.optJSONObject("data");
            if (viewModels.size()>0)
            {
                loadSuccess((T)viewModels);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

    }

    
}
