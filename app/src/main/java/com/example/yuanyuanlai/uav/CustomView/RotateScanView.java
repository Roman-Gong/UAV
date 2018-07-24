package com.example.yuanyuanlai.uav.CustomView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.yuanyuanlai.uav.MyApplication;
import com.example.yuanyuanlai.uav.R;

/**
 * 调用时xml中width和height一样(圆形)
 * width和outer_radius_size属性要一样
 *
 * @author: Gong Yunhao
 * @version: V1.0
 * @date: 2018/7/22
 * @github https://github.com/Roman-Gong
 * @blog https://www.jianshu.com/u/52a8fa1f29fb
 */
public class RotateScanView extends View{

    private final String TAG = "RotateScanView";

    // 默认宽高为80px
    private int mWidth=80,mHeight=80;
    private Paint mPaint = new Paint();
    private Canvas mCanvas;
    // 自定义控件属性
    private int outerRadiusSize = 80;

    // r1外大圈,r2外小圈,r3中间圈,r4小箭头
    private float r1,r2,r3,r4;

    // 旋转数值发生器的数值
    private float rotateValue = 0F;

    // 水质指标级别
    public static final String GREAT = "优";
    public static final String FINE = "良";
    public static final String BAD = "差";
    public static final String CAN_DRINK = "可饮用";
    public static final String CAN_NOT_DRINK = "不可饮";

    // 当前水质级别
    private String currentQuality = FINE;
    private String currentQuality2 = CAN_DRINK;

    @Override
    protected void onDraw(Canvas canvas) {
        mCanvas = canvas;
        drawBackground();
        drawCanDrink();
        drawWaterQuality(currentQuality);
        drawRotateScan();
    }

    // 通过此方法更新级别
    public void setWaterQuality(String quality) {
        currentQuality = quality;
        if (GREAT.equals(quality) || FINE.equals(quality)){
            currentQuality2 = CAN_DRINK;
        }else {
            currentQuality2 = CAN_NOT_DRINK;
        }
        invalidate();
    }

    // 绘制中间的大字，水质级别
    private void drawWaterQuality(String quality) {
        initPaint();
        mCanvas.save();
        mCanvas.translate(0, r4/3);
        mPaint.setTextSize(r3*7/9);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth( 6F );
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor( ContextCompat.getColor(MyApplication.getContext(), R.color.colorWhite));
        mCanvas.drawText(quality, 0, 0, mPaint);
        mCanvas.restore();
    }

    private void drawCanDrink(){
        initPaint();
        mCanvas.save();
        mCanvas.translate(0, r4 * 2.4F);
        // 圆角矩形
        RectF rectF = new RectF(-r4 * 2.2F, -r4 * 0.7F, r4 * 2.2F, r4 * 0.7F);
        mPaint.setStrokeWidth(6F);
        mCanvas.drawRoundRect(rectF, r4, r4, mPaint);
        // 圆角矩形里面的字
        mCanvas.translate(0, r4/3);
        mPaint.setTextSize(r4);
        mPaint.setColor(getResources().getColor(R.color.blue2));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextAlign( Paint.Align.CENTER);
        mCanvas.drawText(currentQuality2, 0, 0, mPaint);
        mCanvas.restore();
    }

    // 绘制不动的圆圈背景
    private void drawBackground() {
        initPaint();
        mCanvas.translate(mWidth/2, mHeight/2);
        mCanvas.save();
        // 中间的圈
        mPaint.setStrokeWidth(2F);
        mCanvas.drawCircle(0, 0, r3, mPaint);
        // 头上的小扇形
        mCanvas.translate(0, -r3 + (r4*2/3));
        RectF rectF = new RectF(-r4, -r4, r4, r4);
        initPaint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth( 1F );
        mCanvas.drawArc(rectF, -70, -40, true, mPaint);
        // 顶上的文字
        String current = "当前水质";
        mCanvas.translate(0, -r4 * 1.5F);
        mPaint.setTextSize( r4 );
        mPaint.setTextAlign(Paint.Align.CENTER);
        mCanvas.drawText(current, 0, 0, mPaint);
        mCanvas.restore();
    }

    private void drawRotateScan() {
        initPaint();
        mPaint.setStrokeWidth(8F);
        mPaint.setColor(Color.BLACK);
        mCanvas.rotate(rotateValue * 360);
        // 外边的圆环
        mPaint.setStrokeWidth(dp2px(r1 - r2));
        int[] colors = new int[]{getResources().getColor( R.color.blue1), getResources().getColor(R.color.blue1), getResources().getColor(R.color.blue1), getResources().getColor(R.color.blue2)};
        SweepGradient sweepGradient = new SweepGradient(0, 0, colors, null);
        mPaint.setShader(sweepGradient);
        mCanvas.drawCircle(0, 0, r2, mPaint);
//        mCanvas.drawLine(r2 - (r1 - r2), 0, r1, 0, mPaint);
    }

    private ValueAnimator valueAnimator;

    private void initAnimator() {
        valueAnimator = ValueAnimator.ofFloat(0F, 1F);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(4 * 1000);
        valueAnimator.addUpdateListener( new ValueAnimator.AnimatorUpdateListener( ) {
            @Override
            public void onAnimationUpdate(ValueAnimator value) {
                rotateValue = (float)value.getAnimatedValue();
                invalidate();
            }
        } );
        valueAnimator.setRepeatCount(-1);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.start();
    }

    public void startAnimation() {
        if (!valueAnimator.isStarted()){
            valueAnimator.start();
            return;
        }
        if (valueAnimator.isPaused()){
            valueAnimator.start();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged( w, h, oldw, oldh );
        // 单位是px
        mWidth = w;
        mHeight = h;
    }

    public RotateScanView(Context context, @Nullable AttributeSet attrs) {
        super( context, attrs );
        initAttrs(context,attrs);
        initPaint();
        initAnimator();
        startAnimation();
    }

    public RotateScanView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super( context, attrs, defStyleAttr );
        initAttrs(context,attrs);
        initPaint();
        initAnimator();
        startAnimation();
    }

    private void initPaint(){
        mPaint.reset();
        mPaint.setColor(getResources().getColor(R.color.lightBlue));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
    }

    private void initAttrs(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RotateScanView);
        outerRadiusSize = typedArray.getDimensionPixelSize(R.styleable.RotateScanView_outer_radius_size, 80);
        // 单位默认是dp
        r1 = outerRadiusSize / 2;
        r2 = r1 * 0.965F;
        r3 = r1 * 3.0F / 5;
        r4 = r1 * 0.12F;
        typedArray.recycle();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow( );
        if (valueAnimator!=null) {
            valueAnimator.cancel();
        }
    }

    /** dp转px **/
    public static int dp2px(float dpValue) {
        Context mContext = MyApplication.getContext();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, mContext.getResources().getDisplayMetrics());
    }

}
