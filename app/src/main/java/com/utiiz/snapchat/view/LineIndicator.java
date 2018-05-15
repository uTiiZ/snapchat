//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.utiiz.snapchat.view;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.support.annotation.ColorInt;
import android.view.animation.LinearInterpolator;
import com.kekstudio.dachshundtablayout.DachshundTabLayout;
import com.kekstudio.dachshundtablayout.indicators.AnimatedIndicatorInterface;
import com.utiiz.snapchat.helper.Snapchat;

public class LineIndicator implements AnimatedIndicatorInterface, AnimatorUpdateListener {
    private Paint paint;
    private RectF rectF;
    private Rect rect;
    private int height;
    private int edgeRadius;
    private int leftX;
    private int rightX;
    private ValueAnimator valueAnimatorLeft;
    private ValueAnimator valueAnimatorRight;
    private LinearInterpolator linearInterpolator;
    private TabLayoutIndicator tabLayoutIndicator;
    private Context context;

    public LineIndicator(TabLayoutIndicator tabLayoutIndicator) {
        this.tabLayoutIndicator = tabLayoutIndicator;
        this.linearInterpolator = new LinearInterpolator();
        this.valueAnimatorLeft = new ValueAnimator();
        this.valueAnimatorLeft.setDuration(500L);
        this.valueAnimatorLeft.addUpdateListener(this);
        this.valueAnimatorLeft.setInterpolator(this.linearInterpolator);
        this.valueAnimatorRight = new ValueAnimator();
        this.valueAnimatorRight.setDuration(500L);
        this.valueAnimatorRight.addUpdateListener(this);
        this.valueAnimatorRight.setInterpolator(this.linearInterpolator);
        this.rectF = new RectF();
        this.rect = new Rect();
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.paint.setStyle(Style.FILL);
        this.leftX = (int) tabLayoutIndicator.getChildXLeft(tabLayoutIndicator.getCurrentPosition());
        this.rightX = (int) tabLayoutIndicator.getChildXRight(tabLayoutIndicator.getCurrentPosition());
        this.edgeRadius = -1;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setEdgeRadius(int edgeRadius) {
        this.edgeRadius = edgeRadius;
        this.tabLayoutIndicator.invalidate();
    }

    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.leftX = (Integer)this.valueAnimatorLeft.getAnimatedValue();
        this.rightX = (Integer)this.valueAnimatorRight.getAnimatedValue();
        this.rect.top = this.tabLayoutIndicator.getHeight() - this.height;
        this.rect.left = this.leftX + this.height / 2;
        this.rect.right = this.rightX - this.height / 2;
        this.rect.bottom = this.tabLayoutIndicator.getHeight();
        this.tabLayoutIndicator.invalidate(this.rect);
    }

    public void setSelectedTabIndicatorColor(@ColorInt int color) {
        this.paint.setColor(color);
    }

    public void setSelectedTabIndicatorHeight(int height) {
        this.height = height;
        if (this.edgeRadius == -1) {
            this.edgeRadius = height;
        }

    }

    public void setIntValues(int startXLeft, int endXLeft, int startXCenter, int endXCenter, int startXRight, int endXRight) {
        this.valueAnimatorLeft.setIntValues(new int[]{startXLeft, endXLeft});
        this.valueAnimatorRight.setIntValues(new int[]{startXRight, endXRight});
    }

    public void setCurrentPlayTime(long currentPlayTime) {
        this.valueAnimatorLeft.setCurrentPlayTime(currentPlayTime);
        this.valueAnimatorRight.setCurrentPlayTime(currentPlayTime);
    }

    public void draw(Canvas canvas) {
        this.rectF.top = (float)(this.tabLayoutIndicator.getHeight() - this.height) - Snapchat.DPToPixel(5.5F, context);
        this.rectF.left = (float)(this.leftX + this.height / 2) + Snapchat.DPToPixel(5.0F, context);
        this.rectF.right = (float)(this.rightX - this.height / 2) - Snapchat.DPToPixel(5.0F, context);
        this.rectF.bottom = this.rectF.top + this.height;
        canvas.drawRoundRect(this.rectF, (float)this.edgeRadius, (float)this.edgeRadius, this.paint);
    }

    public long getDuration() {
        return this.valueAnimatorLeft.getDuration();
    }
}
