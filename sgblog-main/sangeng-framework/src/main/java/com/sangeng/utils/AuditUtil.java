package com.sangeng.utils;

import com.baidu.aip.contentcensor.AipContentCensor;
import com.baidu.aip.contentcensor.EImgType;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "baidu")
@Data
/**
 * 百度ai文本审核工具类
 */
public class AuditUtil {
    private String appId;
    private String apiKey;
    private String secretKey;

    /**
     * 文本审核
     * @param content 文本
     * @return
     * @throws JSONException
     */
    public Map<String,String> auditText(String content) throws JSONException {
        // 初始化一个AipContentCensor
        AipContentCensor client = new AipContentCensor(appId, apiKey, secretKey);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
//        System.setProperty("aip.log4j.conf", "D:\\1\\demo\\src\\main\\resources\\log4j.properties");

        // 调用接口
        JSONObject res = client.textCensorUserDefined(content);
        HashMap<String, String> map = new HashMap<String, String>();
        String conclusion = res.get("conclusion").toString();
        if("合规".equals(conclusion)){
            map.put("pass","1");
        }else{
            map.put("pass","0");
            String msg = res.getJSONArray("data").getJSONObject(0).get("msg").toString();
            map.put("msg",msg);
        }
        return map;
    }
    public Map<String,String> auditImage(String url) throws JSONException {
        // 初始化一个AipContentCensor
        AipContentCensor client = new AipContentCensor(appId, apiKey, secretKey);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 调用接口
        JSONObject res = client.imageCensorUserDefined(url, EImgType.URL, null);
        HashMap<String, String> map = new HashMap<String, String>();
        String conclusion = res.get("conclusion").toString();
        if("合规".equals(conclusion)){
            map.put("pass","1");
        }else{
            map.put("pass","0");
            String msg = res.getJSONArray("data").getJSONObject(0).get("msg").toString();
            map.put("msg",msg);
        }
        return map;
    }
}
