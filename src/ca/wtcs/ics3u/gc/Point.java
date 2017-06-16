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
 * @see Geometry1D
 */


public class Point
        implements Geometry1D {


    /**
     * The inner radius of the point to be shown on the screen
     */

    private static final int RADIUS;

    /**
     * The outer radius that is used to compare the position of the mouse
     * to test a mouseover, as well as highlighting the point when mouse
     * is over the point
     */

    private static final int RANGE_RADIUS;

    static {
        RADIUS = 5;
        RANGE_RADIUS = 8;
    }

    /**
     * The coordinates of the point
     */

    private double x, y;


    /**
     * The states of the point
     */

    private boolean hovered, selected, hidden;


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

    /**
     * Paints the point according to size specified in RADIUS and RANGE_RADIUS
     * and position computed from the viewport.
     * Its colour depends on if it is selected/hovered.
     * If hovered paint an extra ring outside the point
     *
     * @param g        the graphic context to be drawn on
     * @param viewPort the viewport context to determine position
     */

    @Override
    public void paint(Graphics g, ViewPort viewPort) {

        if (!hidden) {

            int vx = viewPort.computeX(x) - RADIUS;
            int vy = viewPort.computeY(y) - RADIUS;

            g.setColor(hovered || selected ? Color.BLUE : Color.BLACK);
            g.fillOval(vx, vy, RADIUS * 2, RADIUS * 2);

            if (hovered)
                g.drawOval(vx - RANGE_RADIUS + RADIUS, vy - RANGE_RADIUS + RADIUS, RANGE_RADIUS * 2, RANGE_RADIUS * 2);
        }
    }


    /**
     * Determines if a mouse position is hovering over the point
     * (i.e. mouse is within distance of RANGE_RADIUS)
     *
     * @param mouseX   the X position of the mouse
     * @param mouseY   the Y position of the mouse
     * @param viewPort the viewport context to determine position
     * @return if the mouse is over the point
     */

    @Override
    public boolean isMouseOver(int mouseX, int mouseY, ViewPort viewPort) {
        return Math.sqrt(Math.pow(mouseX - viewPort.computeX(x), 2) + Math.pow(mouseY - viewPort.computeY(y), 2)) < RANGE_RADIUS;
    }


    /**
     * Determines if the point is selected
     *
     * @return if the point is selected
     */

    @Override
    public boolean getSelected() {
        return selected;
    }

    /**
     * Sets the selected state of point
     *
     * @param selected the selected state of point
     */

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Determines if the point is hidden
     *
     * @return if the point is hidden
     */

    @Override
    public boolean getHidden() {
        return hidden;
    }

    /**
     * Sets the hidden state of point
     *
     * @param hidden the hidden state of point
     */

    @Override
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    /**
     * Sets the hovered state of point
     *
     * @param hovered the hovered state of point
     */

    @Override
    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

}