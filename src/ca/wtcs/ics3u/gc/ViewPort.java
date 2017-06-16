package ca.wtcs.ics3u.gc;


/*
 * Created by Yu Liu on 2017-06-06
 * ICS3U - Culminating Project - A compass-and-straightedge construction tool
 *
 * More information at https://github.com/yuliu2016/GeometryConstructor
 */


/**
 * <code>ViewPort</code> is a position model of a window,
 * and enables pan, zoom, and resize,
 * while calculating the actual window coordinates from an an
 * absolute position or vice versa.
 *
 * @author Yu Liu
 * @see GraphView
 */


class ViewPort {


    /**
     * The minimum zoom ratio allowed by the view
     */

    private static final double MIN_ZOOM;

    /**
     * The maximum zoom ratio allowed by the view
     */

    private static final double MAX_ZOOM;

    static {

        MIN_ZOOM = 0.15;

        MAX_ZOOM = 6;

    }


    /**
     * The position of the view at absolute (0, 0)
     */

    private double centerX, centerY;


    /**
     * The position of the window's center
     */

    private double windowCenterX, windowCenterY;


    /**
     * The rate of pan per update
     */

    private double panX, panY;


    /**
     * The default scale of the view
     */

    private double defaultScale;


    /**
     * The current scale of the view
     */

    private double scale;


    /**
     * The zoom ratio
     */

    private double zoom;


    /**
     * Whether the view should reset
     */

    private boolean viewShouldReset;


    /**
     * Computes the absolute position of a relative x value according to the view
     *
     * @param x relative x value
     * @return the absolute x position on the window
     */

    int computeX(double x) {
        return (int) (centerX + x * scale);
    }


    /**
     * Computes the absolute position of a relative x value according to the view
     *
     * @param y relative x value
     * @return the absolute y position on the window
     */

    int computeY(double y) {
        return (int) (centerY + y * scale);
    }


    /**
     * Computes the relative position of an absolute x value according the the view
     *
     * @param x absolute x value
     * @return the relative x position in the model
     */

    double computeFromX(int x) {
        return (x - centerX) / scale;
    }


    /**
     * Computes the relative position of an absolute y value according the the view
     *
     * @param y absolute y value
     * @return the relative y position in the model
     */

    double computeFromY(int y) {
        return (y - centerY) / scale;
    }


    /**
     * Computes the ratio of the scale to the default scale
     *
     * @return the ratio of the scale to the default scale
     */

    double getRatio() {
        return scale / defaultScale;
    }


    /**
     * Resizes the size of the window
     *
     * @param width  width of the window
     * @param height height of the window
     */

    void setSize(int width, int height) {
        windowCenterX = width / 2.0;
        windowCenterY = height / 2.0;
        scale = defaultScale;
    }


    /**
     * Resets the viewpoint to the center without zooming in or out
     */

    void resetView() {
        viewShouldReset = true;
    }


    /**
     * Sets the default scale of the viewport
     *
     * @param s the default scale of the viewport
     */

    void setDefaultScale(double s) {
        defaultScale = s;
    }


    /**
     * Sets the rate of pan when updating
     *
     * @param x change in x
     * @param y change in y
     */

    void setPan(int x, int y) {
        panX = x;
        panY = y;
    }


    /**
     * Sets the rate of zoom when updating
     *
     * @param zoomRate the rate of zoom increase or decrease
     */

    void setZoom(double zoomRate) {
        zoom = zoomRate;
    }


    /**
     * Updates the view according to various variables. <br>
     * Computes the new center using the zoom, then the pan. <br>
     * Keeps the scale in bounds
     * Resets everything if viewShouldReset is true. <br>
     */

    void update() {

        if (zoom != 1) {

            scale = scale * zoom;

            centerX = windowCenterX + (centerX - windowCenterX) * zoom;
            centerY = windowCenterY + (centerY - windowCenterY) * zoom;

        }

        if (scale < MIN_ZOOM * defaultScale)
            scale = MIN_ZOOM * defaultScale;

        if (scale > MAX_ZOOM * defaultScale)
            scale = MAX_ZOOM * defaultScale;


        if (panX != 0 || panY != 0) {

            centerX += panX;
            centerY += panY;

        }

        if (viewShouldReset) {

            scale = defaultScale;

            centerX = windowCenterX;
            centerY = windowCenterY;

            panX = 0;
            panY = 0;

            zoom = 1;

            viewShouldReset = false;

        }
    }
}