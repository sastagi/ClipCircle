package com.example.santoshastagi.circleclip;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class DigitsView extends View {
  private final Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
  private final Rect mMeasureRect = new Rect();


  private int counter = 0;
  private boolean mIsLeftColumn = false;
  private int mPaddingPx;

  private Context mContext;

  public DigitsView(Context context) {
    super(context);
    mContext = context;
  }

  public DigitsView(Context context, AttributeSet attrs) {
    super(context, attrs);

    mContext = context;
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    mTextPaint.getTextBounds("4", 0, 1, mMeasureRect);
    int height = mMeasureRect.height() * 30 + (30) * mPaddingPx;

    setMeasuredDimension(MeasureSpec.makeMeasureSpec(mMeasureRect.width(), MeasureSpec.EXACTLY),
        MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    int numberCount = 30; //Based on the column, we will draw 2 sets of 0 - 9 or 3 sets.
    for (int i = 0; i < numberCount; i++) {
      drawNumbers(i, canvas);
    }
  }

  public void setNumberSeparation(float viewSizeDp) {
    DisplayMetrics DISPLAY_METRICS = getContext().getResources().getDisplayMetrics();
    mPaddingPx = Math.round(viewSizeDp * (DISPLAY_METRICS.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    requestLayout();
  }

  private void drawNumbers(int number, Canvas canvas) {
    counter = (counter > 9) ? 0 : counter;
    canvas.drawText(counter + "", 0, (number + 1) * mPaddingPx, mTextPaint); //+1 is needed to maintain ease of calculation while coming to animating to a number. Otherwise, the top number has no padding, but all of the others have some.
    counter++;
  }

  public void setFontSize(float viewSizeDp) {
    mTextPaint.setColor(Color.BLACK);
    DisplayMetrics DISPLAY_METRICS = getContext().getResources().getDisplayMetrics();
    mTextPaint.setTextSize(Math.round(viewSizeDp * (DISPLAY_METRICS.xdpi / DisplayMetrics.DENSITY_DEFAULT)));
    invalidate();
  }

  public int[] getOffsetValues(float numberSeparation) {
    int counter = 0;
    DisplayMetrics DISPLAY_METRICS = getContext().getResources().getDisplayMetrics();
    int paddingPx =  Math.round(numberSeparation * (DISPLAY_METRICS.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    int[] offsetValues = new int[10];

    //initial value and final value are used to only provide offsets for the last set of 0 - 9. This may change based on left column (11-20) or not (21-30).
    int initialValue = 21;
    int finalValue = 30;

    for (int i = initialValue; i <= finalValue; i++) {
      offsetValues[counter++] = -(i * paddingPx) + ((5 * paddingPx) / 4);
    }

    return offsetValues;
  }
}