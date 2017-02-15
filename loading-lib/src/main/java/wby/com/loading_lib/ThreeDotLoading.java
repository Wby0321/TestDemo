package wby.com.loading_lib;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 文件名：ThreeDotLoading
 * 描  述：
 * 作  者：Wby
 * 时  间：2017/2/8.
 * 邮  箱：CHLYSS@outlook.com
 * 版  权：
 */

public class ThreeDotLoading extends View {

    private Paint mPaint;

    private String type;
    private float mWidth = 0f;
    private float mHeight = 0f;
    private int dotCount = 3;
    private int mJumpIndex = 0;
    private int radius = 8;
    private int dotColor = Color.WHITE;
    private float dotCurValue = 1.0f;
    private float dotMinValue = 1.0f;
    private float dotMaxValue = 2.0f;
    private long duration = 500;
    private ValueAnimator valueAnimator;


    public ThreeDotLoading(Context context) {
        this(context, null);
    }

    public ThreeDotLoading(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThreeDotLoading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ThreeDotLoading);

        type = typedArray.getString(R.styleable.ThreeDotLoading_type);
        if (type == null || (!type.equals("jump") && !type.equals("zoom")))
            type = "zoom";
        dotCount = typedArray.getInt(R.styleable.ThreeDotLoading_dotCount, dotCount);
        dotColor = typedArray.getInt(R.styleable.ThreeDotLoading_dotColor, Color.WHITE);
        dotMinValue = typedArray.getFloat(R.styleable.ThreeDotLoading_minValue, 1f);
        dotMaxValue = typedArray.getFloat(R.styleable.ThreeDotLoading_maxValue, type.equals("zoom") ? 2f : 1.2f);
        duration = typedArray.getInteger(R.styleable.ThreeDotLoading_duration, 500);
        initPaint();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        radius = (int) (mWidth / dotCount / 4);
        Log.i("loading", "onMeasure: " + radius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float circleX = mWidth / dotCount;
        for (int i = 0; i < dotCount; i++) {
            if (i == mJumpIndex % dotCount) {
                canvas.drawCircle(i * circleX + circleX / 2, type.equals("jump") ? mHeight / 2 / dotCurValue : mHeight / 2, type.equals("zoom") ? radius * dotCurValue : radius, mPaint);
            } else {
                canvas.drawCircle(i * circleX + circleX / 2, mHeight / 2, radius, mPaint);
            }
        }
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(dotColor);
    }


    private ValueAnimator startValueAnim(float start, float center, float end, long time) {
        valueAnimator = ValueAnimator.ofFloat(start, center, end);
        valueAnimator.setDuration(time);
        valueAnimator.setInterpolator(new LinearInterpolator());


        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                dotCurValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                mJumpIndex++;
                if (mJumpIndex == dotCount) mJumpIndex = 0;
                invalidate();
            }
        });
        if (!valueAnimator.isRunning()) valueAnimator.start();

        return valueAnimator;
    }

    public void setDotColor(@ColorInt int color) {
        mPaint.setColor(color);
        postInvalidate();
    }

    public void setDotCount(int count) {
        dotCount = count;
        postInvalidate();
    }

    public void setDuration(long duration) {
        this.duration = duration;
        postInvalidate();
    }

    public void setMinValue(int dotMinValue) {
        this.dotMinValue = dotMinValue;
        postInvalidate();
    }

    public void setMaxValue(int dotMaxValue) {
        this.dotMaxValue = dotMaxValue;
        postInvalidate();
    }

    public void setType(String type) {
        if (!type.equals("jump") && !type.equals("zoom"))
            this.type = "zoom";
        else this.type = type;

    }

    public void startAnim() {
        startValueAnim(dotMinValue, dotMaxValue, dotMinValue, duration);
    }

    public void startAnim(float start, float end, long time) {
        startValueAnim(start, end, start, time);
    }

    public void stopAnim() {
        dotCurValue = 0f;
        mJumpIndex = 0;
        if (valueAnimator != null) {
            clearAnimation();
            valueAnimator.setRepeatCount(0);
            valueAnimator.cancel();
            valueAnimator.end();
        }
    }
}
