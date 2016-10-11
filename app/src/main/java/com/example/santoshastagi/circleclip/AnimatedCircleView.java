package com.example.santoshastagi.circleclip;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class AnimatedCircleView extends View {
  private Interpolator INTERPOLATOR = new AccelerateDecelerateInterpolator();
  private static final int COLOR_FILL = -1;

  private boolean mClockwise = false; //default clockwise is false to enable existing implementation
  private boolean mAnimated = true; //default animation is true to enable existing implementation
  private int mStrokeColor = Color.WHITE;
  private int mStrokeWidth = 1;
  private int mFillColor = -1;
  private boolean mFillCircleAnimation = false;

  @IntDef({START, END, ANIMATING})
  @Retention(RetentionPolicy.SOURCE)
  public @interface State {
  }

  private static final int START = 0;
  private static final int END = 1;
  private static final int ANIMATING = 2;

  private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
  private final RectF mRect = new RectF();

  @State private int mState = START;
  private long mAnimationStartTime = 0;
  private long mDuration = 0;
  private Listener mListener = null;

  public AnimatedCircleView(Context context) {
    super(context);
  }

  public AnimatedCircleView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(attrs);
  }

  private void init(AttributeSet attrs) {
    TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.AnimatedCircleView);
    mClockwise = ta.getBoolean(R.styleable.AnimatedCircleView_clockwise, false);
    mAnimated = ta.getBoolean(R.styleable.AnimatedCircleView_animated, true);
    mState = mAnimated ? START : END;
    mStrokeColor = ta.getColor(R.styleable.AnimatedCircleView_stroke_color, Color.WHITE);
    float strokeWidth = ta.getFloat(R.styleable.AnimatedCircleView_stroke_width_cv, 0f);
    mFillColor = ta.getColor(R.styleable.AnimatedCircleView_fill_color, COLOR_FILL);
    mFillCircleAnimation = ta.getBoolean(R.styleable.AnimatedCircleView_fill_circle, false);
    ta.recycle();

    if (mFillColor != -1) {
      mPaint.setColor(mFillColor);
      mPaint.setStyle(Paint.Style.FILL);
    } else {
      mPaint.setColor(mStrokeColor);
      mPaint.setStyle(Paint.Style.STROKE);
      mPaint.setStrokeWidth(mStrokeWidth);
    }
  }

  @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    float halfStroke = mStrokeWidth / 2f;
    mRect.left = 0 + halfStroke;
    mRect.right = w - halfStroke;
    mRect.top = 0 + halfStroke;
    mRect.bottom = h - halfStroke;
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    if (mState == START) {
      // Draw nothing.
      return;
    }

    float percentToDraw;
    if (mState == ANIMATING) {
      float currentAnimationTime = System.currentTimeMillis() - mAnimationStartTime;
      float percentAnimationTime = currentAnimationTime / mDuration;
      if (percentAnimationTime >= 1f) {
        percentToDraw = 1f;
        mState = END;
        if (mListener != null) mListener.onAnimationEnd();
      } else {
        percentToDraw = INTERPOLATOR.getInterpolation(percentAnimationTime);
      }
    } else {
      // END state
      percentToDraw = 1f;
    }

    if (mFillCircleAnimation) {
      float newWidth = mRect.width() * (1f - percentToDraw);
      float differenceFactor = (mRect.width() - newWidth) / 2;

      float left, right, top, bottom;
        left = 0;
        right = mRect.right;
        top  = 0;
        bottom = mRect.bottom;


      RectF rectF = new RectF(left, top, right, bottom);
      canvas.drawArc(rectF, 0, 360f, false, mPaint);
    } else {
      float sweepValue = 360f * percentToDraw;
      float sweepAngle = mClockwise ? sweepValue : -sweepValue;
      canvas.drawArc(mRect, -90, sweepAngle, false, mPaint);
    }

    if (mState == ANIMATING) {
      postInvalidateOnAnimation();
    }
  }

  public interface Listener {
    void onAnimationEnd();
  }
}
