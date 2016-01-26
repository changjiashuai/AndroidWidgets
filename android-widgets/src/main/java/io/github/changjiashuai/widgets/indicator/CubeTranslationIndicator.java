package io.github.changjiashuai.widgets.indicator;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 16/1/26 17:03.
 */
public class CubeTranslationIndicator extends BaseIndicator {

    private float[] translationX = new float[2], translationY = new float[2];
    private float degrees, scale = 1.0f;

    @Override
    public void draw(Canvas canvas, Paint paint) {
        float rWidth = getWidth() / 5;
        float rHeight = getHeight() / 5;
        for (int i = 0; i < 2; i++) {
            canvas.save();
            canvas.translate(translationX[i], translationY[i]);
            canvas.rotate(degrees);
            canvas.scale(scale, scale);
            RectF rectF = new RectF(-rWidth / 2, -rHeight / 2, rWidth / 2, rHeight / 2);
            canvas.drawRect(rectF, paint);
            canvas.restore();
        }
    }

    @Override
    public List<Animator> createAnimation() {
        List<Animator> animators = new ArrayList<>();
        float startX = getWidth() / 5;
        float startY = getHeight() / 5;
        for (int i = 0; i < 2; i++) {
            final int index = i;
            translationX[index] = startX;
            ValueAnimator translationXAnim = ValueAnimator.ofFloat(startX, getWidth() - startX, getWidth() - startX, startX, startX);
            if (i == 1) {
                translationXAnim = ValueAnimator.ofFloat(getWidth() - startX, startX, startX, getWidth() - startX, getWidth() - startX);
            }
            translationXAnim.setInterpolator(new LinearInterpolator());
            translationXAnim.setDuration(1600);
            translationXAnim.setRepeatCount(ValueAnimator.INFINITE);
            translationXAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    translationX[index] = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            translationXAnim.start();
            translationY[index] = startY;
            ValueAnimator translationYAnim = ValueAnimator.ofFloat(startY, startY, getHeight() - startY, getHeight() - startY, startY);
            if (i == 1) {
                translationYAnim = ValueAnimator.ofFloat(getHeight() - startY, getHeight() - startY, startY, startY, getHeight() - startY);
            }
            translationYAnim.setDuration(1600);
            translationYAnim.setInterpolator(new LinearInterpolator());
            translationYAnim.setRepeatCount(ValueAnimator.INFINITE);
            translationYAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    translationY[index] = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            translationYAnim.start();
            animators.add(translationXAnim);
            animators.add(translationYAnim);
        }

        ValueAnimator scaleAnim = ValueAnimator.ofFloat(1, 0.5f, 1, 0.5f, 1);
        scaleAnim.setDuration(1600);
        scaleAnim.setInterpolator(new LinearInterpolator());
        scaleAnim.setRepeatCount(ValueAnimator.INFINITE);
        scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scale = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        scaleAnim.start();

        ValueAnimator rotateAnim = ValueAnimator.ofFloat(0, 180, 360, 1.5f * 360, 2 * 360);
        rotateAnim.setDuration(1600);
        rotateAnim.setInterpolator(new LinearInterpolator());
        rotateAnim.setRepeatCount(ValueAnimator.INFINITE);
        rotateAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                degrees = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        rotateAnim.start();
        animators.add(scaleAnim);
        animators.add(rotateAnim);
        return animators;
    }
}