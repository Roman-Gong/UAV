package com.example.yuanyuanlai.uav;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.example.yuanyuanlai.uav.Util.WindowUtil;

public class StaticView extends View {

    private Context mContext;
    private int mWidth;
    private int mHeight;
    private Paint mPaint;
    private Paint mVerticalTextPaint;
    private Paint mHorizontalTextPaint;
    private Paint mBarPaint;
    private String TAG = "StaticView";
    private int[] mColorGradient = new int[12];
    private int[] mShader = new int[3];
    private float percent = 0;

    public StaticView(Context context) {
        super(context);
        mContext = context;
        initPaint();
    }

    public StaticView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initPaint();
    }

    public StaticView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initPaint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "" + w);
        Log.d(TAG, "" + h);
        mWidth = w;
        mHeight = h;
    }

    private void initPaint() {

        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.colorAlphaWhite, null));
        mPaint.setStrokeWidth(2.0f);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mVerticalTextPaint = new Paint();
        mVerticalTextPaint.setColor(getResources().getColor(R.color.colorVerticalWhite, null));
        mVerticalTextPaint.setAntiAlias(true);
        mVerticalTextPaint.setTextSize(50.0f);
        mBarPaint = new Paint();
//        mBarPaint.setColor(getResources().getColor(R.color.colorBar, null));
        mBarPaint.setAntiAlias(true);
        mBarPaint.setStyle(Paint.Style.FILL);
        mColorGradient[11] = getResources().getColor(R.color.colorStart_3, null);
        mColorGradient[10] = getResources().getColor(R.color.colorCenter_3, null);
        mColorGradient[9] = getResources().getColor(R.color.colorEnd_3, null);
        mColorGradient[8] = getResources().getColor(R.color.colorStart_2, null);
        mColorGradient[7] = getResources().getColor(R.color.colorCenter_2, null);
        mColorGradient[6] = getResources().getColor(R.color.colorEnd_2, null);
        mColorGradient[5] = getResources().getColor(R.color.colorStart_1, null);
        mColorGradient[4] = getResources().getColor(R.color.colorCenter_1, null);
        mColorGradient[3] = getResources().getColor(R.color.colorEnd_1, null);
        mColorGradient[2] = getResources().getColor(R.color.colorStart, null);
        mColorGradient[1] = getResources().getColor(R.color.colorCenter, null);
        mColorGradient[0] = getResources().getColor(R.color.colorEnd, null);
        mHorizontalTextPaint = new Paint();
        mHorizontalTextPaint.setColor(getResources().getColor(R.color.colorHorizontalWhite, null));
        mHorizontalTextPaint.setAntiAlias(true);
        mHorizontalTextPaint.setTextSize(50.0f);
        mHorizontalTextPaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        changeCoordinateSystem(canvas);
        drawTransverseLine(canvas);
        drawVertical(canvas);
        drawHorizontal(canvas);
        drawBar(canvas, percent);
    }

    private void  changeCoordinateSystem(Canvas canvas) {

        canvas.translate(mWidth/2, mHeight/2);

    }

    private void drawTransverseLine(Canvas canvas) {

        int lineMarginLeft = WindowUtil.dip2px(mContext, 45);
        int lineMarginRight = WindowUtil.dip2px(mContext, 10);

        for (int i=0; i<11; i++) {
            canvas.drawLine(-mWidth/2+lineMarginLeft, 2*mHeight/5-2*i*mHeight/25, mWidth/2-lineMarginRight, 2*mHeight/5-2*i*mHeight/25, mPaint);
        }

    }

    private String generateDigit(int i) {

        String temp = String.valueOf(i);
        int len = temp.length();
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append(temp);
        for (int j=0; j<(4-len); j++) {
            resultBuilder.insert(0, "  ");
        }
        return resultBuilder.toString();

    }

    private String getColor(int color) {

        String result = Integer.toHexString(color);
        result = "#" + result;
        return result;

    }

    private void drawSingleBar(Canvas canvas, int []shaders, int left, int top, int right, int bottom, boolean isFirst, boolean isReverse) {

        if (!isFirst)
            canvas.translate(100, 0);
        if (isReverse) {
            int temp[] = new int[3];
            temp[0] = shaders[2];
            temp[1] = shaders[1];
            temp[2] = shaders[0];
            shaders = temp;
        }
        Shader shader = new LinearGradient(0, top, 0, bottom, shaders, null, Shader.TileMode.CLAMP);
        mBarPaint.setShader(shader);
        RectF rectF = new RectF(left, top, right, bottom);
        canvas.drawRoundRect(rectF, 25, 25, mBarPaint);

    }

    private void drawBar(Canvas canvas, float percent) {

        int firstBarMarginLeft = WindowUtil.dip2px(mContext, 55);
        canvas.save();
        mShader[0] = mColorGradient[0];
        mShader[1] = mColorGradient[1];
        mShader[2] = mColorGradient[2];
        drawSingleBar(canvas, mShader, firstBarMarginLeft-mWidth/2, (int)(percent*(-mHeight/2+150)), firstBarMarginLeft-mWidth/2 + 50, -10, true, false);
        drawSingleBar(canvas, mShader, firstBarMarginLeft-mWidth/2, (int)(percent*(-mHeight/2+250)), firstBarMarginLeft-mWidth/2 + 50, -10, false, false);
        drawSingleBar(canvas, mShader, firstBarMarginLeft-mWidth/2, (int)(percent*(-mHeight/2+350)), firstBarMarginLeft-mWidth/2 + 50, -10, false, false);
        mShader[0] = mColorGradient[3];
        mShader[1] = mColorGradient[4];
        mShader[2] = mColorGradient[5];
        drawSingleBar(canvas, mShader, firstBarMarginLeft-mWidth/2, (int)(percent*(-mHeight/4)), firstBarMarginLeft-mWidth/2 + 50, -10, false, false);
        drawSingleBar(canvas, mShader, firstBarMarginLeft-mWidth/2, (int)(percent*(-mHeight/2+300)), firstBarMarginLeft-mWidth/2 + 50, -10, false, false);
        drawSingleBar(canvas, mShader, firstBarMarginLeft-mWidth/2, (int)(percent*(-mHeight/2+500)), firstBarMarginLeft-mWidth/2 + 50, -10, false, false);

        mShader[0] = mColorGradient[6];
        mShader[1] = mColorGradient[7];
        mShader[2] = mColorGradient[8];
        drawSingleBar(canvas, mShader, firstBarMarginLeft-mWidth/2, (int)(percent*(-mHeight/2+550)), firstBarMarginLeft-mWidth/2 + 50, -10, false, false);
        drawSingleBar(canvas, mShader, firstBarMarginLeft-mWidth/2, 10, firstBarMarginLeft-mWidth/2 + 50, (int)(percent*(mHeight/2-450)), false, true);
        drawSingleBar(canvas, mShader, firstBarMarginLeft-mWidth/2, 10, firstBarMarginLeft-mWidth/2 + 50, (int)(percent*(mHeight/2-300)), false, true);
        mShader[0] = mColorGradient[9];
        mShader[1] = mColorGradient[10];
        mShader[2] = mColorGradient[11];
        drawSingleBar(canvas, mShader, firstBarMarginLeft-mWidth/2, 10, firstBarMarginLeft-mWidth/2 + 50, (int)(percent*(mHeight/2-350)), false, true);
        drawSingleBar(canvas, mShader, firstBarMarginLeft-mWidth/2, 10, firstBarMarginLeft-mWidth/2 + 50, (int)(percent*(mHeight/2-500)), false, true);
        drawSingleBar(canvas, mShader, firstBarMarginLeft-mWidth/2, (int)(percent*(-mHeight/2+500)), firstBarMarginLeft-mWidth/2 + 50, -10, false, false);
        canvas.restore();

    }

    private void drawVertical(Canvas canvas) {

        int textMarginLeft = WindowUtil.dip2px(mContext, 5);
        Rect rect = new Rect();
        String temp = "100";
        mVerticalTextPaint.getTextBounds(temp, 0, temp.length(), rect);
        int textHeight = rect.bottom-rect.top;

        for (int i=0; i<11; i++) {

            canvas.drawText(generateDigit(i*100), -mWidth/2+textMarginLeft, 2*mHeight/5-2*i*mHeight/25+textHeight/3, mVerticalTextPaint);

        }
    }

    private void drawHorizontal(Canvas canvas) {

        float textWidth = mHorizontalTextPaint.measureText("DEC");
        Log.d(TAG, "" + textWidth);
        int marginLeft = WindowUtil.dip2px(mContext, 45);
        int contentWidth = mWidth - WindowUtil.dip2px(mContext, 45) - WindowUtil.dip2px(mContext, 10);
        contentWidth = contentWidth/6;
        canvas.drawText("DEC", marginLeft+contentWidth/2-textWidth/2-mWidth/2, mHeight/2-40, mHorizontalTextPaint);
        canvas.drawText("JAN", marginLeft+3*contentWidth/2-textWidth/2-mWidth/2, mHeight/2-40, mHorizontalTextPaint);
        canvas.drawText("FEB", marginLeft+5*contentWidth/2-textWidth/2-mWidth/2, mHeight/2-40, mHorizontalTextPaint);
        canvas.drawText("MAR", marginLeft+7*contentWidth/2-textWidth/2-mWidth/2, mHeight/2-40, mHorizontalTextPaint);
        canvas.drawText("APR", marginLeft+9*contentWidth/2-textWidth/2-mWidth/2, mHeight/2-40, mHorizontalTextPaint);
        canvas.drawText("MAY", marginLeft+11*contentWidth/2-textWidth/2-mWidth/2, mHeight/2-40, mHorizontalTextPaint);

    }

   public void init() {

       ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
       valueAnimator.setDuration(1000);
       valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
       valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
           @Override
           public void onAnimationUpdate(ValueAnimator valueAnimator) {

               percent = (float)valueAnimator.getAnimatedValue();
               invalidate();

           }
       });
       valueAnimator.start();

   }

}
