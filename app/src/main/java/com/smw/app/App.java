package com.smw.app;

import com.smw.base.BuildConfig;
import com.smw.base.base.BaseApplication;

import com.smw.common.config.ModuleLifecycleConfig;

/**
 * 应用模块: 宿主app
 * <p>
 * 类描述: 宿主app的 application ,在这里通过common组件中定义的组件生命周期配置类进行相应初始化(通过反射调用各个组件的初始化器)
 * <p>
 *
 * @author zeit
 * @since 2020-02-26
 */
public class App extends BaseApplication
{
    private App mApp;
    @Override
    public void onCreate()
    {
        super.onCreate();
        setsDebug(BuildConfig.DEBUG);
        // 初始化需要初始化的组件
        ModuleLifecycleConfig.getInstance().initModuleAhead(this);
        mApp = this;

    }

    public App getApp() {
        return mApp;
    }
}
