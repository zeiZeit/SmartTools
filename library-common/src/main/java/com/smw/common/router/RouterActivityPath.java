package com.smw.common.router;

/**
 * 应用模块: 组件化路由
 * <p>
 * 类描述: 用于在组件化开发中,利用ARouter 进行Activity跳转的统一路径注册, 注册时请写好注释、标注界面功能业务
 * <p>
 *
 * @author zeit
 * @since 2020-02-25
 */
public class RouterActivityPath
{
    /**
     * main组件
     */
    public static class Main
    {
        private static final String MAIN = "/main";
        
        /** 主页面 */
        public static final String PAGER_MAIN = MAIN + "/Main";
    }
    

    public static class User
    {
        private static final String USER = "/user";
        
        /** 登录界面 */
        public static final String PAGER_LOGIN = USER + "/Login";

        /** 关注页面 */
        public static final String PAGER_ATTENTION = USER + "/attention";


        /** 用户搜索界面 */
        public static final String PAGER_USER_SEARCH = USER + "/UserSearch";
    }

    public static class Contract
    {
        private static final String CONTRACT = "/Contract";

        /** 我的合同模板界面 */
        public static final String PAGER_TEMPLATE_MINE = CONTRACT + "/TemplateMine";

        /** 合同模板上传界面 */
        public static final String PAGER_TEMPLATE_CREATE = CONTRACT + "/TemplateCreate";

        /** 合同创建界面 */
        public static final String PAGER_CONTRACT_CREATE = CONTRACT + "/ContractCreate";

        /** Pdf浏览页面 */
        public static final String PAGER_PDF_VIEW = CONTRACT + "/PdfView";

        /** 我的合同列表 */
        public static final String PAGER_CONTRACT_LIST = CONTRACT + "/ContractLs";
        /** 合同详情页 */
        public static final String PAGER_CONTRACT_DETAIL = CONTRACT + "/ContractDetail";
        /** 合同签字页 */
        public static final String PAGER_CONTRACT_SIGN = CONTRACT + "/ContractSign";
        /** 合同签字页 */
        public static final String PAGER_CONTRACT_CONFIRM = CONTRACT + "/ContractConfirm";
    }

}
