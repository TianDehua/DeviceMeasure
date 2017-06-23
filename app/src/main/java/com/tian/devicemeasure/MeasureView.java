package com.tian.devicemeasure;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author TianDehua
 */

public class MeasureView extends View {

    private Paint mPaint;
    private int mWidth;
    private int mHeight;

    public MeasureView( Context context ) {
        this( context, null );
    }

    public MeasureView( Context context, AttributeSet attrs ) {
        this( context, attrs, 0 );
    }

    public MeasureView( Context context, AttributeSet attrs, int defStyleAttr ) {
        super( context, attrs, defStyleAttr );
        initView( );
    }

    private void initView( ) {
        mPaint = new Paint( );
        mPaint.setColor( getResources( ).getColor( android.R.color.darker_gray) );
        mPaint.setTextSize( 16 );
    }

    @Override
    protected void onSizeChanged( int w, int h, int oldw, int oldh ) {
        super.onSizeChanged( w, h, oldw, oldh );
        mWidth = w;
        mHeight = h;
    }
    
    private void drawLineAndUnit( Canvas canvas, int maxLenght ) {
        mPaint.setStrokeWidth( 3 );
        final float scale = getContext( ).getResources( ).getDisplayMetrics( ).density;
        canvas.drawText( "1dp="+scale+",totalDp="+px2dp( getContext(), maxLenght ), 150, 50, mPaint ); //总的dp
        canvas.drawLine( 0, 0, maxLenght, 0, mPaint ); // 画线
        int tenDp = dp2px( getContext( ), 20 );
        int totalWidth = 0;
        int index = 0;
        while ( totalWidth < maxLenght ) {
            totalWidth += tenDp;
            canvas.translate( tenDp, 0 );
            canvas.drawLine( 0, 0, 0, -5, mPaint ); // 画单元格
            canvas.drawText( String.valueOf( ++index ), 0, -10, mPaint ); // 标注
        }
        if(totalWidth >= maxLenght) {
            int yetValue = totalWidth - maxLenght + 2;
            canvas.translate( -yetValue, 0 );
            canvas.drawLine( 0, 0, 0, -5, mPaint ); // 画最后单元格
            canvas.drawText( String.valueOf(  px2dp( getContext(), maxLenght ) ) , -30, -30, mPaint ); // 标注最后一个
        }
    }

    @Override
    protected void onDraw( Canvas canvas ) {
        canvas.save( );
        canvas.translate( 0, 100 );
        drawLineAndUnit( canvas, mWidth );
        canvas.restore( );
        canvas.rotate( 90 );
        canvas.translate( 0, -100 );
        drawLineAndUnit( canvas, mHeight );
    }

    public int dp2px( Context context, float dipValue ) {
        final float scale = context.getResources( ).getDisplayMetrics( ).density;
        return (int) ( dipValue * scale + 0.5f );
    }

    public int px2dp( Context context, float pxValue ) {
        final float scale = context.getResources( ).getDisplayMetrics( ).density;
        return (int) ( pxValue / scale + 0.5f );
    }

}
