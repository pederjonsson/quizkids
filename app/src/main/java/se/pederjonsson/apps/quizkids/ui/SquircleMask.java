package se.pederjonsson.apps.quizkids.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;

/**
 * Created by Gaming on 2018-10-27.
 */

public class SquircleMask extends Squircle {

    public SquircleMask(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void doStuffInOnDraw(Canvas canvas) {
        squirclePath.setFillType(Path.FillType.INVERSE_EVEN_ODD);
        canvas.drawPath(squirclePath, mPaint);
    }
}
