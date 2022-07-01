package com.smw.main.interceptor;

import com.limpoxe.support.servicemanager.ServiceManager;
import com.smw.common.services.ILoginService;
import com.smw.common.utils.Base64;
import com.zhouyou.http.interceptor.BaseDynamicInterceptor;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>描述：对参数进行签名、添加token、时间戳处理的拦截器</p>
 * 主要功能说明：<br>
 * 因为参数签名没办法统一，签名的规则不一样，签名加密的方式也不同有MD5、BASE64等等，只提供自己能够扩展的能力。<br>
 * 作者： zhouyou<br>
 * 日期： 2017/5/4 15:21 <br>
 * 版本： v1.0<br>
 */
public class CustomSignInterceptor extends BaseDynamicInterceptor<CustomSignInterceptor> {
    @Override
    public TreeMap<String, String> dynamic(TreeMap<String, String> dynamicMap) {
        //dynamicMap:是原有的全局参数+局部参数
        //你不必关心当前是get/post/上传文件/混合上传等，库中会自动帮你处理。
        //根据需要自己处理，如果你只用到token则不必处理isTimeStamp()、isSign()
        TreeMap<String, String> newMap = new TreeMap<>();
        if (isSign()) {
            //1.因为你的字段key可能不是sign，这种需要动态的自己处理
            //2.因为你的签名的规则不一样，签名加密方式也不一样，只提供自己能够扩展的能力
            newMap.put("data", sign(dynamicMap));
            if (needOldParam()){
                newMap.putAll(dynamicMap);
            }
        }
        if (isTimeStamp()) {//是否添加时间戳，因为你的字段key可能不是timestamp,这种动态的自己处理
            newMap.put("time", String.valueOf(System.currentTimeMillis()));
        }
        if (isAccessToken()) {//是否添加token
            ILoginService loginService = (ILoginService) ServiceManager.getService(ILoginService.LOGIN_SERVICE_NAME);
            newMap.put("token", loginService.getToken());
            newMap.put("uuid", loginService.getUUID());
        }
        return newMap;//dynamicMap:是原有的全局参数+局部参数+新增的动态参数
    }

    //示例->签名规则：POST+url+参数的拼装+secret
    private String sign(TreeMap<String, String> dynamicMap) {
        JSONObject jb = new JSONObject();
        try {
        for (Map.Entry<String, String> entry : dynamicMap.entrySet()) {
                jb.put(entry.getKey(),entry.getValue());
        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Base64.encode(jb.toString().getBytes(StandardCharsets.UTF_8));
    }
}