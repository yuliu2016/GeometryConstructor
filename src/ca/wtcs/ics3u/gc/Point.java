package ca.wtcs.ics3u.gc;


/*
 * Created by Yu Liu on 2017-06-06
 * ICS3U - Culminating Project - A compass-and-straightedge construction tool
 *
 * More information at https://github.com/yuliu2016/GeometryConstructor
 */


import java.awt.*;


/**
 * A <code>Point</code> represents a point on the plane that has a x and y coordinate.
 * <p>
 * In the future it will be changed into more dynamic class
 *
 * @author Yu Liu
 * @see Geometry
 */


public class Point
        extends Geometry {


    /**
     * The inner radius of the point to be shown on the screen
     */

    private static final int RADIUS;

    /**
     * The outer radius that is used to compare the position of the mouse
     * to test a mouseover, as well as highlighting the point when mouse
     * is over the point
     */

    private static final int RANGE;

    static {
        RADIUS = 5;
        RANGE = 8;
    }

    /**
     * The coordinates of the point
     */

    private double x, y;

    /**
     * Initializes a point with coordinates
     *
     * @param x the X absolute coordinate
     * @param y the Y absolute coordinate
     */

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    double getX() {
        return x;
    }

    double getY() {
        return y;
    }

    /**
     * Paints the point according to size specified in RADIUS and RANGE
     * and position computed from the viewport.
     * Its colour depends on if it is selected/hovered.
     * If hovered paint an extra ring outside the point
     *
     * @param g        the graphic context to be drawn on
     * @param viewPort the viewport context to determine position
     */

    @Override
    public void paint(Graphics g, ViewPort viewPort) {

        if (!isHidden()) {

            int vx = viewPort.computeX(x) - RADIUS;
            int vy = viewPort.computeY(y) - RADIUS;

            g.setColor(getColor());
            g.fillOval(vx, vy, RADIUS * 2, RADIUS * 2);

            if (isHovered())
                g.drawOval(vx - RANGE + RADIUS, vy - RANGE + RADIUS, RANGE * 2, RANGE * 2);
        }
    }


    /**
     * Determines if a mouse position is hovering over the point
     * (i.e. mouse is within distance of RANGE)
     *
     * @param mouseX   the X position of the mouse
     * @param mouseY   the Y position of the mouse
     * @param viewPort the viewport context to determine position
     * @return if the mouse is over the point
     */

    @Override
    public boolean isMouseOver(int mouseX, int mouseY, ViewPort viewPort) {
        return ViewPort.mag(mouseX - viewPort.computeX(x), mouseY - viewPort.computeY(y)) < RANGE;
    }

}