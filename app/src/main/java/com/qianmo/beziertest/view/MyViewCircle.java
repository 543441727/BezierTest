package com.qianmo.beziertest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/31 0031.
 * E-Mail：543441727@qq.com
 * 使用贝塞尔曲线绘制圆心
 */

public class MyViewCircle extends View {
    private Paint mPaint;
    private Paint mPaintCircle;
    private Paint mPaintPoint;
    private int mCenterX;
    private int mCenterY;
    private int mCircleRadius;

    private int mDuration = 1000; //动画总时间
    private int mCurrTime = 0;  //当前已进行时间
    private int mCount = 100;//将总时间划分多少块
    private float mPiece = mDuration / mCount; //每一块的时间 ；

    private List<PointF> mPointDatas;
    private List<PointF> mPointControlls;

    private boolean isRuning;

    public MyViewCircle(Context context) {
        this(context, null);
    }

    public MyViewCircle(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyViewCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        mPaintCircle = new Paint();
        mPaintCircle.setColor(Color.BLACK);
        mPaintCircle.setStrokeWidth(3);
        mPaintCircle.setStyle(Paint.Style.STROKE);
        mPaintCircle.setAntiAlias(true);

        mPaintPoint = new Paint();
        mPaintPoint.setColor(Color.BLACK);
        mPaintPoint.setStrokeWidth(5);
        mPaintPoint.setStyle(Paint.Style.FILL);
        mPaintPoint.setAntiAlias(true);

        mCircleRadius = 150;

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //初始化坐标系
        mCenterX = getWidth() / 2;
        mCenterY = getHeight() / 2;

        mPointDatas = new ArrayList<>();
        mPointControlls = new ArrayList<>();

        //初始化数据点数据和辅助点位置
        mPointDatas.add(new PointF(mCenterX, mCenterY - mCircleRadius));
        mPointDatas.add(new PointF(mCenterX + mCircleRadius, mCenterY));
        mPointDatas.add(new PointF(mCenterX, mCenterY + mCircleRadius));
        mPointDatas.add(new PointF(mCenterX - mCircleRadius, mCenterY));

        mPointControlls.add(new PointF(mCenterX + mCircleRadius / 2, mCenterY - mCircleRadius));
        mPointControlls.add(new PointF(mCenterX + mCircleRadius, mCenterY - mCircleRadius / 2));

        mPointControlls.add(new PointF(mCenterX + mCircleRadius, mCenterY + mCircleRadius / 2));
        mPointControlls.add(new PointF(mCenterX + mCircleRadius / 2, mCenterY + mCircleRadius));

        mPointControlls.add(new PointF(mCenterX - mCircleRadius / 2, mCenterY + mCircleRadius));
        mPointControlls.add(new PointF(mCenterX - mCircleRadius, mCenterY + mCircleRadius / 2));

        mPointControlls.add(new PointF(mCenterX - mCircleRadius, mCenterY - mCircleRadius / 2));
        mPointControlls.add(new PointF(mCenterX - mCircleRadius / 2, mCenterY - mCircleRadius));
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //绘制坐标系
        drawCoordinates(canvas);

        //绘制圆
//        drawCenterCircle(canvas);


        //绘制数据点和辅助点
        drawBezierLine(canvas);


       if (isRuning){
           //动态改变数据点和辅助点
           mCurrTime += mPiece;
           if (mCurrTime < mDuration) {
               mPointDatas.get(0).y += 120 / mCount;
               mPointControlls.get(2).x -= 20.0 / mCount;

               mPointControlls.get(3).y -= 80.0 / mCount;
               mPointControlls.get(4).y -= 80.0 / mCount;
               mPointControlls.get(5).x += 20.0 / mCount;

               postInvalidateDelayed((long) mPiece);
           }
       }
    }

    /**
     * @param canvas
     */
    public void drawCoordinates(Canvas canvas) {
        canvas.save();
        //绘制x，y轴坐标系
        canvas.drawLine(mCenterX, 0, mCenterX, getHeight(), mPaint);
        canvas.drawLine(0, mCenterY, getWidth(), mCenterY, mPaint);

        canvas.restore();
    }

    public void drawCenterCircle(Canvas canvas) {
        canvas.save();
        //绘制中心圆
        canvas.drawCircle(mCenterX, mCenterY, mCircleRadius, mPaintCircle);
        canvas.restore();
    }

    /**
     * @param canvas
     */
    public void drawBezierLine(Canvas canvas) {
        //绘制数据点
        canvas.save();
        for (int i = 0; i < mPointDatas.size(); i++) {
            canvas.drawPoint(mPointDatas.get(i).x, mPointDatas.get(i).y, mPaintPoint);
        }
        //绘制控制点
        for (int i = 0; i < mPointControlls.size(); i++) {
            canvas.drawPoint(mPointControlls.get(i).x, mPointControlls.get(i).y, mPaintPoint);
        }

        //利用三阶贝塞尔曲线实现画圆
        Path path = new Path();
        path.moveTo(mPointDatas.get(0).x, mPointDatas.get(0).y);
        for (int i = 0; i < mPointDatas.size(); i++) {
            if (i == mPointDatas.size() - 1) {
                path.cubicTo(mPointControlls.get(2 * i).x, mPointControlls.get(2 * i).y, mPointControlls.get(2 * i + 1).x, mPointControlls.get(2 * i + 1).y, mPointDatas.get(0).x, mPointDatas.get(0).y);

            } else {
                path.cubicTo(mPointControlls.get(2 * i).x, mPointControlls.get(2 * i).y, mPointControlls.get(2 * i + 1).x, mPointControlls.get(2 * i + 1).y, mPointDatas.get(i + 1).x, mPointDatas.get(i + 1).y);
            }

        }
        canvas.drawPath(path, mPaintCircle);

        canvas.restore();
    }


    public boolean isRuning() {
        return isRuning;
    }

    public void setRuning(boolean runing) {
        isRuning = runing;
        invalidate();
    }


}
