package com.example.yuanyuanlai.uav.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.yuanyuanlai.uav.R;

public abstract class ControlView extends View {

    public static final int ACTION_PRESSED = 1;
    public static final int ACTION_MOVE = 0;
    public static final int ACTION_RELEASE = -1;

    private Region edgeRegion = new Region();

    private Region touchRegion = new Region();

    private Point centerPoint = new Point();

    private int radius;

    public ControlView(Context context) {
        this(context, null);
    }

    public ControlView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ControlView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeData(context, attrs);
        initialTouchRange();
    }

    private void initializeData(Context context, @Nullable AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ControlView);
        radius = typedArray.getDimensionPixelSize(R.styleable.ControlView_edge_radius, 200);
        typedArray.recycle();

        centerPoint.x = radius + 20;
        centerPoint.y = radius + 20;

    }

    private void initialTouchRange() {

        Path edgeRulePath = new Path();
        edgeRulePath.addCircle(centerPoint.x, centerPoint.y, radius, Path.Direction.CW);
        Region globalRegion = new Region(centerPoint.x - radius, centerPoint.y - radius,
                centerPoint.x + radius, centerPoint.y + radius);
        edgeRegion.setPath(edgeRulePath, globalRegion);

        Path touchPath = new Path();
        touchPath.addCircle(centerPoint.x, centerPoint.y, radius * 2, Path.Direction.CW);
        Region globalRegionSecond = new Region(centerPoint.x - 2 * radius, centerPoint.y - 2 * radius,
                centerPoint.x + 2 * radius, centerPoint.y + 2 * radius);
        touchRegion.setPath(touchPath, globalRegionSecond);


    }

    private double calculateAngle(float dx, float dy) {
        double degrees = Math.toDegrees(Math.atan2(dy, dx));
        return degrees < 0 ? Math.floor(degrees + 360) : degrees;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();

        Log.d("ControlView", "---->" + x + ":" + y);
        double angle = calculateAngle(x - centerPoint.x, y - centerPoint.y);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (edgeRegion.contains((int) x, (int) y)) {
                    actionDown(x, y, angle);
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (touchRegion.contains((int) x, (int) y)) {
                    actionMove(x, y, angle);
                } else {
                    resetStickInControl();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                actionUp(x, y, angle);
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sideLength = radius * 2 + 40;
        setMeasuredDimension(sideLength, sideLength);
    }

    /**
     * Return the center point of the circle.
     *
     * @return The center point of the circle
     */
    public Point centerPoint() {
        return new Point(centerPoint);
    }

    /**
     * Notify the View the current event information of the action down
     *
     * @param x     The event's x coordinate
     * @param y     The event's y coordinate
     * @param angle The angle of the touch point relative to the center of the circle.
     *              represented by an double between 0 and 359.
     */
    protected void actionDown(float x, float y, double angle) {
    }

    /**
     * Notify the View the current event information of the action move
     *
     * @param x     The event's x coordinate
     * @param y     The event's y coordinate
     * @param angle The angle of the touch point relative to the center of the circle.
     *              represented by an double between 0 and 359.
     */
    protected void actionMove(float x, float y, double angle) {
    }

    /**
     * Notify the View the current event information of the action up or cancel
     *
     * @param x     The event's x coordinate
     * @param y     The event's y coordinate
     * @param angle The angle of the touch point relative to the center of the circle.
     *              represented by an double between 0 and 359.
     */
    protected void actionUp(float x, float y, double angle) {
    }


    protected abstract void resetStickInControl();


}
