package com.smw.main.ui.main;

import com.smw.base.model.BaseModel;
import com.zhouyou.http.EasyHttp;

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
public class AppConfigModel<T> extends BaseModel<T>
{

    private List<Disposable> disposableList = new ArrayList<>();


    @Override
    protected void load()
    {


    }


    @Override
    public void cancel() {
        super.cancel();
        for (Disposable item:disposableList){
            EasyHttp.cancelSubscription(item);
        }
    }

}
