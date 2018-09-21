package com.yks.simpledemo2.widget;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.yks.simpledemo2.bean.ItemEmoje;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 描述：表情雨
 * 作者：zzh
 * time:2018/09/20
 * 参考网址：http://www.apkbus.com/blog-691662-78656.html
 */

public class RainView extends View {

    private Paint paint;
    private Matrix matrix;
    private Random random;
    private List<ItemEmoje> bitmapList;
    private int imgId;//表情图片资源
    private int mCount;//要显示表情的个数
    private boolean isRun;//是否运行，默认没有运行

    public RainView(Context context) {
        this(context,null);
    }

    public RainView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RainView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);//防锯齿
        paint.setFilterBitmap(true);//对位图进行滤波处理
        paint.setDither(true);//防止抖动

        matrix = new Matrix();//矩阵工具类
        random = new Random();//随机工具
        bitmapList = new ArrayList<>();
    }

    /**
     * 描述：暴露的接口，用于设置图片资源和要显示的数量
     * @param imgId 图片资源
     * @param count 要显示的数量
     */
    public void setImgId(int imgId,int count){
        this.imgId = imgId;
        this.mCount = count;
    }

    /**
     * 描述：初始化数据
     * 作者：zzh
     */
    private void initData(){
        release();//先清空之前的数据
        for (int i=0;i<mCount;i++){
            ItemEmoje emoje = new ItemEmoje();
            emoje.bitmap = BitmapFactory.decodeResource(this.getResources(),imgId);//设置图片资源
            //起始横坐标在【100，getWidth()-100】之间
            emoje.x = random.nextInt(this.getWidth() - 200) + 100;
            //起始纵坐标在（-getHeight(),0）质检，即从屏幕的左上角开始
            emoje.y = -random.nextInt(this.getHeight());
            //横向偏移[-2,2]质检，左右摇摆
            emoje.offsetX = random.nextInt(4) - 2;
            //纵向固定下落
            emoje.offsetY = 12;
            //缩放比例（0.8,1.2）之间
            emoje.scale = (float)(random.nextInt(40) + 80) / 100f;
            bitmapList.add(emoje);
        }
    }

    /**
     * 描述：开始的方法
     * 作者：zzh
     * @param isRun 是否要运行
     */
    public void start(boolean isRun){
        this.isRun = isRun;
        initData();
        postInvalidate();//刷新view
    }

    /**
     * 描述：释放资源
     * 作者：zzh
     */
    private void release(){
        if (bitmapList != null && bitmapList.size() > 0){
            for (ItemEmoje emoje : bitmapList){
                if (!emoje.bitmap.isRecycled()){//判断图片资源是否已回收
                    emoje.bitmap.recycle();
                }
            }
            bitmapList.clear();//清空list缓存
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isRun){
            boolean isInScreen = false;//判断表情是否在屏幕内
            for (int i=0;i<bitmapList.size();i++){
                matrix.reset();
                //缩放
                matrix.setScale(bitmapList.get(i).scale,bitmapList.get(i).scale);
                //下落过程坐标
                bitmapList.get(i).x = bitmapList.get(i).x + bitmapList.get(i).offsetX;
                bitmapList.get(i).y = bitmapList.get(i).y + bitmapList.get(i).offsetY;
                if (bitmapList.get(i).y <= getHeight()){//判断表情是否还在屏幕内
                    isInScreen = true;
                }
                //位移
                matrix.postTranslate(bitmapList.get(i).x,bitmapList.get(i).y);
                canvas.drawBitmap(bitmapList.get(i).bitmap,matrix,paint);
            }

            if (isInScreen){
                postInvalidate();
            }else {
                release();
            }
        }
    }
}
