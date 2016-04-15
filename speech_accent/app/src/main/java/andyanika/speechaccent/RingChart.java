package andyanika.speechaccent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by kolpakov on 12/04/16.
 */
public class RingChart extends View {
    private static final int START_ANGLE_POINT = -90;

    private Paint valuePaint;
    private Paint backgroundPaint;
    private RectF rect;
    private float angle = 36;

    private int strokeWidth = 10;
    private int desiredSize = 100;

    public RingChart(Context context, AttributeSet attrs) {
        super(context, attrs);

        desiredSize = (int) (desiredSize * getResources().getDisplayMetrics().density);
        strokeWidth = (int) (strokeWidth * getResources().getDisplayMetrics().density);

        backgroundPaint = createColor(strokeWidth, getResources().getColor(R.color.colorPrimary));
        valuePaint = createColor(strokeWidth, getResources().getColor(R.color.colorAccent));
        rect = new RectF(strokeWidth, strokeWidth, desiredSize - strokeWidth, desiredSize - strokeWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = getMeasuredWidth();
        int heightSize = getMeasuredHeight();
        int width = desiredSize;
        int height = desiredSize;

        switch (View.MeasureSpec.getMode(widthMeasureSpec)) {
            case View.MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            case View.MeasureSpec.AT_MOST:
                width = Math.min(desiredSize, widthSize);
                break;
        }

        switch (View.MeasureSpec.getMode(heightMeasureSpec)) {
            case View.MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case View.MeasureSpec.AT_MOST:
                height = Math.min(desiredSize, heightSize);
                break;
        }

        int size = width > height ? height : width;
        rect.right = size - strokeWidth;
        rect.bottom = size - strokeWidth;
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawArc(rect, START_ANGLE_POINT, 360, false, backgroundPaint);
        canvas.drawArc(rect, START_ANGLE_POINT, angle, false, valuePaint);
    }

    public void setProgress(int percent) {
        if (percent < 0) {
            percent = 0;
        } else if (percent > 100) {
            percent = 100;
        }

        angle = 3.6f * percent;
        requestLayout();
    }

    protected Paint createColor(int strokeWidth, int color) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        if (strokeWidth != 0) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(strokeWidth);
        } else {
            paint.setStyle(Paint.Style.FILL);
        }
        paint.setColor(color);
        return paint;
    }
}

