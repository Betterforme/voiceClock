package sj.com.voiceclock.drawview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import sj.com.voiceclock.R;

/**
 * Created by SJ on 2017/7/29.
 */

public class CenterRolate extends View {

    private Bitmap bitmapDisplay = null;
    private Bitmap bitmap;
    private Matrix matrix;
    private int nBitmapWidth = 0;   //  图片的宽度
    private int nBitmapHeight = 0;  //  图片的高度
    private float fAngle = 0.0f,fAngle1 = 0.0f;    //  图片旋转
    private float fScale = 0.5f;    //  图片缩放 1.0表示为原图
    private Paint paintBaGua;
    private ValueAnimator valueAnimator,valueAnimator1;
    public CenterRolate(Context context) {
        this(context,null);
    }

    public CenterRolate(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CenterRolate(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        matrix = new Matrix();
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_blue_fengche);
        nBitmapWidth = bitmap.getWidth();
        nBitmapHeight = bitmap.getHeight();
        paintBaGua = new Paint();
        paintBaGua.setFlags(Paint.ANTI_ALIAS_FLAG);
        valueAnimator = ValueAnimator.ofFloat(0,360);
        valueAnimator.setDuration(10000);
        valueAnimator.setRepeatCount(100);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                fAngle = (float)valueAnimator.getAnimatedValue();
            }
        });
        valueAnimator.start();

        valueAnimator1 = ValueAnimator.ofFloat(0,360);
        valueAnimator1.setDuration(1000);
        valueAnimator1.setRepeatCount(100);
        valueAnimator1.setInterpolator(new LinearInterpolator());
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                fAngle1 = (float)valueAnimator.getAnimatedValue();
            }
        });
        valueAnimator1.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.rotate(fAngle,getWidth()/2,getWidth()/2);

        matrix.reset();
        matrix.setTranslate(getWidth()/2-bitmap.getWidth()/2, 0);     //设置图片的旋转中心，即绕（X,Y）这点进行中心旋转
        matrix.preRotate(fAngle1, bitmap.getWidth()/2, bitmap.getWidth()/2);  //要旋转的角度
        canvas.drawBitmap(bitmap,matrix,paintBaGua);
        canvas.restore();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
            setMeasuredDimension(width,width);
    }
}
