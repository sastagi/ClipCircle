package com.example.santoshastagi.circleclip;


public class AnimationUtil {

  public static int getFillCircleDiameter(int viewHeightPx) {
    return (int) (0.78 * viewHeightPx);
  }

  public static float getFontSize(int viewHeightDp) {
    return (float) (0.45 * viewHeightDp);
  }

  public static int getSmallNumberSeparation(int viewHeightDp) {
    return (int) (0.45 * viewHeightDp);
  }
}
