package com.example.yuanyuanlai.uav.util;

import com.example.yuanyuanlai.uav.Bean.UserId;
import com.google.gson.Gson;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author: Gong Yunhao
 * @version: V1.0
 * @date: 2018/7/20
 * @github https://github.com/GongYunHaoyyy
 * @blog https://www.jianshu.com/u/52a8fa1f29fb
 */
public class OkHttpUtils {

    private String baseUrl = "http://api.tlink.io/tlink_interface/api";
    private String APIKey = "a87410496236400c85215c2848e2aafe";
    private String userId = "200003948";

    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");


    private OkHttpUtils() {
        okHttpClient = new OkHttpClient();
    }
    private static volatile OkHttpUtils okHttpUtil;
    private OkHttpClient okHttpClient;

    public static OkHttpUtils getInstance() {
        if (okHttpUtil == null){
            synchronized (OkHttpUtils.class) {
                if (okHttpUtil == null){
                    okHttpUtil = new OkHttpUtils();
                }
            }
        }
        return okHttpUtil;
    }

    public void get(String detailUrl, Callback callback){
        Request request = new Request.Builder()
                .url(baseUrl + detailUrl)
                .get()
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public void post(String detailUrl, Callback callback){
        UserId id = new UserId();
        id.setUserId(userId);
        Gson gson = new Gson();
        String json = gson.toJson(id);
        RequestBody requestBody = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(baseUrl + detailUrl)
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

}
