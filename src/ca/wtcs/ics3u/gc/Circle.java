package ca.wtcs.ics3u.gc;


/*
 * Created by Yu Liu on 2017-06-15
 * ICS3U - Culminating Project - A compass-and-straightedge construction tool
 *
 * More information at https://github.com/yuliu2016/GeometryConstructor
 */


import java.awt.*;


/**
 * A <code>Circle</code> is a {@link Geometry}
 * object that represents a circle on a plane
 * with the center at one point through another,
 * with the distance between these points the radius
 * of the circle. The position of a circle must be
 * dynamically computed, meaning the two {@link Point}
 * that defines the object must also have computed
 * or fixed values.
 */


public class Circle
        extends Geometry {

    /**
     * The width of the circle's stroke when it's hovered
     */

    private static final int THICK_STROKE_WIDTH;


    static {
        THICK_STROKE_WIDTH = 4;
    }

    /**
     * The two points that defines the circle
     */

    private Point center, throughPoint;


    /**
     *
     */

    Circle(Point center, Point throughPoint) {
        this.center = center;
        this.throughPoint = throughPoint;
    }


    private double radius(ViewPort viewPort) {
        return ViewPort.mag(viewPort.computeX(throughPoint.getX()) - viewPort.computeX(center.getX()),
                viewPort.computeY(throughPoint.getY()) - viewPort.computeY(center.getY()));
    }

    /**
     * Paints the geometry entity
     *
     * @param g        the graphic context to be drawn on
     * @param viewPort the viewport context to determine position
     */

    @Override
    public void paint(Graphics g, ViewPort viewPort) {

        if (!isHidden()) {
            int cx, cy, r;

            cx = viewPort.computeX(center.getX());
            cy = viewPort.computeY(center.getY());
            r = (int) radius(viewPort);

            g.setColor(getColor());
            g.drawOval(cx - r, cy - r, r * 2, r * 2);

        }
    }


    /**
     * Determines if a mouse position is hovering over the geometry
     *
     * @param mouseX   the X position of the mouse
     * @param mouseY   the Y position of the mouse
     * @param viewPort the viewport context to determine position
     * @return if the mouse is over geometry
     */

    @Override
    public boolean isMouseOver(int mouseX, int mouseY, ViewPort viewPort) {
        return Math.abs(radius(viewPort) - ViewPort.mag(mouseX - viewPort.computeX(center.getX()),
                mouseY - viewPort.computeY(center.getY()))) < THICK_STROKE_WIDTH;
    }

}
