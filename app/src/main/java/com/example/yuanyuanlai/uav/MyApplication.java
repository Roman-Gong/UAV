package com.example.yuanyuanlai.uav;

import android.app.Application;
import android.content.Context;

/**
 * @author: Gong Yunhao
 * @version: V1.0
 * @date: 2018/7/19
 * @github https://github.com/GongYunHaoyyy
 * @blog https://www.jianshu.com/u/52a8fa1f29fb
 */
public class MyApplication extends Application{

    private static Context mthis;

    @Override
    public void onCreate() {
        super.onCreate( );
        mthis = this;
    }

    public static Context getContext(){
        return mthis;
    }
}
