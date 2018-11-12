package com.example.yuanyuanlai.uav.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.yuanyuanlai.uav.R;
import com.example.yuanyuanlai.uav.base.BaseActivity;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText editaccout, editPassword;
    private Button buttonLogin;
    private LinearLayout linearSignIn;
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public void setContentView() {
        setContentView( R.layout.activity_login );
    }

    @Override
    public void initListener() {
        buttonLogin.setOnClickListener(this);
        linearSignIn.setOnClickListener(this);
    }

    @Override
    public void initView() {
        editaccout = findViewById(R.id.et_login_user_name);
        editPassword = findViewById(R.id.et_login_password);
        buttonLogin = findViewById(R.id.btn_login_login);
        linearSignIn = findViewById(R.id.ll_sign_in);
        logo = findViewById(R.id.iv_login_logo);

        Bitmap bmp = ((BitmapDrawable) getResources().getDrawable(
                R.drawable.ic_logo)).getBitmap();
        logo.setImageBitmap(createReflectedImage(bmp));
    }

    @Override
    public void initDate() {

    }

    private boolean checkAccount() {
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_login:
                startActivity(WaterQualityActivity.newInstance(LoginActivity.this));
                //finish();
                break;
            case R.id.ll_sign_in:
                startActivity(SignInActivity.newInstance(LoginActivity.this));
                break;
                default:
                    break;
        }
    }

    public static Bitmap createReflectedImage(Bitmap originalImage) {
        final int reflectionGap = 2;
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
                height / 2, width, height / 2, matrix, false);

        // 下方显示1/3原图
        Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
                (height + height / 3), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(originalImage, 0, 0, null);
        //Paint defaultPaint = new Paint();
        // 分割线,2像素
        //canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);
        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0,
                originalImage.getHeight(), 0, bitmapWithReflection.getHeight()
                + reflectionGap, 0x70ffffff, 0x00ffffff,
                Shader.TileMode.MIRROR);

        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
                + reflectionGap, paint);

        return bitmapWithReflection;
        }
}
