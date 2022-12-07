package com.smw.common.global;

/**
 * 应用模块: 全局配置
 * <p>
 * 类描述: 全局的存储key,单个组件的可以单个组件自行在各自的组件中定义
 * <p>
 *
 * @author zeit
 * @since 2020-02-25
 */
public class GlobalKey
{
    public interface Event{
        /** 用户信息 */
        String USER_INFO_UPDATE = "user_info_update";

        /** 上传了新的合同模板 */
        String TEMPLATE_UPLOAD = "template_upload_success";
    }

    public interface Key {

        String USER_SEARCH_RESULT = "USER_SEARCH_RESULT";
        String TEMPLATE_SELECTED_RESULT = "USER_SEARCH_RESULT";
    }
}
