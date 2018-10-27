package se.pederjonsson.apps.quizkids.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Gaming on 2018-10-27.
 */

public class Squircle extends View {


    protected Path squirclePath;
    protected Paint mPaint;

    public Squircle(Context context, AttributeSet attrs) {
        super(context, attrs);
       // setColor(context.obtainStyledAttributes(attrs, R.styleable.telavox_view_color));
    }

    protected int mColor = Color.GREEN;

    private void setColor(TypedArray typedArray) {
       // mColor = typedArray.getColorStateList(R.styleable.telavox_view_color_color);
        //typedArray.recycle();
    }

    protected int widthSize = 0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        squirclePath = getSquirclePaath(2, 2, (widthSize / 2) - 2);
        setPaints();
        doStuffInOnDraw(canvas);
    }

    protected void setPaints(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mColor);
    };

    protected void doStuffInOnDraw(Canvas canvas){
        canvas.drawPath(squirclePath, mPaint);
    }

    protected Path getSquirclePaath(int left, int top, int radius) {
        final double radiusToPow = radius * radius * radius;

        Path path = new Path();
        path.moveTo(-radius, 0);
        for (int x = -radius; x <= radius; x++)
            path.lineTo(x, ((float) Math.cbrt(radiusToPow - Math.abs(x * x * x))));
        for (int x = radius; x >= -radius; x--)
            path.lineTo(x, ((float) -Math.cbrt(radiusToPow - Math.abs(x * x * x))));
        path.close();

        Matrix matrix = new Matrix();
        matrix.postTranslate(left + radius, top + radius);
        path.transform(matrix);

        return path;
    }
}
