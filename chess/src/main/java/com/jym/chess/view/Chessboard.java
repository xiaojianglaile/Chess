package com.jym.chess.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.jym.chess.R;

/**
 * Created by Jimmy on 2016/9/18 0018.
 */
public class Chessboard extends View {

    private final int mRow = 8;
    private final int mCol = 8;
    private final int mBorderLineWidth = 8;
    private final int mNormalLineWidth = 3;

    private Paint mBorderLinePaint;
    private Paint mNormalLinePaint;
    private Paint mTextPaint;
    private int mWidth;
    private int mHeight;
    private float mGridW;
    private float mGridH;

    public Chessboard(Context context) {
        this(context, null);
    }

    public Chessboard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Chessboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {

        mBorderLinePaint = new Paint();
        mBorderLinePaint.setAntiAlias(true);
        mBorderLinePaint.setStrokeWidth(mBorderLineWidth);
        mBorderLinePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        mBorderLinePaint.setColor(Color.parseColor("#000000"));
        mBorderLinePaint.setStyle(Paint.Style.STROKE);
        mBorderLinePaint.setTextAlign(Paint.Align.CENTER);
        mBorderLinePaint.setFakeBoldText(false);

        mNormalLinePaint = new Paint();
        mNormalLinePaint.setAntiAlias(true);
        mNormalLinePaint.setStrokeWidth(mNormalLineWidth);
        mNormalLinePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        mNormalLinePaint.setColor(Color.parseColor("#000000"));
        mNormalLinePaint.setStyle(Paint.Style.FILL);
        mNormalLinePaint.setTextAlign(Paint.Align.CENTER);
        mNormalLinePaint.setFakeBoldText(false);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStrokeWidth(mBorderLineWidth);
        mTextPaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
        mTextPaint.setColor(Color.parseColor("#000000"));
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setFakeBoldText(false);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        // 画边框
        canvas.drawRect(mGridW, mGridH, mWidth + mGridW, mHeight + mGridH, mBorderLinePaint);

        // 画内部线(横线)
        for (int i = 1; i <= mRow / 2; i++) {
            canvas.drawLine(mGridW, mGridH * i + mGridH, mWidth + mGridW, mGridH * i + mGridH, mNormalLinePaint);
            canvas.drawLine(mGridW, mHeight - mGridH * i + mGridH, mWidth + mGridW, mHeight - mGridH * i + mGridH, mNormalLinePaint);
        }

        // 画内部线(竖线)
        for (int i = 1; i <= mCol; i++) {
            canvas.drawLine(mGridW * i + mGridW, mGridH, mGridW * i + mGridW, mGridH * (mRow / 2) + mGridH, mNormalLinePaint);
            canvas.drawLine(mGridW * i + mGridW, mHeight - mGridH * (mRow / 2) + mGridH, mGridW * i + mGridW, mHeight + mGridH, mNormalLinePaint);
        }

        // 画斜线
        canvas.drawLine(mGridW * 4, mGridH, mGridW * 6, mGridH * 3, mNormalLinePaint);
        canvas.drawLine(mGridW * 6, mGridH, mGridW * 4, mGridH * 3, mNormalLinePaint);
        canvas.drawLine(mGridW * 4, mHeight + mGridH, mGridW * 6, mHeight - mGridH * 2 + mGridH, mNormalLinePaint);
        canvas.drawLine(mGridW * 6, mHeight + mGridH, mGridW * 4, mHeight - mGridH * 2 + mGridH, mNormalLinePaint);

        // 写字
        mTextPaint.setTextSize((float) (mGridH * 0.5));
        float y = mHeight / 2 + mGridH + (mGridH - getFontHeight(mTextPaint)) / 2;
        canvas.drawText(getContext().getString(R.string.chu_river), mGridW * 3, y, mTextPaint);
        canvas.drawText(getContext().getString(R.string.chinese_border), mGridW * 7, y, mTextPaint);

    }

    /**
     * 计算文字高度
     */
    private float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (float) (Math.ceil(fm.descent - fm.top) + 2);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mGridW = w / (mCol + 2);
        mGridH = (float) (h / (mRow + 3.5));
        mWidth = (int) (mGridW * mCol);
        mHeight = (int) (mGridH * (mRow + 1.5));
    }
}
