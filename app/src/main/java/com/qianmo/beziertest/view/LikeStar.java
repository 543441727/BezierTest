package com.qianmo.beziertest.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.qianmo.beziertest.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



/**
 * Created by Administrator on 2017/4/1 0001.
 * E-Mail：543441727@qq.com
 * 使用贝塞尔曲线实现点赞效果
 */

public class LikeStar extends ViewGroup {
    private List<Drawable> mStarDrawable;
    private List<Interpolator> mInterpolators;
    private int mWidth;
    private int mHeight;
    //定义贝塞尔曲线的数据点和两个控制点
    private PointF mStartPoint, mEndPoint, mControllPointOne, mControllPointTwo;

    private Random random = new Random();

    public LikeStar(Context context) {
        this(context, null);
    }

    public LikeStar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LikeStar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(final Context context) {
        mStarDrawable = new ArrayList<>();



        mInterpolators = new ArrayList<>();
        mStartPoint = new PointF();
        mEndPoint = new PointF();
        mControllPointOne = new PointF();
        mControllPointTwo = new PointF();

        //初始化图片资源
        mStarDrawable.add(getResources().getDrawable(R.mipmap.heart_red));
        mStarDrawable.add(getResources().getDrawable(R.mipmap.heart_blue));
        mStarDrawable.add(getResources().getDrawable(R.mipmap.heart_yellow));
        mStarDrawable.add(getResources().getDrawable(R.mipmap.heart_green));

        //初始化插补器
        mInterpolators.add(new LinearInterpolator());
        mInterpolators.add(new AccelerateDecelerateInterpolator());
        mInterpolators.add(new AccelerateInterpolator());
        mInterpolators.add(new DecelerateInterpolator());

        ImageView image_heard = new ImageView(context);
        image_heard.setImageDrawable(mStarDrawable.get(0));

        image_heard.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        image_heard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击之后开始动画,添加红心到布局文件并开始动画
                final ImageView image_random = new ImageView(context);
                image_random.setImageDrawable(mStarDrawable.get(random.nextInt(4)));

                image_random.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT));
                addView(image_random);

                invalidate();

                //开始做动画效果
                PointF endPointRandom = new PointF(random.nextInt(mWidth), mEndPoint.y);
//                BezierTypeEvaluator bezierTypeEvaluator = new BezierTypeEvaluator(mControllPointOne, mControllPointTwo);
                BezierTypeEvaluator bezierTypeEvaluator = new BezierTypeEvaluator(new PointF( random.nextInt(mWidth ),random.nextInt(mHeight)), new PointF( random.nextInt(mWidth),random.nextInt(mHeight)));
                ValueAnimator valueAnimator = ValueAnimator.ofObject(bezierTypeEvaluator, mStartPoint, endPointRandom);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        PointF pointF = (PointF) animation.getAnimatedValue();
                        image_random.setX(pointF.x);
                        image_random.setY(pointF.y);
                    }
                });

                valueAnimator.setDuration(2000);
                valueAnimator.start();

            }
        });
        addView(image_heard);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();


        // 初始化各个点

        //借用第一个子view控件中的宽高
        View child = getChildAt(0);
        int childW = child.getMeasuredWidth();
        int childH = child.getMeasuredHeight();

        mStartPoint.x = (mWidth - childW) / 2;
        mStartPoint.y = mHeight - childH;
        mEndPoint.x = (mWidth - childW) / 2;
        mEndPoint.y = 0 - childH;

        mControllPointOne.x = random.nextInt(mWidth / 2);
        mControllPointOne.y = random.nextInt(mHeight / 2) + mHeight / 2;

        mControllPointTwo.x = random.nextInt(mWidth / 2) + mWidth / 2;
        mControllPointTwo.y = random.nextInt(mHeight / 2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        //获取view的宽高测量模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //保存测量高度
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i("wangjitao", "l:" + l + ",t:" + t + ",r:" + r + ",b:" + b);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int childW = child.getMeasuredWidth();
            int childH = child.getMeasuredHeight();
            child.layout((mWidth - childW) / 2, (mHeight - childH), (mWidth - childW) / 2 + childW, mHeight);
        }
    }

//    /**
//     * 开始动画
//     */
//    public void startRunning() {
//        BezierTypeEvaluator bezierTypeEvaluator = new BezierTypeEvaluator(mControllPointOne, mControllPointTwo);
//        ValueAnimator valueAnimator = ValueAnimator.ofObject(bezierTypeEvaluator, mStartPoint, mEndPoint);
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                PointF pointF = (PointF) animation.getAnimatedValue();
//                getChildAt(0).setX(pointF.x);
//                getChildAt(0).setY(pointF.y);
//            }
//        });
//
//        valueAnimator.setDuration(3000);
//        valueAnimator.start();
//    }

    public class BezierTypeEvaluator implements TypeEvaluator<PointF> {
        private PointF mControllPoint1, mControllPoint2;

        public BezierTypeEvaluator(PointF mControllPointOne, PointF mControllPointTwo) {
            mControllPoint1 = mControllPointOne;
            mControllPoint2 = mControllPointTwo;
        }

        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            PointF pointCur = new PointF();
            pointCur.x = mStartPoint.x * (1 - fraction) * (1 - fraction) * (1 - fraction) + 3
                    * mControllPoint1.x * fraction * (1 - fraction) * (1 - fraction) + 3
                    * mControllPoint2.x * (1 - fraction) * fraction * fraction + endValue.x * fraction * fraction * fraction;// 实时计算最新的点X坐标
            pointCur.y = mStartPoint.y * (1 - fraction) * (1 - fraction) * (1 - fraction) + 3
                    * mControllPoint1.y * fraction * (1 - fraction) * (1 - fraction) + 3
                    * mControllPoint2.y * (1 - fraction) * fraction * fraction + endValue.y * fraction * fraction * fraction;// 实时计算最新的点Y坐标
            return pointCur;
        }
    }
}
