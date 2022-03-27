package com.smw.common.router;

/**
 * 应用模块: 组件化路由
 * <p>
 * 类描述: 用于组件化开发中,ARouter Fragment路径统一注册, 注册的路径请写好注释、标注业务功能
 * <p>
 *
 * @author zeit
 * @since 2020-02-25
 */
public class RouterFragmentPath
{
    
    /** 首页选股组件 */
    public static class Select
    {
        private static final String SELECT = "/select";
        
        /** 选股组件 */
        public static final String PAGER_SELECT = SELECT + "/Stock";
        
    }

    public static class User
    {
        private static final String USER = "/user";
        
        /** 个人中心 */
        public static final String PAGER_USER = USER + "/User";

        /**
         * 登录
         */
        public static final String PAGER_LOGIN = USER + "/Login";

        /**
         * 注册
         */
        public static final String PAGER_REGISTER = USER + "/Register";

        /**
         * 修改密码
         */
        public static final String PAGER_CHANGE_PWD = USER + "/ChangePwd";

        /**
         * 个人信息
         */
        public static final String PAGER_INFO = USER + "/Info";

        /**
         * 修改个人信息
         */
        public static final String PAGER_UPDATE = USER + "/Update";


        /**
         * 通知
         */
        public static final String PAGER_NOTICES = USER + "/Notices";

        /**
         * Vip
         */
        public static final String PAGER_VIP_MAIN = USER + "/VipMain";

    }

    public static class Settings{

        private static final String SETTING = "/Setting";


        /**
         * 设置
         */
        public static final String PAGER_SETTINGS = SETTING + "/Settings";


        /**
         * 账号安全
         */
        public static final String PAGER_SAFE = SETTING + "/Safe";

        /**
         * 反馈
         */
        public static final String PAGER_FED = SETTING + "/Fed";

        /**
         * 关于
         */
        public static final String PAGER_ABOUT = SETTING + "/About";

        /**
         * 用户协议
         */
        public static final String PAGER_WEB = SETTING + "/Web";
    }
    
}
