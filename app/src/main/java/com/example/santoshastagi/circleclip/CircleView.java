package com.example.santoshastagi.circleclip;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

public class CircleView extends RelativeLayout {

  AnimatedCircleView mFillAnimatedCircleCV;

  public CircleView(Context context) {
    super(context, null);
  }

  public CircleView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    LayoutInflater.from(getContext()).inflate(R.layout.circle_view, this, true);

    mFillAnimatedCircleCV = (AnimatedCircleView) findViewById(R.id.cvInnerStaticCircle);
  }

  public void setCircleViewProperties(int circleDiameter) {

    mFillAnimatedCircleCV.getLayoutParams().width = AnimationUtil.getFillCircleDiameter(circleDiameter);
    mFillAnimatedCircleCV.getLayoutParams().height = AnimationUtil.getFillCircleDiameter(circleDiameter);
  }

}