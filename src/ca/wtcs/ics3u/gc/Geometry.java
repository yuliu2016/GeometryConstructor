package ca.wtcs.ics3u.gc;


/*
 * Created by Yu Liu on 2017-06-06
 * ICS3U - Culminating Project - A compass-and-straightedge construction tool
 *
 * More information at https://github.com/yuliu2016/GeometryConstructor
 */


import java.awt.*;

/**
 * <code>Geometry</code> is a top-level interface for
 * all geometric entities used in this application. An
 * object that implements this interface will be able to
 * select/deselect , show/hide, and paint itself, and would
 * also be able to add itself into a {@link GraphView}
 *
 * @author Yu Liu
 */


abstract class Geometry {


    /**
     * The selected state of the geometry
     */

    private boolean selected;


    /**
     * The hidden state of the geometry
     */

    private boolean hidden;


    /**
     * The hovered state of the geometry
     */

    private boolean hovered;


    /**
     * Paints the geometry entity
     *
     * @param g        the graphic context to be drawn on
     * @param viewPort the viewport context to determine position
     */

    abstract void paint(Graphics g, ViewPort viewPort);


    /**
     * Determines if a mouse position is hovering over the geometry
     *
     * @param mouseX   the X position of the mouse
     * @param mouseY   the Y position of the mouse
     * @param viewPort the viewport context to determine position
     * @return if the mouse is over geometry
     */

    abstract boolean isMouseOver(int mouseX, int mouseY, ViewPort viewPort);


    /**
     * Determines if the geometry is selected
     *
     * @return if the geometry is selected
     */

    boolean isSelected() {
        return selected;
    }

    /**
     * Sets the selected state of geometry
     *
     * @param selected the selected state of geometry
     */

    void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Determines if the geometry is hidden
     *
     * @return if the geometry is hidden
     */

    boolean isHidden() {
        return hidden;
    }

    /**
     * Sets the hidden state of geometry
     *
     * @param hidden the hidden state of the geometry
     */

    void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    /**
     * Determines if the geometry is on mouseover
     *
     * @return if the geometry is hidden
     */

    boolean isHovered() {
        return hovered;
    }

    /**
     * Sets the hovered state of geometry
     *
     * @param hovered the hovered state of geometry
     */

    void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    /**
     * Determines the color of the geometric object according to its state.
     * Overriding paint method should call this method to determine color
     *
     * @return The unified colour of the object
     */

    Color getColor() {
        return hovered || selected ? Color.BLUE : Color.BLACK;
    }

}