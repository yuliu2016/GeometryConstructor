package ca.wtcs.ics3u.gc;


import java.awt.*;


/*
 * Created by Yu Liu on 2017-06-06
 * ICS3U - Culminating Project - A compass-and-straightedge construction tool
 *
 * More information at https://github.com/yuliu2016/GeometryConstructor
 */


/**
 * A <code>Point</code> represents a point on the plane that has a x and y coordinate.
 *
 * In the future it will be changed into more dynamic class
 *
 * @author Yu Liu
 * @see Geometry
 * @see Geometry1D
 */


public class Point
        implements Geometry1D {


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
     * @param x the X absolute coordinate
     * @param y the Y absolute coordinate
     */

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Paints the point according to size specified in {@link Const}
     * and position computed from the viewport.
     * Its colour depends on if it is selected/hovered.
     * If hovered paint an extra ring outside the point
     * @param g the graphic context to be drawn on
     * @param viewPort the viewport context to determine position
     */

    @Override
    public void paint(Graphics g, ViewPort viewPort) {

        if (!hidden) {

            int vx = viewPort.computeX(x) - Const.POINT_DIAMETER / 2;
            int vy = viewPort.computeY(y) - Const.POINT_DIAMETER / 2;

            g.setColor(hovered || selected ? Color.BLUE : Color.GRAY);

            g.fillOval(vx, vy, Const.POINT_DIAMETER, Const.POINT_DIAMETER);

            if (hovered)
                g.drawOval(vx - (Const.POINT_OUTLINE - Const.POINT_DIAMETER) / 2,
                        vy - (Const.POINT_OUTLINE - Const.POINT_DIAMETER) / 2,
                        Const.POINT_OUTLINE, Const.POINT_OUTLINE);
        }
    }

    
    /**
     * Determines if a mouse position is hovering over the point
     * @param mouseX the X position of the mouse
     * @param mouseY the Y position of the mouse
     * @param viewPort the viewport context to determine position
     * @return if the mouse is over the point
     */

    @Override
    public boolean isMouseOver(int mouseX, int mouseY, ViewPort viewPort) {
        return Math.sqrt(Math.pow(mouseX - viewPort.computeX(x), 2) + Math.pow(mouseY - viewPort.computeY(y), 2)) < Const.POINT_OUTLINE / 2;
    }


    /**
     * Determines if the point is selected
     * @return if the point is selected
     */

    @Override
    public boolean isSelected() {
        return selected;
    }


    /**
     * Determines if the point is hidden
     * @return if the point is hidden
     */

    @Override
    public boolean isHidden() {
        return hidden;
    }


    /**
     * Sets the hovered state of point
     * @param hovered the hovered state of point
     */

    @Override
    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }


    /**
     * Sets the selected state of point
     * @param selected the selected state of point
     */

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    /**
     * Sets the hidden state of point
     * @param hidden the hidden state of point
     */

    @Override
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

}