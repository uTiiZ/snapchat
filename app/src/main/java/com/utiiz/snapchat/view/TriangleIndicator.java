package com.utiiz.snapchat.view;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.view.animation.LinearInterpolator;

import com.kekstudio.dachshundtablayout.DachshundTabLayout;
import com.kekstudio.dachshundtablayout.indicators.AnimatedIndicatorInterface;
import com.utiiz.snapchat.R;
import com.utiiz.snapchat.helper.Snapchat;

/**
 * Created by Andy671
 */

public class TriangleIndicator implements AnimatedIndicatorInterface, ValueAnimator.AnimatorUpdateListener{

    private Paint paint;
    private Path path;
    private Rect rect;
    private Context context;

    private int height;

    private ValueAnimator valueAnimator;

    private TabLayoutIndicator tabLayoutIndicator;

    private int frameX;

    public TriangleIndicator(TabLayoutIndicator tabLayoutIndicator) {
        this.tabLayoutIndicator = tabLayoutIndicator;

        valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(DEFAULT_DURATION);
        valueAnimator.addUpdateListener(this);

        /*paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);*/

        paint = new Paint();

        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        rect = new Rect();

        frameX = (int) tabLayoutIndicator.getChildXCenter(tabLayoutIndicator.getCurrentPosition());
    }

    public void setInterpolator(TimeInterpolator interpolator){
        valueAnimator.setInterpolator(interpolator);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        frameX = (int) valueAnimator.getAnimatedValue();

        rect.left = frameX - height / 2;
        rect.right = frameX + height / 2;
        rect.top = tabLayoutIndicator.getHeight() - height;
        rect.bottom = tabLayoutIndicator.getHeight();

        tabLayoutIndicator.invalidate(rect);
    }

    @Override
    public void setSelectedTabIndicatorColor(@ColorInt int color) {
        paint.setColor(color);
    }

    @Override
    public void setSelectedTabIndicatorHeight(int height) {
        this.height = height;
    }

    @Override
    public void setIntValues(int startXLeft, int endXLeft,
                             int startXCenter, int endXCenter,
                             int startXRight, int endXRight) {
        valueAnimator.setIntValues(startXCenter, endXCenter);
    }

    @Override
    public void setCurrentPlayTime(long currentPlayTime) {
        valueAnimator.setCurrentPlayTime(currentPlayTime);
    }

    @Override
    public void draw(Canvas canvas) {
        /*Drawable drawable = context.getDrawable(R.drawable.ic_indicator_triangle);
        Bitmap icon = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.drawBitmap(icon, new Matrix(), paint);
        drawable.setBounds(frameX, 0, (int) Snapchat.DPToPixel(25.6F, context), (int) Snapchat.DPToPixel(8.35F, context));
        drawable.draw(canvas);

        canvas.drawCircle(frameX, canvas.getHeight() - height/2, height/2, paint);*/

        Point a = new Point(frameX - (int) Snapchat.DPToPixel(5F, context), canvas.getHeight());
        Point b = new Point(frameX, canvas.getHeight() - (int) Snapchat.DPToPixel(3F, context));
        Point c = new Point(frameX + (int) Snapchat.DPToPixel(5F, context), canvas.getHeight());

        path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.lineTo(b.x, b.y);
        path.lineTo(c.x, c.y);
        path.lineTo(a.x, a.y);
        path.lineTo(b.x, b.y);

        canvas.drawPath(path, paint);
    }

    @Override
    public long getDuration() {
        return valueAnimator.getDuration();
    }

    public void setContext(Context context) {
        this.context = context;
    }

}