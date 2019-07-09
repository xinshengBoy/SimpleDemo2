package com.yks.simpledemo2.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.OvershootInterpolator;

/**
 * 描述：自定义仪表盘
 * 作者：zzh
 * time:2019/07/06
 */
public class DashBoardView extends View {
    private Paint paint,tmpPaint,textPaint,strokePaint;
    private RectF rect;
    private int backgroundColor;//背景色
    private float length;//仪表盘半径
    private float pointLength;//指针长度
    private float per;//指数百分比
    private float perPoint;//缓存变化中的指针百分比
    private float perOld;//变化前的指针百分比
    private float r;


    public DashBoardView(Context context) {
        super(context);
        init();
    }

    public DashBoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DashBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs ,defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        rect = new RectF();
        tmpPaint = new Paint();
        textPaint = new Paint();
        strokePaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width / 2 / 4 * 5;
        initIndex(width / 2);
        setMeasuredDimension(width,height);//优化组件高度
    }

    /**
     * 描述：初始化参数值
     * 作者：zzh
     * @param specSize 半径
     */
    private void initIndex(int specSize) {
        backgroundColor = Color.WHITE;
        r = specSize;
        length = r / 4 * 3;
        pointLength = -(float)(r*0.6);
        per = 0;
        perOld = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setLayerType(LAYER_TYPE_SOFTWARE,null);//软件加速
        //颜色指示环
        initRing(canvas);
        //刻度文字
        initScale(canvas);
        //指针
        initPointer(canvas);
        //提示内容
        initText(canvas);
    }

    private void initRing(Canvas canvas) {
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);
        canvas.save();
        canvas.translate(canvas.getWidth()/2,r);

        //前一百红白渐变圆环
        paint.setStyle(Paint.Style.FILL);
        int[] colors = {Color.parseColor("#F95A37"), Color.parseColor("#f9cf45"), Color.parseColor("#00ff00")};
        float[] positions = {0.5f - 10f/180f * 0.5f, 0.5f + 0.5f * 5f / 6f, 1.0f};
        SweepGradient sweepGradient = new SweepGradient(0,0,colors,positions);
        paint.setShader(sweepGradient);
        rect = new RectF(-length,-length,length,length);
        canvas.drawArc(rect,170,10f+180f/6f*5f,true,paint);

        //100之后颜色渐变
        paint.setStyle(Paint.Style.FILL);
        canvas.rotate(10f,0f,0f);
        int[] colors2 = {Color.parseColor("#79D062"), Color.parseColor("#3FBF55")};
        float[] positions2 = {0.5f+0.5f*(144f/180f),1.0f};
        SweepGradient sweepGradient2 = new SweepGradient(0,0,colors2,positions2);
        paint.setShader(sweepGradient2);
        rect = new RectF(-length,-length,length,length);
        canvas.drawArc(rect,180f+180f*(140f/180f),180f/6+10,true,paint);

        canvas.restore();
        canvas.save();
        canvas.translate(canvas.getWidth()/2,r);

        strokePaint = new Paint();
        strokePaint.setColor(0x3f979797);
        strokePaint.setStrokeWidth(10);
        strokePaint.setShader(null);
        strokePaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(rect,170,200,true,strokePaint);

        canvas.restore();
        canvas.save();
        canvas.translate(canvas.getWidth()/2,r);

        paint.setShader(null);//底边水平
        paint.setColor(backgroundColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(-length  , (float) (Math.sin(Math.toRadians(10) ) * length /3f * 2f), length  ,  (float) (Math.sin(Math.toRadians(10)) * length  + 100) , paint);
        canvas.drawRect(-length , (float) (Math.sin(Math.toRadians(10) ) * length /3f * 2f), length , (float) (Math.sin(Math.toRadians(10) ) * length /3f * 2f) , strokePaint);

        //内部背景色填充
        paint.setColor(backgroundColor);
        paint.setShader(null);
        rect = new RectF( - (length - length / 3f  - 2), -(length / 3f * 2f - 2), length - length / 3f -2 , length / 3f * 2f - 2);
        canvas.drawArc(rect, 170, 200, true, strokePaint);
        canvas.drawArc(rect, 0, 360, true, paint);

    }

    private void initScale(Canvas canvas) {
        canvas.restore();
        canvas.save();
        canvas.translate(canvas.getWidth()/2, r);
        paint.setColor(Color.parseColor("#999999"));

        tmpPaint = new Paint(paint); //小刻度画笔对象
        tmpPaint.setStrokeWidth(1);
        tmpPaint.setTextSize(16);
        tmpPaint.setTextAlign(Paint.Align.CENTER);

        canvas.rotate(-90,0f,0f);

        float  y = length;
        y = - y;
        int count = 12; //总刻度数
        paint.setColor(backgroundColor);

        float tempRou = 180 / 12f;

        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(2);

        //绘制刻度和百分比
        for (int i = 0 ; i <= count ; i++){

            if (i % 2 == 0 ) {
                canvas.drawText(String.valueOf((i) * 10), 0, y - 20f, tmpPaint);
            }

            canvas.drawLine(0f, y , 0, y + length / 15, paint);
            canvas.rotate(tempRou,0f,0f);
        }
    }

    private void initPointer(Canvas canvas) {
        paint.setColor(Color.BLACK);

        canvas.restore();
        canvas.save();
        canvas.translate(canvas.getWidth()/2, r);
        float change;

        if (perPoint < 1 ){
            change = perPoint * 180;
        }else {
            change = 180;
        }

        //根据参数得到旋转角度
        canvas.rotate(-90 + change,0f,0f);

        //绘制三角形形成指针
        Path path = new Path();
        path.moveTo(0 , pointLength);
        path.lineTo(-10 , 0);
        path.lineTo(10,0);
        path.lineTo(0 , pointLength);
        path.close();

        canvas.drawPath(path, paint);
    }

    private void initText(Canvas canvas) {
        //抗锯齿
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
        canvas.restore();
        canvas.save();
        canvas.translate(canvas.getWidth()/2, r);

        float rIndex = length ;

        //设置文字展示的圆环
        paint.setColor(Color.parseColor("#eeeeee"));
        paint.setShader(null);
        paint.setShadowLayer(5, 0, 0, 0x54000000);
        rect = new RectF( - (rIndex/ 3 ), - (rIndex / 3), rIndex / 3, rIndex / 3);
        canvas.drawArc(rect, 0, 360, true, paint);

        paint.clearShadowLayer();

        canvas.restore();
        canvas.save();
        canvas.translate(canvas.getWidth()/2f , r);


        textPaint.setStrokeWidth(1);
        textPaint.setAntiAlias(true);

        textPaint.setTextSize(20);
        textPaint.setColor(Color.parseColor("#fc6555"));
        textPaint.setTextAlign(Paint.Align.CENTER);


        //判断指数变化及颜色设定
        if (per < 60){
            textPaint.setColor(Color.parseColor("#ff6450"));
        }else if (per < 100) {
            textPaint.setColor(Color.parseColor("#f5a623"));
        }else {
            textPaint.setColor(Color.parseColor("#79d062"));
        }

        float swidth = textPaint.measureText(String.valueOf(per));
        //计算偏移量 是的数字和百分号整体居中显示
        swidth =   (swidth - (swidth + 22) / 2);


        canvas.translate( swidth , 0);
        canvas.drawText("" + (int)per, 0, 0, textPaint);

//        textPaint.setTextSize(18);
//        textPaint.setTextAlign(Paint.Align.LEFT);
//
//        canvas.drawText("%" , 0, 0, textPaint);
//        textPaint.setTextAlign(Paint.Align.CENTER);
//        textPaint.setColor(Color.parseColor("#999999"));
//
//
        canvas.restore();
        canvas.save();
        canvas.translate(canvas.getWidth()/2  , r + length / 3 /2 );
//        canvas.drawText("完成率" , 0, 0, textPaint);
    }

    public void setBackGroundColor(int color){
        this.backgroundColor = color;
    }

    public void setPointLength1(float pointLength1){
        this.pointLength = -length * pointLength1 ;
    }

    public void cgangePer(float per ){
        this.perOld = this.per;
        this.per = per;
        ValueAnimator va =  ValueAnimator.ofFloat(perOld,per);
        va.setDuration(1000);
        va.setInterpolator(new OvershootInterpolator());
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                perPoint = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        va.start();

    }
}
