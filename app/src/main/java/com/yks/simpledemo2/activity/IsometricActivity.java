package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yks.simpledemo2.R;
import com.yks.simpledemo2.tools.Info;

import io.fabianterhorst.isometric.Color;
import io.fabianterhorst.isometric.IsometricView;
import io.fabianterhorst.isometric.Path;
import io.fabianterhorst.isometric.Point;
import io.fabianterhorst.isometric.shapes.Prism;

/**
 * 描述：绘制几何图形
 * 作者：zzh
 * 参考网址：https://github.com/FabianTerhorst/Isometric
 * Created by admin on 2018/8/21.
 */

public class IsometricActivity extends Activity {

    private IsometricView isometric1,isometric2,isometric3,isometric4,isometric5;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_isometric);

        initView();
    }

    private void initView() {
        Info.setActionBar(IsometricActivity.this,R.id.headerLayout,"几何图形");

        isometric1 = findViewById(R.id.view_isometric1);
        isometric2 = findViewById(R.id.view_isometric2);
        isometric3 = findViewById(R.id.view_isometric3);
        isometric4 = findViewById(R.id.view_isometric4);
        isometric5 = findViewById(R.id.view_isometric5);

        createIso();
    }

    private void createIso() {
        isometric1.clear();
        isometric2.clear();
        isometric3.clear();
        isometric4.clear();
        isometric5.clear();
        isometric1.add(new Prism(Point.ORIGIN, 3, 3, 1), new Color(50, 60, 160));
        isometric1.add(new Path(new Point[]{
                new Point(1, 1, 1),
                new Point(2, 1, 1),
                new Point(2, 2, 1),
                new Point(1, 2, 1)
        }), new Color(50, 160, 60));
        isometric2.add(
                new Prism(
                        new Point(/* x */ 0, /* y */ 0, /* z */ 0),
		/* width */ 1, /* length */ 1, /* height */ 1
                ),
                new Color(33, 150, 243)
        );
        isometric3.add(new Prism(new Point(0, 0, 0)), new Color(33, 150, 243));
        isometric3.add(new Prism(new Point(-1, 1, 0), 1, 2, 1), new Color(33, 150, 243));
        isometric3.add(new Prism(new Point(1, -1, 0), 2, 1, 1), new Color(33, 150, 243));

        isometric4.add(new Prism(Point.ORIGIN, 3, 3, 1), new Color(50, 60, 160));
        isometric4.add(new Path(new Point[]{
                new Point(1, 1, 1),
                new Point(2, 1, 1),
                new Point(2, 2, 1),
                new Point(1, 2, 1)
        }), new Color(50, 160, 60));
        Color blue = new Color(50, 60, 160);
        Color red = new Color(160, 60, 50);
        Prism cube = new Prism(Point.ORIGIN, 3, 3, 1);
        isometric5.add(cube, red);
        isometric5.add(cube
	/* (1.5, 1.5) is the center of the prism */
                .rotateZ(new Point(1.5, 1.5, 0), Math.PI / 12)
                .translate(0, 0, 1.1), blue);
    }
}
