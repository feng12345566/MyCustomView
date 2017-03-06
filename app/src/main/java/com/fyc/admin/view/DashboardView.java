package com.fyc.admin.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.fyc.admin.utils.common.SizeUtil;

/**
 * Created by Admin on 2017/3/5.
 */

public class DashboardView extends View {
    private int width;
    private int height;
    private Paint paint;
    private int padding = 10;
    private int minValue = 0;
    private int maxValue = 240;
    int lineWidth = 0;
    int mRadius = 0;
    private int value = 0;
    private int oldValue = 0;
    private int currentValue = 0;
    private String unitText = "km/h";
    public DashboardView(Context context) {
        super(context);
        init(context);
    }

    public DashboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DashboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DashboardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        post(runnable);
    }


    public String getUnitText() {
        return unitText;
    }

    public void setUnitText(String unitText) {
        this.unitText = unitText;
        invalidate();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (value < currentValue) {
                currentValue -= 2;
                if (currentValue > value) {
                    invalidate();
                    postDelayed(this, 100);
                }
            } else if (value > currentValue) {
                currentValue += 2;
                if (currentValue < value) {
                    invalidate();
                    postDelayed(this, 100);
                }
            }
        }
    };

    private void init(Context context) {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#10DEDE"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        canvas.save();
        int w = Math.min(width - getPaddingLeft() - getPaddingRight(), height - getPaddingBottom() - getPaddingTop());
        int centerX = width / 2;
        int centerY = height / 2;
        mRadius = w / 2;
        drawUnit(canvas);
        canvas.translate(centerX, centerY);
        canvas.drawCircle(0, 0, w / 2, paint);
        canvas.rotate(-120);
        drawScale(canvas);
        drawPointer(canvas);

    }


    private void drawUnit(Canvas canvas) {
        if (TextUtils.isEmpty(unitText)) {
            return;
        }
        paint.setTextSize(60);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(SizeUtil.Dp2Px(getContext(), 1.5f));
        paint.setColor(Color.parseColor("#10DEDE"));
        Rect textBound = new Rect();
        paint.getTextBounds(unitText, 0, unitText.length(), textBound);
        canvas.drawText(unitText, width / 2 + (textBound.left - textBound.right) / 2, height / 2 - mRadius * 1.0f / 2, paint);
        canvas.save();
    }


    private void drawScale(Canvas canvas) {
        for (int s = 0; s < (maxValue - minValue) / 10 + 1; s++) {
            if (s % 2 == 0) { //整点
                paint.setStrokeWidth(SizeUtil.Dp2Px(getContext(), 1.5f));
                lineWidth = 40;
                paint.setTextSize(40);
                String text = s * 10 + "";
                Rect textBound = new Rect();
                paint.getTextBounds(text, 0, text.length(), textBound);
                paint.setColor(Color.parseColor("#10DEDE"));
                canvas.save();
                canvas.translate(0, -mRadius + SizeUtil.Dp2Px(getContext(), 5.0f) + lineWidth + 10 + (textBound.bottom - textBound.top) / 2);
                paint.setStyle(Paint.Style.FILL);
                canvas.rotate(120 - 10 * s);
                canvas.drawText(text, -(textBound.right + textBound.left) / 2 - 10, -(textBound.bottom + textBound.top) / 2, paint);
                canvas.restore();
            } else { //非整点
                lineWidth = 20;
                paint.setStrokeWidth(SizeUtil.Dp2Px(getContext(), 1));
            }
            canvas.drawLine(0, -mRadius + 10, 0, -mRadius + 10 + lineWidth, paint);
            canvas.rotate(10);
        }
    }

    private void drawPointer(Canvas canvas) {
        canvas.rotate(-250 + currentValue * 240 / (maxValue - minValue));
        float pointerWidth = SizeUtil.Dp2Px(getContext(), 4f);
        RectF rectFSecond = new RectF(-pointerWidth / 2, -mRadius + 20, pointerWidth / 2, 0);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(pointerWidth);
        canvas.drawRoundRect(rectFSecond, mRadius, mRadius, paint);

        //绘制中心小圆
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(0, 0, mRadius * 0.06f, paint);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}
