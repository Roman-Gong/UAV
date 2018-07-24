package com.example.yuanyuanlai.uav.util;

import okhttp3.Callback;
import okhttp3.FormBody;
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
public class OkHttpUtil {

    private String baseUrl = "";

    private OkHttpUtil() {
        okHttpClient = new OkHttpClient();
    }
    private static volatile OkHttpUtil okHttpUtil;
    private OkHttpClient okHttpClient;

    public static OkHttpUtil getInstance() {
        if (okHttpUtil == null){
            synchronized (OkHttpUtil.class) {
                if (okHttpUtil == null){
                    okHttpUtil = new OkHttpUtil();
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
        RequestBody requestBody = new FormBody.Builder()
//                .add("userId", id)
                .build();
        Request request = new Request.Builder()
                .url(baseUrl + detailUrl)
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

}
