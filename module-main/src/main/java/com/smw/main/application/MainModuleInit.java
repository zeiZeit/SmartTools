package com.smw.main.application;

import android.content.Context;

import com.blankj.utilcode.util.Utils;
import com.limpoxe.support.servicemanager.ServiceManager;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;
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
import com.kingja.loadsir.core.LoadSir;
import com.orhanobut.logger.Logger;
import com.smw.common.services.ILoginService;
import com.smw.main.R;
import com.smw.main.interceptor.CustomSignInterceptor;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.converter.GsonDiskConverter;
import com.zhouyou.http.cache.model.CacheMode;
import com.zhouyou.http.config.ApiServices;

/**
 * 应用模块: main
 * <p>
 * 类描述: main组件的业务初始化,其他业务组件所共用功能的初始化
 * <p>
 *
 * @author zeit
 * @since 2020-02-26
 */
public class MainModuleInit implements IModuleInit
{
    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.common_trans, R.color.common_text_main);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @Override
    public boolean onInitAhead(BaseApplication application)
    {
        //初始化manager
        ServiceManager.init(application);
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
                .setCacheMode(CacheMode.FIRSTREMOTE)
                .addInterceptor(new CustomSignInterceptor());
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
