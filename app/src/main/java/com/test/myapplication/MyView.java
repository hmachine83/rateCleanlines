package com.test.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {
    private Rect BoxRounds = new Rect();
    private Paint BoxPaint = new Paint();
    private Paint BoxFill = new Paint();
    int BarHeight = 96;
    int colorBlueRect = 0xff8faadc;
    int colorBlueShade = 0xfbe5d6;
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs){

        if(attrs != null){
            TypedArray ta = getContext().obtainStyledAttributes(attrs,R.styleable.MyView);

           colorBlueShade = ta.getColor(R.styleable.MyView_bck_color,0xfbe5d6);
           ta.recycle();
        }
        BoxPaint.setColor(colorBlueRect);
        BoxPaint.setStrokeWidth(4f);
        BoxFill.setColor(colorBlueShade);
        BoxFill.setStyle(Paint.Style.FILL);
        BoxPaint.setStyle(Paint.Style.STROKE);


    }
   public  void updateBacground(){
        colorBlueRect = 0xff8faadc;
       BoxPaint.setColor(colorBlueRect);
        colorBlueShade = 0xfffbe5d6;
       BoxFill.setColor(colorBlueShade);
        this.invalidate();
    }
    public  void removeFrame(){
        colorBlueRect = 0x8faadc;
        BoxPaint.setColor(colorBlueRect);
        this.invalidate();
    }
    public  void removeBacground(){
        colorBlueShade = 0xfbe5d6;
        BoxFill.setColor(colorBlueShade);
        this.invalidate();
    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawRect(BoxRounds,BoxFill);
        canvas.drawRect(BoxRounds,BoxPaint);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        BoxRounds = new Rect(0,0,w,h);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int offset = (int)(BoxPaint.getStrokeWidth()/2);
        BoxRounds.top = this.getPaddingTop() + offset;
        BoxRounds.left =  this.getPaddingStart()+ offset;
        BoxRounds.right =this.getMeasuredWidth() - this.getPaddingEnd() - offset;
        BoxRounds.bottom = this.getMeasuredHeight() - this.getPaddingBottom() - offset;

    }
}
