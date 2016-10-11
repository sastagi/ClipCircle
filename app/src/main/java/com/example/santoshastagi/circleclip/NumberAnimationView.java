package com.example.santoshastagi.circleclip;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

public class NumberAnimationView extends RelativeLayout {

  CircleView mCircleCV;
  NumbersView mNumbersDynamicCV;

  public NumberAnimationView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    LayoutInflater.from(getContext()).inflate(R.layout.numbers_animation_view, this, true);

    mCircleCV = (CircleView) findViewById(R.id.cvCircleView);
    mNumbersDynamicCV = (NumbersView) findViewById(R.id.cvNumbersDynamic);
  }

  public void setNumberViewProperties(int viewHeightPx) {
    mCircleCV.setCircleViewProperties(viewHeightPx);
    DisplayMetrics DISPLAY_METRICS = getContext().getResources().getDisplayMetrics();;
    mNumbersDynamicCV.setNumbersViewProperties(Math.round(viewHeightPx / (DISPLAY_METRICS.xdpi / DisplayMetrics.DENSITY_DEFAULT)));

    int viewHeightWidthPx = AnimationUtil.getFillCircleDiameter(viewHeightPx);

    mNumbersDynamicCV.getLayoutParams().width = viewHeightWidthPx;
    mNumbersDynamicCV.getLayoutParams().height = viewHeightWidthPx;
  }

  public void animateNumbers(int desiredNumber) {
    final int tensNumber = desiredNumber / 10;
    final int unitsNumber = desiredNumber % 10;
    mNumbersDynamicCV.animateNumbers(tensNumber, unitsNumber);
  }
}

