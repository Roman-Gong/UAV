package com.example.yuanyuanlai.uav;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class StickControlView extends ControlView {

    private Region ballRegion, invalidRegion;

    private Paint paint = new Paint();

    private Point center;

    private int edgeInnerRadius, stickInnerRadius, edgeOuterRadius, stickOuterRadius, dr;

    private float stickX, stickY;

    private int stickInnerColor, stickOuterColor, edgeOuterColor, edgeInnerColor, dotColor; //颜色属性

    private double currentAngle;

    private OnAngleUpdateListener angleUpdateListener;

    public StickControlView(Context context) {
        this(context, null);
    }

    public StickControlView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickControlView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialAttr(context, attrs);
        initialData();
    }

    private void initialAttr(Context context, @Nullable AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StickControlView);
        edgeOuterRadius = typedArray.getDimensionPixelSize(R.styleable.StickControlView_edge_outer_radius,
                200);
        stickInnerRadius = typedArray.getDimensionPixelSize(R.styleable.StickControlView_stick_inner_radius,
                edgeOuterRadius / 2);
        stickInnerColor = typedArray.getColor(R.styleable.StickControlView_stick_inner_color,
                getResources().getColor(R.color.colorInnerStickDefault));
        edgeOuterColor = typedArray.getColor(R.styleable.StickControlView_edge_outer_color,
                getResources().getColor(R.color.colorOuterEdgeDefault));
        edgeInnerColor = typedArray.getColor(R.styleable.StickControlView_edge_inner_color,
                getResources().getColor(R.color.colorInnerEdgeDefault));
        edgeInnerRadius = typedArray.getDimensionPixelOffset(R.styleable.StickControlView_edge_inner_radius,
                190);
        dotColor = typedArray.getColor(R.styleable.StickControlView_dot_color,
                getResources().getColor(R.color.colorDotDefault));
        stickOuterRadius = typedArray.getDimensionPixelSize(R.styleable.StickControlView_stick_outer_radius,
                edgeOuterRadius / 2 + 20);
        stickOuterColor = typedArray.getDimensionPixelSize(R.styleable.StickControlView_stick_outer_color,
                getResources().getColor(R.color.colorOuterStickDefault));
        typedArray.recycle();

    }

    private void initialData() {
        
        dr = edgeOuterRadius - stickInnerRadius;
        center = centerPoint();
        stickX = center.x;
        stickY = center.y;

        Region ballRegionClip = new Region(center.x - dr, center.y - dr,
                center.x + dr, center.y + dr);
        Path rockerRulePath = new Path();
        rockerRulePath.addCircle(center.x, center.y, dr
                , Path.Direction.CW);

        ballRegion = new Region();
        ballRegion.setPath(rockerRulePath, ballRegionClip);

        int invalidRadius = edgeOuterRadius / 3;
        Region invalidRegionClip = new Region(center.x - invalidRadius, center.y - invalidRadius,
                center.x + invalidRadius, center.y + invalidRadius);
        Path eventInvalidPath = new Path();
        eventInvalidPath.addCircle(center.x, center.y, invalidRadius, Path.Direction.CW);
        invalidRegion = new Region();
        invalidRegion.setPath(eventInvalidPath, invalidRegionClip);

        currentAngle = -1;
        
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawColor(Color.TRANSPARENT);
        drawOuterEdge(canvas);
        drawInnerEdge(canvas);
        drawOuterStick(canvas);
        drawInnerStick(canvas);
        drawDot(canvas);

    }

    protected void drawOuterStick(Canvas canvas){

        paint.reset();
        paint.setColor(stickOuterColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(stickX, stickY, stickOuterRadius, paint);

    }

    protected void drawOuterEdge(Canvas canvas) {

        paint.reset();
        paint.setColor(edgeOuterColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5.0f);
        canvas.drawCircle(center.x, center.y, edgeOuterRadius, paint);

    }

    protected void drawInnerEdge(Canvas canvas){

        paint.reset();
        paint.setColor(edgeInnerColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5.0f);
        canvas.drawCircle(center.x, center.y, edgeInnerRadius, paint);


    }

    protected void drawInnerStick(Canvas canvas) {

        Log.d("InnerStick", "---->调用了InnerStick");
        paint.reset();
        paint.setColor(stickInnerColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(stickX, stickY, stickInnerRadius, paint);

    }

    protected void drawDot(Canvas canvas){

        paint.reset();
        paint.setColor(dotColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        canvas.translate(stickX, stickY);
        canvas.drawCircle(0,0, 5, paint);
        for (int i=0; i<6; i++){
            canvas.drawCircle(20,0, 5, paint);
            canvas.rotate(60);
        }

        for (int i=0; i<12; i++){
            canvas.drawCircle(40,0, 5, paint);
            canvas.rotate(30);
        }

        for (int i=0; i<18; i++){
            canvas.drawCircle(60,0, 5, paint);
            canvas.rotate(20);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {



        return super.onTouchEvent(event);
    }

    private void updateStickPos(float x, float y) {

        if (ballRegion.contains((int) x, (int) y)) {
            //Log.d("StickControlView", "---->在范围内");
            stickX = x;
            stickY = y;
        } else {
            //Log.d("StickControlView", "---->在范围外");
            float dx = x - center.x;
            float dy = y - center.y;
            float scale = (float) Math.sqrt((Math.pow(dx, 2) + Math.pow(dy, 2)));
            stickX = dx * dr / scale + center.x;
            stickY = dy * dr / scale + center.y;
        }
        invalidate();

    }


    // 松手后恢复
    private void resetStick() {

        currentAngle = -1;
        stickX = center.x;
        stickY = center.y;
        invalidate();

    }

    private void updateAngle(double angle, int action) {

        currentAngle = angle;
        if (angleUpdateListener != null) {
            angleUpdateListener.onAngleUpdate(angle, action);
        }

    }

    @Override
    protected void actionDown(float x, float y, double angle) {
        updateStickPos(x, y);
        if (!invalidRegion.contains((int) x, (int) y)) {
            updateAngle(angle, ACTION_PRESSED);
        }
    }

    @Override
    protected void actionMove(float x, float y, double angle) {
        updateStickPos(x, y);
        if (!invalidRegion.contains((int) x, (int) y)) {
            updateAngle(angle, ACTION_MOVE);
        }
    }

    @Override
    protected void actionUp(float x, float y, double angle) {
        resetStick();
        updateAngle(angle, ACTION_RELEASE);
    }

    @Override
    protected void resetStickInControl() {
        resetStick();
    }

    /**
     * Returns the current angle.
     *
     * @return The current angle, -1 represent no touch action.
     */
    public double getCurrentAngle(){
        return currentAngle;
    }

    /**
     * Register a callback to be invoked when the angle is updated.
     *
     * @param angleUpdateListener The callback that will run.
     */
    public void setAngleUpdateListener(OnAngleUpdateListener angleUpdateListener) {
        this.angleUpdateListener = angleUpdateListener;
    }

    /**
     * Interface definition for a callback to be invoked when The angle between the finger
     * and the center of the circle update.
     */
    public interface OnAngleUpdateListener {

        /**
         * Called when angle has been clicked.
         *
         * @param angle  The angle between the finger and the center of the circle.
         * @param action action of the finger.
         */
        void onAngleUpdate(double angle, int action);
    }




}
