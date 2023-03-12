package com.sangeng.utils;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TextProcessUtil {
    public static final String API_KEY = "mrWuwE22Ig88AmgsvmfG48Rb";
    public static final String SECRET_KEY = "DGrAd9CijHabmg7uSlhqjMItd907cf4a";

    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    public static String keyWords(String text) throws IOException, JSONException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType,"\"text:\"[\"" + text + "\"]");
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rpc/2.0/nlp/v1/txt_keywords_extraction?access_token=" + getAccessToken())
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        Map<String,String> map = (Map<String, String>) JSON.parse(response.body().string());
        return map.get("results");
    }

    public static String getSummary(String text, String title) throws IOException, JSONException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"content\":\"+"+text+"+\",\"max_summary_len\":200,\"title\":\""+title+"\"}");
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rpc/2.0/nlp/v1/news_summary?charset=UTF-8&access_token=" + getAccessToken())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        Map<String,String> map = (Map<String, String>) JSON.parse(response.body().string());
        System.out.println(map.get("summary"));
        return map.get("summary");
    }
    public static void main(String[] args) throws JSONException, IOException {



    }

    /**
     * 从用户的AK，SK生成鉴权签名（Access Token）
     *
     * @return 鉴权签名（Access Token）
     * @throws IOException IO异常
     */
    static String getAccessToken() throws IOException, JSONException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=" + API_KEY
                + "&client_secret=" + SECRET_KEY);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        return new JSONObject(response.body().string()).getString("access_token");
    }
}
