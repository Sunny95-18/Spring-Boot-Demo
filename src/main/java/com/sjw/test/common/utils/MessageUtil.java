package com.sjw.test.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.sjw.test.common.exceptions.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发送短信功能
 * @author Sunny
 * @version 1.0
 * @date 2020/4/28 14:00
 */
@Slf4j
public class MessageUtil {

    //短信apikey
    private static final String API_KEY="";
    //模板id
    private static final String CODE_TEMP_ID="";
    //云片url
    private static final String URL="https://sms.yunpian.com/v2/sms/tpl_single_send.json";
    //编码格式。发送编码格式统一用UTF-8
    private static String ENCODING = "UTF-8";
    /*
     *
     * @author Sunny
     * @description 发送短信验证码
     * @date 2019/6/27 10:37
     * @param  * @param mobile
     * @return java.lang.String
     * @version 1.0
     */
    public static String sendMessageByCode(String mobile,String codeValue){

        //短信apiKey
        String apiKey = API_KEY;
        //验证码
        //String codeValue = RandomStringUtils.random(4, false, true);
        //短信验证码模板ID
        //String messageId = (SysConfigUtils.get(MessageCodeConfig.class)).getMessageCodeTemplateId();
        String messageId=CODE_TEMP_ID;
        //发送短信API
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apiKey);
        params.put("mobile", mobile);
        params.put("tpl_id", messageId);
        params.put("tpl_value", URLEncoder.encode("#code#") + "=" + URLEncoder.encode(codeValue));
        log.info("apikey:{},mobile:{},messageId:{}",apiKey,mobile,messageId);
        String result=post(URL, params);
        checkSmsResponse(result);
        log.info("{}验证码发送结束",mobile);
        return codeValue;
    }
    /*
     *
     * @author Sunny
     * @description 基于HttpClient 4.3的通用POST方法
     * @date 2019/6/27 10:49
     * @param  * @param url
     * @param paramsMap
     * @return java.lang.String
     * @version 1.0
     */
    private static String post(String url, Map<String, String> paramsMap) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            if (paramsMap != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList, ENCODING));
            }
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity, ENCODING) + "";

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseText.replace("\\", "");
    }
    private static void checkSmsResponse(String result) {
        JSONObject jsonObject = JSONObject.parseObject(result);
        log.info("checkSmsResponse response: {}", jsonObject.toJSONString());
        Integer code = jsonObject.getInteger("code");
        if (!Integer.valueOf(0).equals(code)) {
            Integer http_status_code = jsonObject.getInteger("http_status_code");
            if (http_status_code == null) {
                http_status_code = Integer.valueOf(500);
            }

            String msg = jsonObject.getString("msg");
            if (StringUtils.isBlank(msg)) {
                msg = "the third party error";
            }
            throw new SystemException(Long.parseLong(http_status_code.toString()), msg);
        }
    }
}
