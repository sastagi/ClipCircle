package com.example.santoshastagi.circleclip;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

public class NumbersView extends LinearLayout {
   DigitsView mLeftColumnCV;
   DigitsView mRightColumnCV;

  private final Path mClipPath = new Path();

  private int[] leftOffsetValues;
  private int[] rightOffsetValues;

  public NumbersView(Context context, AttributeSet attrs) {
    super(context, attrs);

    setOrientation(HORIZONTAL);
    setGravity(Gravity.CENTER_HORIZONTAL);

    mLeftColumnCV = (DigitsView) findViewById(R.id.cvLeftColumn);
    mRightColumnCV = (DigitsView) findViewById(R.id.cvRightColumn);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    LayoutInflater.from(getContext()).inflate(R.layout.numbers_view, this, true);

  }

  @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    float radius = Math.max(w, h) / 2f;
    mClipPath.addCircle((w / 2f), (h / 2f), radius, Path.Direction.CW);
  }

  @Override protected void dispatchDraw(Canvas canvas) {
    canvas.clipPath(mClipPath, Region.Op.INTERSECT);
    super.dispatchDraw(canvas);
  }

  public void setNumbersViewProperties(int viewSizeDp) {

    mLeftColumnCV = (DigitsView) findViewById(R.id.cvLeftColumn);
    mRightColumnCV = (DigitsView) findViewById(R.id.cvRightColumn);
    mLeftColumnCV.setFontSize(AnimationUtil.getFontSize(viewSizeDp));
    mRightColumnCV.setFontSize(AnimationUtil.getFontSize(viewSizeDp));

    mLeftColumnCV.setNumberSeparation(AnimationUtil.getSmallNumberSeparation(viewSizeDp));
    mRightColumnCV.setNumberSeparation(AnimationUtil.getSmallNumberSeparation(viewSizeDp));

    leftOffsetValues = mLeftColumnCV.getOffsetValues(AnimationUtil.getSmallNumberSeparation(viewSizeDp));
    rightOffsetValues = mRightColumnCV.getOffsetValues(AnimationUtil.getSmallNumberSeparation(viewSizeDp));
  }

  /*
  Animate NumberView.
  */
  public void animateNumbers(final int left, final int right) {
    mLeftColumnCV.setY(leftOffsetValues[0]);
    mLeftColumnCV.setY(leftOffsetValues[0]);

    ObjectAnimator moveLeftNumber = ObjectAnimator.ofFloat(mLeftColumnCV, "translationY", 0, leftOffsetValues[left]);
    moveLeftNumber.setInterpolator(new DecelerateInterpolator());
    moveLeftNumber.setDuration(2000);
    moveLeftNumber.start();

    ObjectAnimator moveRightNumber = ObjectAnimator.ofFloat(mRightColumnCV, "translationY", 0, rightOffsetValues[right]);
    moveRightNumber.setInterpolator(new DecelerateInterpolator());
    moveRightNumber.setDuration(2000);
    moveRightNumber.start();
  }

}
