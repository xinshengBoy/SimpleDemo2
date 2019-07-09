package com.yks.simpledemo2.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.yks.simpledemo2.R;
import com.yks.simpledemo2.tools.Info;

/**
 * 描述：自定义图片缩放
 * 作者：zzh
 * time:2019/07/04
 */
public class ScaleImageActivity extends Activity {
    private Context mContext = ScaleImageActivity.this;
    private Activity mActivity = ScaleImageActivity.this;

    private ImageView iv_scale_image;
    private int mode = 0;//记录模式
    private static final int DRAG = 1;//拖拉模式
    private static final int ZOOM = 2;//缩放模式
    private PointF startPoint = new PointF();//PointF(浮点对)
    private Matrix matrix = new Matrix();//矩阵对象
    private Matrix currentMatrix = new Matrix();//存放照片当前的矩阵
    private float startDis;//开始距离
    private PointF midPoint;//中心点
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_scale_image);

        initView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        Info.setActionBar(mActivity,R.id.headerLayout,"自定义图片缩放");

        iv_scale_image = findViewById(R.id.iv_scale_image);
        iv_scale_image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //参考文档：https://www.jb51.net/article/131609.htm
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_DOWN://手指下压
                        mode = DRAG;
                        currentMatrix.set(iv_scale_image.getImageMatrix());//记录图片当前移动的位置
                        startPoint.set(motionEvent.getX(),motionEvent.getY());
                        break;
                    case MotionEvent.ACTION_MOVE://手指移动
                        if (mode == DRAG){//拖拉模式
                            float dx = motionEvent.getX() - startPoint.x;//得到在X轴的移动距离
                            float dy = motionEvent.getY() - startPoint.y;//得到在Y轴的移动距离
                            matrix.set(currentMatrix);//设置初始位置
                            matrix.postTranslate(dx,dy);//实现位置移动
                        }else if (mode == ZOOM){//缩放模式
                            float endDis = distance(motionEvent);
                            if (endDis > 5f){//防止不规则手指触碰
                                float scale = endDis / startDis;//获得缩放倍数
                                if (scale > 5f){
                                    scale = 5f;
                                }else if (scale < 0.2){
                                    scale = 0.2f;
                                }
                                matrix.set(currentMatrix);
                                matrix.postScale(scale,scale,midPoint.x,midPoint.y);//在原来的基础上缩放
                            }
                        }
                        break;
                    case  MotionEvent.ACTION_UP:
                        //手指离开屏幕
                        break;
                    case MotionEvent.ACTION_POINTER_UP:////当屏幕上已经有手指离开屏幕，屏幕上还有一个手指，就会触发这个事件
                        mode = 0;
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN://当屏幕上已经有触点(手指)，再有一个手指按下屏幕，就会触发这个事件
                        mode = ZOOM;
                        startDis = distance(motionEvent);
                        if (startDis > 5f){//防止不规则触碰
                            midPoint = getMiddle(motionEvent);
                            currentMatrix.set(iv_scale_image.getImageMatrix());
                        }
                        break;
                    default:
                        break;
                }
                iv_scale_image.setImageMatrix(matrix);//改变图片的矩阵位置
                return true;
            }
        });

        iv_scale_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_scale_image.setScaleType(ImageView.ScaleType.CENTER);
            }
        });

    }

    /**
     * 描述：计算两点之间的距离，利用勾股定理
     * 作者：zzh
     * @param event 对象
     * @return 移动的距离
     */
    private float distance(MotionEvent event){
        float dx = event.getX(1) - event.getX(0);
        float dy = event.getY(1) - event.getY(0);
        return (float) Math.sqrt(dx*dx + dy*dy);
    }

    /**
     * 描述：获取两点之间的中心点
     * @param event 对象
     * @return 中心点
     */
    private PointF getMiddle(MotionEvent event){
        float midX = (event.getX(1)) + event.getX(0) / 2;
        float midY = (event.getY(1)) + event.getY(0) / 2;
        return  new PointF(midX,midY);
    }
}
