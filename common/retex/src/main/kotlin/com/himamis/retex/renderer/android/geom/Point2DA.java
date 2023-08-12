package com.himamis.retex.renderer.android.geom;

import android.graphics.PointF;

import com.himamis.retex.renderer.share.platform.geom.Point2D;

public class Point2DA implements Point2D {

    private PointF point;

    public Point2DA(double x, double y) {
        point = new PointF((float) x, (float) y);
    }

    public double getX() {
        return point.x;
    }

    public void setX(double x) {
        point.x = (float) x;
    }

    public double getY() {
        return point.y;
    }

    public void setY(double y) {
        point.y = (float) y;
    }

}
