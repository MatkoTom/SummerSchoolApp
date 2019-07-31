package com.example.summerschoolapp.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

public class CustomAnimationUtils {

    public static void expandDescription(final View view, final int startHeight, final int targetHeight, int duration) {

        Animation animExpand = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {

                if (interpolatedTime == 1) {

                    view.getLayoutParams().height = targetHeight;
                    view.requestLayout();

                } else {

                    view.getLayoutParams().height = (int) (((targetHeight - startHeight) * interpolatedTime) + startHeight);
                    view.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        animExpand.setDuration(duration);
        view.startAnimation(animExpand);
    }

    public static void collapseDescription(final View view, final int startHeight, final int targetHeight, int duration) {

        Animation animCollapse = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {

                if (interpolatedTime == 1) {

                    view.getLayoutParams().height = targetHeight;
                    view.requestLayout();

                } else {

                    view.getLayoutParams().height = startHeight - (int) ((startHeight - targetHeight) * interpolatedTime);
                    view.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        animCollapse.setDuration(duration);
        view.startAnimation(animCollapse);
    }

    public static void scale(View view, float from, float to, int duration,  Animator.AnimatorListener listener) {

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, from, to);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, from, to);

        scaleX.setDuration(duration);
        scaleY.setDuration(duration);


        if (listener != null) {
            scaleX.addListener(listener);
        }

        scaleX.start();
        scaleY.start();
    }

    public static void fadeIn(View view, int duration,  Animator.AnimatorListener listener) {

        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(view, View.ALPHA, 0, 1);
        alphaAnimation.setDuration(duration);

        if (listener != null) {
            alphaAnimation.addListener(listener);
        }

        alphaAnimation.start();
    }

    public static void fadeOut(View view, int duration,  Animator.AnimatorListener listener) {

        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(view, View.ALPHA, 1, 0);
        alphaAnimation.setDuration(duration);

        if (listener != null) {
            alphaAnimation.addListener(listener);
        }

        alphaAnimation.start();
    }

    public static void fadeIn(View view, int duration, float from, float to,  Animator.AnimatorListener listener) {

        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(view, View.ALPHA, from, to);
        alphaAnimation.setDuration(duration);

        if (listener != null) {
            alphaAnimation.addListener(listener);
        }

        alphaAnimation.start();
    }

    public static void fadeOut(View view, int duration, float from, float to,  Animator.AnimatorListener listener) {

        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(view, View.ALPHA, from, to);
        alphaAnimation.setDuration(duration);

        if (listener != null) {
            alphaAnimation.addListener(listener);
        }

        alphaAnimation.start();
    }

    public static ObjectAnimator translateX(View view, float from, float to, int duration,  Animator.AnimatorListener listener) {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationX", from, to);
        translationX.setDuration(duration);

        if (listener != null) {
            translationX.addListener(listener);
        }

        translationX.start();

        return translationX;
    }

    public static ObjectAnimator translateY(View view, float from, float to, int duration, Animator.AnimatorListener listener) {
        ObjectAnimator translationY = ObjectAnimator.ofFloat(view, "translationY", from, to);
        translationY.setDuration(duration);

        if (listener != null) {
            translationY.addListener(listener);
        }

        translationY.start();

        return translationY;
    }

    public static void scaleX(View view, float from, float to, int duration,  Animator.AnimatorListener listener) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, from, to);

        scaleX.setDuration(duration);


        if (listener != null) {
            scaleX.addListener(listener);
        }

        scaleX.start();
    }

    public static void scaleY(View view, float from, float to, int duration,  Animator.AnimatorListener listener) {
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, from, to);

        scaleY.setDuration(duration);


        if (listener != null) {
            scaleY.addListener(listener);
        }

        scaleY.start();
    }

    public static void rotate(View view, int duration, float fromDegrees, float toDegrees,  Animator.AnimatorListener listener) {
        ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(view, View.ROTATION, fromDegrees, toDegrees);
        rotateAnimation.setDuration(duration);

        if (listener != null) {
            rotateAnimation.addListener(listener);
        }

        rotateAnimation.start();
    }

    public static void changeWeightSum(LinearLayout lay, int duration, float to, Animator.AnimatorListener listener){
        float ws = lay.getWeightSum();
        ObjectAnimator animator = ObjectAnimator.ofFloat(lay, "weightSum", ws, 2.0f);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                lay.requestLayout();
            }
        });

        if(listener != null) animator.addListener(listener);
        animator.start();
    }
}
