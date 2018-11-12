package com.example.yuanyuanlai.uav.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.example.yuanyuanlai.uav.R;

/**
 * @author: Gong Yunhao
 * @version: V1.0
 * @date: 2018/7/24
 * @github https://github.com/Roman-Gong
 * @blog https://www.jianshu.com/u/52a8fa1f29fb
 */
public class WaveView extends View{

    private float CC = (float) 0.552284749831;
    private Path mAbovePath,mBelowWavePath;
    private Paint mAboveWavePaint,mBelowWavePaint;
    private float mHeight,mWidth;

    private DrawFilter mDrawFilter;

    private float φ;

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化路径
        mAbovePath = new Path();
        mBelowWavePath = new Path();
        //初始化画笔
        mAboveWavePaint = new Paint( Paint.ANTI_ALIAS_FLAG);
        mAboveWavePaint.setAntiAlias(true);
        mAboveWavePaint.setStyle( Paint.Style.FILL);
        mAboveWavePaint.setColor(getResources().getColor(R.color.waveAboveBlue));

        mBelowWavePaint = new Paint( Paint.ANTI_ALIAS_FLAG);
        mBelowWavePaint.setAntiAlias(true);
        mBelowWavePaint.setStyle( Paint.Style.FILL);
        mBelowWavePaint.setColor(getResources().getColor( R.color.waveBelowBlue));
//        mBelowWavePaint.setAlpha(80);
        //画布抗锯齿
        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.translate(0, mHeight / 2);
        canvas.setDrawFilter(mDrawFilter);

        mAbovePath.reset();
        mBelowWavePath.reset();
        φ-=0.1f;
        float y,y2;

        double ω = 2 * Math.PI / getWidth();

        mAbovePath.rCubicTo(0, CC * mHeight / 2, (1 - CC) * mWidth / 2, mHeight / 2, mWidth / 2, mHeight / 2);
        mBelowWavePath.rCubicTo(0, CC * mHeight / 2, (1 - CC) * mWidth / 2, mHeight / 2, mWidth / 2, mHeight / 2);

        mAbovePath.lineTo(0, 0);
        mBelowWavePath.lineTo(0, 0);
        for (float x = 0; x <= getWidth(); x += 20) {
            /**
             *  y=Asin(ωx+φ)+k
             */
            y = (float) (20 * Math.cos(ω * x + φ) + 6);
            y2 = (float) (20 * Math.sin(ω * x + φ) + 0);
            mAbovePath.lineTo(x, y);
            mBelowWavePath.lineTo(x, y2);
        }

        mAbovePath.lineTo( mWidth, 0);
        mBelowWavePath.lineTo( mWidth, 0);
        mAbovePath.rCubicTo(0, mHeight / 2 * CC,-mWidth / 2 * (1-CC), mHeight / 2,  -mWidth / 2, mHeight / 2);
        mBelowWavePath.rCubicTo(0, mHeight / 2 * CC,-mWidth / 2 * (1-CC), mHeight / 2,  -mWidth / 2, mHeight / 2);

        canvas.drawPath(mAbovePath,mAboveWavePaint);
        canvas.drawPath(mBelowWavePath,mBelowWavePaint);

        postInvalidateDelayed(60);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged( w, h, oldw, oldh );
        mHeight = h;
        mWidth = w;
    }
}
