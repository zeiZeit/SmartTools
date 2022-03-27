package com.smw.main.application;

import com.blankj.utilcode.util.Utils;
import com.smw.base.base.BaseApplication;
import com.smw.base.loadsir.EmptyCallback;
import com.smw.base.loadsir.ErrorCallback;
import com.smw.base.loadsir.LoadingCallback;
import com.smw.base.loadsir.TimeoutCallback;
import com.smw.base.storage.MmkvHelper;
import com.smw.base.utils.SerializeUtil;
import com.smw.common.IModuleInit;
import com.smw.common.adapter.ScreenAutoAdapter;
import com.smw.common.contract.AppConfig;
import com.smw.common.contract.StocksInfo;
import com.kingja.loadsir.core.LoadSir;
import com.orhanobut.logger.Logger;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.converter.GsonDiskConverter;
import com.zhouyou.http.cache.model.CacheMode;
import com.zhouyou.http.config.ApiServices;

/**
 * 应用模块: main
 * <p>
 * 类描述: main组件的业务初始化
 * <p>
 *
 * @author zeit
 * @since 2020-02-26
 */
public class MainModuleInit implements IModuleInit
{
    @Override
    public boolean onInitAhead(BaseApplication application)
    {
        ScreenAutoAdapter.setup(application);
        EasyHttp.init(application);
        if (application.issDebug())
        {
            EasyHttp.getInstance().debug("easyhttp", true);
        }
        EasyHttp.getInstance()
            .setBaseUrl(ApiServices.BASE_URL)
            .setReadTimeOut(15 * 1000)
            .setWriteTimeOut(15 * 1000)
            .setConnectTimeout(15 * 1000)
            .setRetryCount(3)
            .setCacheDiskConverter(new GsonDiskConverter())
            .setCacheMode(CacheMode.FIRSTREMOTE);
        LoadSir.beginBuilder()
            .addCallback(new ErrorCallback())
            .addCallback(new LoadingCallback())
            .addCallback(new EmptyCallback())
            .addCallback(new TimeoutCallback())
            .setDefaultCallback(LoadingCallback.class)
            .commit();
        Utils.init(application);
        Logger.i("main组件初始化完成 -- onInitAhead");

        initInfo();
        return false;
    }

    private void initInfo() {
        String str = MmkvHelper.getInstance().getMmkv().decodeString("appConfig");
        try {
            if (str !=null){
                AppConfig config = (AppConfig) SerializeUtil.serializeToObject(str);
                AppConfig.init(config);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean onInitLow(BaseApplication application)
    {
        return false;
    }
}
