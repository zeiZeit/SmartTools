package com.smw.user.config;


import com.kingja.loadsir.core.LoadSir;
import com.limpoxe.support.servicemanager.ServiceManager;
import com.smw.base.base.BaseApplication;

import com.smw.base.loadsir.EmptyCallback;
import com.smw.base.loadsir.ErrorCallback;
import com.smw.base.loadsir.LoadingCallback;
import com.smw.base.loadsir.TimeoutCallback;
import com.smw.common.IModuleInit;
import com.smw.common.global.GlobalKey;
import com.smw.common.services.ILoginService;
import com.smw.user.data.UserInfoUtil;


/**
 * 应用模块: main
 * <p>
 * 类描述: main组件的业务初始化
 * <p>
 *
 * @author zeit
 * @since 2020-02-26
 */
public class UserModuleInit implements IModuleInit
{
    @Override
    public boolean onInitAhead(BaseApplication application)
    {
        initInfo();
        ServiceManager.publishService(ILoginService.LOGIN_SERVICE_NAME,UserInfoUtil.class.getName(),UserInfoUtil.getInstance().getClass().getClassLoader());
        return false;
    }

    private void initInfo() {
        UserInfoUtil.getInstance();
    }

    @Override
    public boolean onInitLow(BaseApplication application)
    {
        return false;
    }
}
