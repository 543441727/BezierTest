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

/**
 * Created by wangjitao on 2017/3/30 0030.
 * E-Mail：543441727@qq.com
 */

public class MyView1 extends View {

    private Point controlPointOne = new Point(200, 200);
    private Point controlPointTwo = new Point(500, 200);

    private boolean isControlPointTwo ;

    private Paint paintBezier;
    private Paint paintLine;

    public MyView1(Context context) {
        this(context, null);
    }

    public MyView1(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paintBezier = new Paint();
        paintBezier.setStyle(Paint.Style.STROKE);
        paintBezier.setColor(Color.BLACK);
        paintBezier.setStrokeWidth(10);


        paintLine = new Paint();
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setColor(Color.RED);
        paintLine.setStrokeWidth(3);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        Path path = new Path();
        path.moveTo(100, 500);
        path.cubicTo(controlPointOne.x, controlPointOne.y,controlPointTwo.x, controlPointTwo.y, 900, 500);
        //绘制路径
        canvas.drawPath(path, paintBezier);
        //绘制辅助点
        canvas.drawPoint(controlPointOne.x, controlPointOne.y, paintBezier);
        canvas.drawPoint(controlPointTwo.x, controlPointTwo.y, paintBezier);
        //绘制连线
//        canvas.drawLine(100, 500, controlPointOne.x, controlPointOne.y, paintLine);
//        canvas.drawLine(900, 500, controlPointOne.x, controlPointOne.y, paintLine);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (isControlPointTwo){
                    controlPointOne.x = (int) event.getX();
                    controlPointOne.y = (int) event.getY();
                }else {
                    controlPointTwo.x = (int) event.getX();
                    controlPointTwo.y = (int) event.getY();
                }
                invalidate();
                break;
        }
        return true;
    }


    public boolean isControlPointTwo() {
        return isControlPointTwo;
    }

    public void setControlPointTwo(boolean controlPointTwo) {
        isControlPointTwo = controlPointTwo;
    }
}
