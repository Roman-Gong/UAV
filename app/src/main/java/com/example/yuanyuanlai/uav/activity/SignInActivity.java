package com.example.yuanyuanlai.uav.activity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuanyuanlai.uav.R;
import com.example.yuanyuanlai.uav.base.BaseActivity;

public class SignInActivity extends BaseActivity implements View.OnClickListener {
    private Button sendButton;
    private TextView countDown;
    private EditText phoneEditText, verificationCodeEditText;
    private ValueAnimator animator;
    private static final String TAG = "SignInActivity";

    public static Intent newInstance(Context context) {
        Intent intent = new Intent(context, SignInActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public void setContentView() {
        setContentView( R.layout.activity_sign_in );
    }

    @Override
    public void initListener() {
        sendButton.setOnClickListener(this);
    }

    @Override
    public void initView() {
        sendButton = findViewById( R.id.sendButton );
        countDown = findViewById( R.id.countDown );
        phoneEditText = findViewById(R.id.phone_number);
        verificationCodeEditText = findViewById(R.id.verification_code);
    }

    @Override
    public void initDate() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sendButton:
                if (!phoneEditText.getText().toString().isEmpty()){
                    sendButton.setEnabled( false );
                    //确认网络请求发送后
                    animator = ValueAnimator.ofInt(59, 0);
                    animator.setDuration(60 * 1000);
                    animator.setInterpolator(new LinearInterpolator());
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            int value = (int)valueAnimator.getAnimatedValue();
                            countDown.setText(String.valueOf(value));
                            if (value<1){
                                initButton();
                            }
                        }
                    });
                    animator.start();

                    // 此处开始请求验证码
                }else{
                    Toast.makeText(this, "邮箱为空!", Toast.LENGTH_SHORT).show();
                }
                break;
                default:
                    break;
        }
    }

    private void initButton(){
        sendButton.setEnabled( true );
        countDown.setText( "时间" );
    }
}
