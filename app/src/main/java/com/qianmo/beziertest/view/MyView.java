package com.qianmo.beziertest.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.qianmo.beziertest.R;

/**
 * Created by Administrator on 2017/3/30 0030.
 * E-Mail：543441727@qq.com
 */

public class MyView extends View {

    private Point controlPoint = new Point(200, 200);

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);

        Path path = new Path();
        path.moveTo(100, 500);
        path.quadTo(controlPoint.x, controlPoint.y, 700, 500);
        //绘制路径
        canvas.drawPath(path, paint);
        //绘制辅助点
        canvas.drawPoint(controlPoint.x,controlPoint.y,paint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                controlPoint.x = (int) event.getX();
                controlPoint.y = (int) event.getY();
                invalidate();
                break;
        }
        return true;
    }
}
