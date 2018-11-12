package com.example.yuanyuanlai.uav.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * @author: Gong Yunhao
 * @version: V1.0
 * @date: 2018/7/26
 * @github https://github.com/Roman-Gong
 * @blog https://www.jianshu.com/u/52a8fa1f29fb
 */
public abstract class BaseActivity extends AppCompatActivity {

    private ActivityManager mActivityManager = ActivityManager.getActivityManager();
    private Activity mthis;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        mthis=this;
        setFullScreen();
        setContentView();
        initView();
        initDate();
        initListener();
        if(null != mActivityManager) {
            mActivityManager.putActivity(this);
        }
    }

    public abstract void setContentView();

    public abstract void initListener();

    public abstract void initView();

    public abstract void initDate();

    private void setFullScreen() {
        View view = getWindow().getDecorView();
        int options = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        view.setSystemUiVisibility(options);
        getWindow().setStatusBarColor( Color.TRANSPARENT);

        //默认为设置黑底白字
        changeStatusBarTextColor( true );
    }

    /**
     * 状态栏白色就设置成深色字体(传入true)
     * 状态栏深色就设置成白色字体(传入false)
     * @param isBlack
     */
    @TargetApi(Build.VERSION_CODES.M)
    public void changeStatusBarTextColor(@NonNull boolean isBlack){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isBlack) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//设置状态栏黑色字体
            }else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);//恢复状态栏白色字体
            }
        }
    }

    @Override
    protected void onDestroy() {
        if(null != mActivityManager) {
            mActivityManager.removeActivity(this);
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume( );
    }

    @Override
    protected void onStop() {
        super.onStop( );
    }

    @Override
    protected void onPause() {
        super.onPause( );
    }

    @Override
    protected void onStart() {
        super.onStart( );
    }

    public Toast toast;
    public void showToast(String text) {
        if (toast == null) {
            toast = Toast.makeText(mthis, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.show();
    }
    public void showToast(int textId) {
        showToast(getString(textId));
    }

}
