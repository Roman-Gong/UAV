package com.example.yuanyuanlai.uav;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class HeightControlView extends View {

    private int width, height;
    private Paint paint;
    private Region touchRegion = new Region();
    private Point centerPoint = new Point();
    private float switchX, switchY;
    private Region switchRegion = new Region();
    private float dr;

    public HeightControlView(Context context) {
        super(context);

        paint = new Paint();

    }

    public HeightControlView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();

    }

    public HeightControlView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint();

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX(),y = event.getY();
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
            if (touchRegion.contains((int)x, (int)y)) {
                actionDown(y);
                return true;
            }
                break;
            case MotionEvent.ACTION_MOVE:
                actionMove(y);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                actionUp(y);
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRectangle(canvas);
        drawSwitch(canvas);

    }


    private void initialTouchRange(){

        Path touchPath = new Path();
        RectF rectF = new RectF(0, 0, width, height);
        touchPath.addRect(rectF, Path.Direction.CW);
        Region globalRegion = new Region(0, 0, width, height);
        touchRegion.setPath(touchPath, globalRegion);

    }

    private void initialSwitchRange(){

        centerPoint.x = width/2;
        centerPoint.y = height/2;
        switchX = centerPoint.x;
        switchY = centerPoint.y;

        Region switchClip = new Region(0, 5*height/12, width, 7*height/12);
        Path switchPath = new Path();
        switchPath.addRoundRect(0, 5*height/12, width, 7*height/12, new float[]{15, 15, 15, 15, 15, 15, 15, 15}, Path.Direction.CW);
        switchRegion.setPath(switchPath, switchClip);

        dr = 5*height/12;

    }

    private void actionDown(float y){
        updateSwitch(y);
    }

    private void actionMove(float y){

        updateSwitch(y);

    }

    private void actionUp(float y){

        resetSwitch();

    }

    private void updateSwitch(float y){

            if (y>60 && y<height-60){
                switchX = 0;
                switchY = y;
                invalidate();
            }

    }

    private void resetSwitch(){

        switchX = centerPoint.x;
        switchY = centerPoint.y;
        invalidate();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        initialSwitchRange();
        initialTouchRange();
    }


    private void drawRectangle(Canvas canvas){

        paint.reset();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(getResources().getColor(R.color.colorRectangle));
        paint.setStrokeWidth(5.0f);
        paint.setAntiAlias(true);
        canvas.drawRoundRect(35, 10, width-35, height-10, (width-70)/2, (width-70)/2, paint);

        paint.setColor(getResources().getColor(R.color.colorInnerRectangle));
        canvas.drawRoundRect(45, 20, width-45, height-20, (width-70)/2, (width-70)/2, paint);

    }

    private void drawSwitch(Canvas canvas){

        canvas.save();
        paint.reset();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(R.color.colorSwitch));
        paint.setAntiAlias(true);
        canvas.translate(0, switchY - height/12);
        canvas.drawRoundRect(0, 0, width, height/6, 25, 25, paint);
        canvas.restore();

        canvas.save();
        paint.reset();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(getResources().getColor(R.color.colorWhite));
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5.0f);
        canvas.translate(0, switchY - height*1/24);
        canvas.drawLines(new float[]{
                20, 0, width-20, 0,
                20, height/24, width-20, height/24,
                20, height/12, width-20, height/12
        }, paint);

        canvas.restore();

    }
}
